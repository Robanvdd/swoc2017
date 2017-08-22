#ifndef GAME_H
#define GAME_H

#include <QObject>

#include "gameobject.h"
#include "solarsystem.h"
#include "player.h"

class Game : public GameObject
{
    Q_OBJECT
    Q_PROPERTY(QString name MEMBER m_name NOTIFY nameChanged)
    Q_PROPERTY(int currentTick MEMBER m_currentTick NOTIFY currentTickChanged)
    Q_PROPERTY(QList<QObject*> solarSystems MEMBER m_solarSystems NOTIFY solarSystemsChanged)
    Q_PROPERTY(QList<QObject*> players MEMBER m_players NOTIFY playersChanged)
public:
    explicit Game(QObject *parent = 0);
    explicit Game(int gameId, QObject *parent = 0);
    ~Game();

    Q_INVOKABLE bool solarSystemExists(int solarSystemId) const;
    Q_INVOKABLE void createSolarSystem(int solarSystemId);
    Q_INVOKABLE SolarSystem* getSolarSystem(int solarSystemId) const;

    Q_INVOKABLE bool playerExists(int playerId) const;
    Q_INVOKABLE void createPlayer(int playerId);
    Q_INVOKABLE Player* getPlayer(int playerId) const;

    Q_INVOKABLE void reset();

signals:
    void nameChanged();
    void currentTickChanged();
    void solarSystemsChanged();
    void playersChanged();

public slots:
private:
    QString m_name;
    int m_currentTick = -1;
    QList<QObject*> m_solarSystems;
    QList<QObject*> m_players;
};

#endif // GAME_H
