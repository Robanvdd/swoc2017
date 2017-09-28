#ifndef ENGINE_H
#define ENGINE_H

#include "MacroGame.h"
#include "PlayerBotFolders.h"
#include "UniverseBuilder.h"

#include <QObject>
#include <QElapsedTimer>
#include <QTimer>

class Engine : public QObject
{
    Q_OBJECT
public:
    explicit Engine(QList<PlayerBotFolders*> playerBotFolders, QObject *parent = 0);

signals:
    void finished();
    void errorOccured();

public slots:
    void startNewMacroGame();

private:
    QString m_executable;
    QList<PlayerBotFolders*> m_playerBotFolders;
    QList<MacroGame*> m_macroGames;
    UniverseBuilder m_universeBuilder;
};

#endif // ENGINE_H
