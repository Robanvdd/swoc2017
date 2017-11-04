#ifndef UNIVERSE_H
#define UNIVERSE_H

#include "Fight.h"
#include "Player.h"
#include "SolarSystem.h"

#include <QJsonObject>
#include <QMap>
#include <QObject>
#include <QList>

class Universe : public GameObject
{
    Q_OBJECT
public:
    explicit Universe(QMap<int, SolarSystem*> solarSystems, QObject *parent = nullptr);
    void writeState(QJsonObject& gameState);
    void applyTick(double durationInSeconds);

    void addPlayer(Player* player);
    QList<Ufo*> getUfosNearLocation(const QPointF& location, const Player& player);

    QMap<int, SolarSystem*> getSolarSystems() const;
    Planet* getPlanet(int id) const;
    QMap<int, Player*> getPlayers() const;
    QMap<int, Fight*> getFights() const;
    SolarSystem* getCorrespondingSolarSystem(Planet* planet);

    QList<Ufo*> getFriends(const QList<Ufo*>& attackees, const Player& player);
signals:

public slots:

private:
    QMap<int, SolarSystem*> m_solarSystems;
    QMap<int, Player*> m_players;
    QMap<int, Fight*> m_fights;
    double m_baseIncomePerSecond;
    double m_incomePerPlanetPerSecond;

    void addCredits(Player* player, double durationInSeconds);
};

#endif // UNIVERSE_H
