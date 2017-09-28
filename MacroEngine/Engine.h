#ifndef ENGINE_H
#define ENGINE_H

#include "MacroGame.h"
#include "UniverseBuilder.h"

#include <QObject>
#include <QElapsedTimer>
#include <QTimer>

class Engine : public QObject
{
    Q_OBJECT
public:
    explicit Engine(QString executable, QObject *parent = 0);

signals:
    void finished();
    void errorOccured();

public slots:
    void startNewMacroGame();

private:
    QString m_executable;
    QList<MacroGame*> m_macroGames;
    UniverseBuilder m_universeBuilder;
};

#endif // ENGINE_H
