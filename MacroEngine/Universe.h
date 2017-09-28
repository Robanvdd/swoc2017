#ifndef UNIVERSE_H
#define UNIVERSE_H

#include "Fight.h"
#include "Player.h"
#include "SolarSystem.h"

#include <QJsonObject>
#include <QObject>

class Universe : public GameObject
{
    Q_OBJECT
public:
    explicit Universe(QList<SolarSystem*> solarSystems, QObject *parent = nullptr);
    void writeState(QJsonObject& gameState);
    void applyTick(double durationInSeconds);

    void addPlayer(Player* player);

    QList<SolarSystem*> getSolarSystems() const;
    QList<Player*> getPlayers() const;
    QList<Fight*> getFights() const;

signals:

public slots:

private:
    QList<SolarSystem*> m_solarSystems;
    QList<Player*> m_players;
    QList<Fight*> m_fights;
    double m_baseIncomePerSecond;
    double m_incomePerPlanetPerSecond;

    void addCredits(Player* player, double durationInSeconds);
};

#endif // UNIVERSE_H
