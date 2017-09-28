#include "Universe.h"

#include <QJsonArray>

Universe::Universe(QList<SolarSystem *> solarSystems, QObject *parent)
    : GameObject(parent)
    , m_solarSystems(solarSystems)
    , m_baseIncomePerSecond(5000)
    , m_incomePerPlanetPerSecond(1000)
{
    for (auto solarSystemIt = m_solarSystems.begin(); solarSystemIt != m_solarSystems.end(); solarSystemIt++)
    {
        (*solarSystemIt)->setParent(this);
    }
}

void Universe::writeState(QJsonObject& gameState)
{
//    QJsonArray solarSystemArray;
//    foreach (const SolarSystem* solarSystem, m_solarSystems) {
//        QJsonObject solarSystemObject;
//        solarSystem->writeState(solarSystemObject);
//        solarSystemArray.append(solarSystemObject);
//    }
//    gameState["solarSystems"] = solarSystemArray;
    QJsonArray playerArray;
//    foreach (Player* player, m_players) {
//        QJsonObject playerObject;
//        player->writeState(playerObject);
//        playerArray.append(playerObject);
//    }
    gameState["players"] = playerArray;

    // TODO
//    QJsonArray fights;
//    gameState["fights"] = fights;
}

void Universe::applyTick(double durationInSeconds)
{
    foreach (SolarSystem* solarSystem, m_solarSystems) {
        solarSystem->applyTick(durationInSeconds);
    }
    foreach (Player* player, m_players) {
        addCredits(player, durationInSeconds);
    }
}

void Universe::addPlayer(Player* player)
{
    m_players << player;
}

QList<SolarSystem*> Universe::getSolarSystems() const
{
    return m_solarSystems;
}

QList<Player*> Universe::getPlayers() const
{
    return m_players;
}

QList<Fight*> Universe::getFights() const
{
    return m_fights;
}

void Universe::addCredits(Player* player, double durationInSeconds)
{
    player->addCredits(m_baseIncomePerSecond * durationInSeconds);
    int ownedPlanets = 0;
    foreach (SolarSystem* solarSystem, m_solarSystems) {
        foreach (Planet* planet, solarSystem->getPlanets()) {
            if (planet->getOwnedBy() == player->getId())
                ownedPlanets++;
        }
    }
    if (ownedPlanets > 0)
        player->addCredits(ownedPlanets * m_incomePerPlanetPerSecond * durationInSeconds);
}
