#include "BuyCommand.h"
#include "CommandBase.h"
#include "ConquerCommand.h"
#include "MacroGame.h"
#include "MicroGameInput.h"
#include "MoveToCoordCommand.h"
#include "MoveToPlanetCommand.h"
#include <QFile>
#include <QDebug>
#include <QJsonArray>
#include <QJsonDocument>
#include <QTextStream>
#include <iostream>
#include <exception>

#ifdef __linux__
#define RUN_FILE "/run.sh"
#endif
#ifdef _WIN32_
#define RUN_FILE "\\run.bat"
#endif
#ifdef _WIN64
#define RUN_FILE "\\run.bat"
#endif

MacroGame::MacroGame(QList<PlayerBotFolders*> playerBotFolders, Universe* universe, QObject *parent)
    : GameObject(parent)
    , m_playerBotFolders(playerBotFolders)
    , m_universe(universe)
    , m_tickTimer(new QTimer(this))
    , m_currentTick(1)
    , m_tickDurationInSeconds(0.25)
    , m_name("Match ###")
{
    m_universe->setParent(this);

    foreach (auto playerBotFolder, m_playerBotFolders)
    {
        auto player = new Player(playerBotFolder->getPlayerName(), this);
        m_ufoShop.giveUfo(player, m_universe);
        m_ufoShop.giveUfo(player, m_universe);
        m_ufoShop.giveUfo(player, m_universe);
        m_universe->addPlayer(player);
        auto bot = new MacroBot(playerBotFolder->getMacroBotFolder() + RUN_FILE, "", this);
        m_macroBots << bot;
        m_playerBotMap[player] = bot;
        m_botPlayerMap[bot] = player;
        m_playerMicroBotFolder[player] = playerBotFolder->getMicroBotFolder();
    }

    connect(m_tickTimer, &QTimer::timeout, this, [this]() { handleTick(); });
}

void MacroGame::run()
{
    startBots();
    m_elapsedTimer.start();
    m_tickTimer->start(static_cast<int>(m_tickDurationInSeconds * 1000));
}

void MacroGame::startBots()
{
    for (auto it = m_macroBots.begin(); it != m_macroBots.end(); it++)
    {
        auto macroBot = *it;
        macroBot->startProcess();
    }
}

void MacroGame::killBots()
{
    for (auto it = m_macroBots.begin(); it != m_macroBots.end(); it++)
    {
        auto macroBot = *it;
        macroBot->stopProcess();
    }
}

void MacroGame::killMicroGames()
{
    for (auto it = m_microGames.begin(); it != m_microGames.end(); it++)
    {
        auto microGame = *it;
        microGame->stopProcess();
    }
}

void MacroGame::stopMacroGame()
{
    m_tickTimer->stop();
    killBots();
    killMicroGames();
    deleteLater();
}

bool MacroGame::gameTimeOver()
{
    return m_elapsedTimer.elapsed() > 600e3;
}

void MacroGame::handleTick()
{
    if (gameTimeOver())
    {
        stopMacroGame();
        return;
    }

    m_universe->applyTick(m_tickDurationInSeconds);

    QJsonDocument gameStateDoc(generateGameState());
    writeGameState(gameStateDoc);
    communicateWithBots(gameStateDoc);

    m_currentTick++;
}

void MacroGame::writeGameState(QJsonDocument gameStateDoc)
{
    auto gameStateJson = gameStateDoc.toJson(QJsonDocument::Indented);
    QFile file("macro_" + QString::number(m_currentTick) + ".json");
    if (file.open(QIODevice::ReadWrite))
    {
        QTextStream stream(&file);
        stream << gameStateJson;
    }
}

void MacroGame::communicateWithBot(Player* player, QJsonDocument gameStateDoc)
{
    auto macroBot = m_playerBotMap[player];
    macroBot->sendGameState(gameStateDoc.toJson(QJsonDocument::Compact));

    // Collect all commands
    QStringList commands = macroBot->receiveCommands();
    for (auto commandString : commands)
    {
        // Parse command
        QJsonParseError error;
        auto doc = QJsonDocument::fromJson(commandString.toUtf8(), &error);
        if (error.error == QJsonParseError::NoError)
        {
            auto object = doc.object();
            std::unique_ptr<CommandBase> command = createCommand(object);
            handleCommand(player, command);
        }
    }
}

void MacroGame::handleBuyCommand(Player* player, BuyCommand* buyCommand)
{
    m_ufoShop.buyUfos(player, m_universe->getPlanet(buyCommand->getPlanetId()), m_universe, buyCommand->getAmount());
}

void MacroGame::handleConquerCommand(Player* player, ConquerCommand* conquerCommand)
{
    auto planet = m_universe->getPlanet(conquerCommand->getPlanetId());
    if (planet == nullptr || player == nullptr)
        return;
    // Planet not yet claimed
    if (planet->getOwnedBy() == -1)
    {
        planet->takeOverBy(player);
        return;
    }

    // Prepare fight
    Player* currentOwner = m_universe->getPlayers().value(planet->getOwnedBy(), nullptr);
    if (currentOwner == nullptr)
    {
        throw std::logic_error("Planet is owned, but not by an existing player");
    }

    SolarSystem* solarSystem = m_universe->getCorrespondingSolarSystem(planet);
    QPointF location = solarSystem->getPlanetLocation(*planet);
    QList<Ufo*> nearbyUfosPlayer = solarSystem->getUfosNearLocation(location, *player);
    QList<Ufo*> nearbyUfosCurrentOwner = solarSystem->getUfosNearLocation(location, *currentOwner);
    if (nearbyUfosPlayer.size() == 0)
        return;
    if (nearbyUfosCurrentOwner.size() == 0)
    {
        planet->takeOverBy(player);
        return;
    }
    startMicroGame(player, nearbyUfosPlayer, currentOwner, nearbyUfosCurrentOwner);
}

void MacroGame::handleMoveToPlanetCommand(Player* player, MoveToPlanetCommand* moveToPlanetCommand)
{
    if (player == nullptr || moveToPlanetCommand == nullptr)
        throw std::logic_error("Got nullptr player or moveToPlanetCommand");

    auto planetId = moveToPlanetCommand->getPlanetId();
    auto planet = m_universe->getPlanet(planetId);
    if (planet == nullptr)
        return;

    foreach (auto ufoId, moveToPlanetCommand->getUfos())
    {
        if (player->hasUfo(ufoId))
        {
            auto ufo = player->getUfo(ufoId);
            ufo->setFlyingToPlanet(true);
            ufo->setTargetPlanet(planet);
        }
    }
}

void MacroGame::handleMoveToCoordCommand(Player* player, MoveToCoordCommand* moveToCoordCommand)
{
    if (player == nullptr || moveToCoordCommand == nullptr)
        throw std::logic_error("Got nullptr player or moveToCoordCommand");

    foreach (auto ufoId, moveToCoordCommand->getUfos())
    {
        if (player->hasUfo(ufoId))
        {
            auto ufo = player->getUfo(ufoId);
            ufo->setFlyingToCoord(true);
            ufo->setTargetCoord(moveToCoordCommand->getCoords());
        }
    }
}

void MacroGame::startMicroGame(Player* playerA, QList<Ufo*> ufosPlayerA, Player* playerB, QList<Ufo*> ufosPlayerB)
{
    MicroGameInput input(playerA, ufosPlayerA, m_playerMicroBotFolder[playerA],
                         playerB, ufosPlayerB, m_playerMicroBotFolder[playerB]);

    foreach (auto ufo, ufosPlayerA)
    {
        ufo->setInFight(true);
    }
    foreach (auto ufo, ufosPlayerB)
    {
        ufo->setInFight(true);
    }

    MicroGame* microGame = new MicroGame("java -jar D:\\micro.jar", input);
    microGame->startProcess();

    QObject::connect(microGame, &MicroGame::dataAvailable, this, [this, microGame]() {
        if (microGame->canReadLine())
        {
            qDebug() << "Data available";
            // TODO parse output
            auto result = microGame->readLine();
            qDebug() << result;
            microGame->stopProcess();
            m_microGames.removeAll(microGame);
            microGame->deleteLater();
        }
    });

    m_microGames << microGame;
}

void MacroGame::handleCommand(Player* player, std::unique_ptr<CommandBase>& command)
{
    command->printCommand();
    BuyCommand* buyCommand = dynamic_cast<BuyCommand*>(command.get());
    if (buyCommand)
    {
        handleBuyCommand(player, buyCommand);
    }
    ConquerCommand* conquerCommand = dynamic_cast<ConquerCommand*>(command.get());
    if (conquerCommand)
    {
        handleConquerCommand(player, conquerCommand);
    }
    MoveToPlanetCommand* moveToPlanetCommand = dynamic_cast<MoveToPlanetCommand*>(command.get());
    if (moveToPlanetCommand)
    {
        handleMoveToPlanetCommand(player, moveToPlanetCommand);
    }
    MoveToCoordCommand* moveToCoordCommand = dynamic_cast<MoveToCoordCommand*>(command.get());
    if (moveToCoordCommand)
    {
        handleMoveToCoordCommand(player, moveToCoordCommand);
    }
}

void MacroGame::communicateWithBots(QJsonDocument gameStateDoc)
{
    for (auto player : m_universe->getPlayers())
    {
        communicateWithBot(player, gameStateDoc);
    }
    std::cout << std::flush;
}

QJsonObject MacroGame::generateGameState()
{
    QJsonObject gameState;
    gameState["id"] = m_id;
    gameState["name"] = m_name;
    gameState["tick"] = m_currentTick;
    m_universe->writeState(gameState);
    return gameState;
}

std::unique_ptr<CommandBase> MacroGame::createCommand(const QJsonObject object)
{
    auto commandString = object["Command"].toString();
    std::unique_ptr<CommandBase> command;
    if (commandString == "moveToPlanet")
    {
        command = std::make_unique<MoveToPlanetCommand>();
    }
    else if (commandString == "moveToCoord")
    {
        command = std::make_unique<MoveToCoordCommand>();
    }
    else if (commandString == "buy")
    {
        command = std::make_unique<BuyCommand>();
    }
    else if (commandString == "conquer")
    {
        command = std::make_unique<ConquerCommand>();
    }
    else {
        throw std::exception();
    }
    command->readCommand(object);
    return command;
}
