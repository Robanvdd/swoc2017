#ifndef MACROGAME_H
#define MACROGAME_H

#include "GameObject.h"
#include "MacroBot.h"
#include "MicroGame.h"
#include "Universe.h"
#include "CommandBase.h"
#include "PlayerBotFolders.h"
#include "UfoShop.h"
#include "BuyCommand.h"
#include "ConquerCommand.h"
#include "MoveToPlanetCommand.h"
#include "MoveToCoordCommand.h"
#include "FightCommand.h"

#include <QDir>
#include <QElapsedTimer>
#include <QMap>
#include <QObject>
#include <QTimer>
#include <QList>
#include <memory>

class MacroGame : public GameObject
{
    Q_OBJECT
public:
    MacroGame(QList<PlayerBotFolders*> playerBotFolders, Universe *universe, QObject *parent = nullptr);
    void run();

signals:
    void finished();
    void errorOccured();

public slots:

private:
    QList<PlayerBotFolders*> m_playerBotFolders;
    Universe* m_universe;

    QTimer* m_tickTimer;
    QElapsedTimer m_elapsedTimer;
    QList<MacroBot*> m_macroBots;
    QList<MicroGame*> m_microGames;
    int m_currentTick;
    double m_tickDurationInSeconds;
    QMap<Player*, MacroBot*> m_playerBotMap;
    QMap<MacroBot*, Player*> m_botPlayerMap;
    QMap<Player*, QString> m_playerMicroBotFolder;
    QString m_name;
    UfoShop m_ufoShop;
    int m_gameId;
    QDir m_matchDir;
    QDir m_tickDir;

    void startBots();
    void killBots();
    void killMicroGames();
    void handleTick();
    void communicateWithBots(QJsonDocument gameStateDoc);
    QJsonObject generateGameState();
    void stopMacroGame();
    bool gameTimeOver();
    void writeGameState(QJsonDocument doc);
    void communicateWithBot(Player* player, QJsonDocument gameStateDoc);
    std::unique_ptr<CommandBase> createCommand(const QJsonObject object);
    void handleCommand(Player *player, std::unique_ptr<CommandBase> &command);
    void handleBuyCommand(Player* player, BuyCommand* buyCommand);
    void handleConquerCommand(Player* player, ConquerCommand* conquerCommand);
    void handleMoveToPlanetCommand(Player* player, MoveToPlanetCommand* moveToPlanetCommand);
    void handleMoveToCoordCommand(Player* player, MoveToCoordCommand* moveToCoordCommand);
    void handleFightCommand(Player* attacker, int ufoId);
    void handleFightCommand(Player* player, FightCommand* fightCommand);

    void startMicroGame(const MicroGameInput& input, Planet* planet);
    void setNameAndLogDir();
    void startMicroGame(Player* player, const QPointF& location, Planet* planet);
    void killMacro();
    void startMicroGame(const MicroGameInput& input);
};

#endif // MACROGAME_H
