#include "Engine.h"

#include <QTimer>
#include <iostream>

Engine::Engine(QList<PlayerBotFolders*> playerBotFolders, QObject *parent)
    : QObject(parent)
    , m_playerBotFolders(playerBotFolders)
{
    foreach (auto playerBotFolders, m_playerBotFolders)
    {
        playerBotFolders->setParent(this);
    }
}

void Engine::startNewMacroGame()
{
    auto newMacroGame = new MacroGame(m_playerBotFolders, m_universeBuilder.buildUniverse(), this);
    m_macroGames << newMacroGame;
    connect(newMacroGame, &MacroGame::finished, this, [this, newMacroGame]()
    {
        std::cout << "MacroGame finished" << std::endl;
        m_macroGames.removeOne(newMacroGame);
    });
    connect(newMacroGame, &MacroGame::destroyed, this, [this, newMacroGame]()
    {
        std::cout << "MacroGame destroyed" << std::endl;
        QTimer::singleShot(5000, this, SLOT(startNewMacroGame()));
    });
    std::cout << "Starting new MacroGame" << std::endl;
    newMacroGame->run();
}

