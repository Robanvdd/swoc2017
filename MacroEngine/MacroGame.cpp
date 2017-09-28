#include "BuyCommand.h"
#include "CommandBase.h"
#include "ConquerCommand.h"
#include "MacroGame.h"
#include "MoveToCoordCommand.h"
#include "MoveToPlanetCommand.h"
#include <QFile>
#include <QJsonArray>
#include <QJsonDocument>
#include <QTextStream>
#include <iostream>

MacroGame::MacroGame(QString executable, Universe* universe, QObject *parent)
    : GameObject(parent)
    , m_executable(executable)
    , m_universe(universe)
    , m_tickTimer(new QTimer(this))
    , m_currentTick(1)
    , m_tickDurationInSeconds(1.0)
    , m_name("Match ###")
{
    m_universe->setParent(this);

//    auto player1 = new Player("dwight", this);
//    player1->giveUfo(new Ufo());
//    player1->giveUfo(new Ufo());
//    player1->giveUfo(new Ufo());
//    //auto bot1 = new MacroBot("C:/Users/Gebruiker/Desktop/Debug/MacroBot.exe", this);
//    auto bot1 = new MacroBot("python", "D:\\helloWorld.py", this);
    //auto bot1 = new MacroBot(executable, this);
    auto player2 = new Player("Bot2", this);
    player2->giveUfo(new Ufo());
    player2->giveUfo(new Ufo());
    player2->giveUfo(new Ufo());
    //auto bot2 = new MacroBot("python", "D:\\helloWorld.py /C", this);
    auto bot2 = new MacroBot("cmd.exe", "Z:\\Release\\MacroBot.exe", this);
    //"python D:\\helloWorld.py"
    //auto bot2 = new MacroBot("cmd.exe", "-v", this);

    //m_universe->addPlayer(player1);
    m_universe->addPlayer(player2);
    m_macroBots << bot2; //bot1 << bot2;

    //m_playerBotMap[player1] = bot1;
    m_playerBotMap[player2] = bot2;
    //m_botPlayerMap[bot1] = player1;
    m_botPlayerMap[bot2] = player2;

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

    // Handle all commands
    QStringList commands = macroBot->receiveCommands();
    for (auto commandString : commands)
    {
        // Handle command
        QJsonParseError error;
        auto doc = QJsonDocument::fromJson(commandString.toUtf8(), &error);
        if (error.error == QJsonParseError::NoError)
        {
            auto object = doc.object();
            std::unique_ptr<CommandBase> command = createCommand(object);
            command->readCommand(object);
            command->printCommand();
        }
    }
}

void MacroGame::communicateWithBots(QJsonDocument gameStateDoc)
{
    std::cout << "Talking" << std::endl;
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
    auto command = object["Command"].toString();
    if (command == "moveToPlanet")
    {
        return std::make_unique<MoveToPlanetCommand>();
    }
    else if (command == "moveToCoord")
    {
        return std::make_unique<MoveToCoordCommand>();
    }
    else if (command == "buy")
    {
        return std::make_unique<BuyCommand>();
    }
    else if (command == "conquer")
    {
        return std::make_unique<ConquerCommand>();
    }
    throw std::exception("Stuff broke");
}
