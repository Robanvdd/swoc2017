#ifndef GAME_H
#define GAME_H

#include <QObject>

#include "gameobject.h"
#include "solarsystem.h"

class Game : public GameObject
{
    Q_OBJECT
    Q_PROPERTY(QString name MEMBER m_name NOTIFY nameChanged)
    Q_PROPERTY(int currentTick MEMBER m_currentTick NOTIFY currentTickChanged)
    Q_PROPERTY(QList<QObject*> solarSystems MEMBER m_solarSystems NOTIFY solarSystemsChanged)
public:
    explicit Game(QObject *parent = 0);
    explicit Game(int gameId, QObject *parent = 0);
    Q_INVOKABLE bool solarSystemExists(int solarSystemId) const;
    Q_INVOKABLE void createSolarSystem(int solarSystemId);
    Q_INVOKABLE SolarSystem* getSolarSystem(int solarSystemId) const;

signals:
    void nameChanged();
    void currentTickChanged();
    void solarSystemsChanged();

public slots:
private:
    QString m_name;
    int m_currentTick = -1;
    QList<QObject*> m_solarSystems;
};

#endif // GAME_H
