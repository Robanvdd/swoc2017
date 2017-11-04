#include "Ufo.h"
#include "Universe.h"

#include <algorithm>
#include <QJsonArray>

Universe::Universe(QMap<int, SolarSystem *> solarSystems, QObject *parent)
    : GameObject(parent)
    , m_solarSystems(solarSystems)
    , m_baseIncomePerSecond(1500)
    , m_incomePerPlanetPerSecond(225)
{
    for (auto solarSystemIt = m_solarSystems.begin(); solarSystemIt != m_solarSystems.end(); solarSystemIt++)
    {
        (*solarSystemIt)->setParent(this);
    }
}

void Universe::writeState(QJsonObject& gameState)
{
    QJsonArray solarSystemArray;
    foreach (const SolarSystem* solarSystem, m_solarSystems) {
        QJsonObject solarSystemObject;
        solarSystem->writeState(solarSystemObject);
        solarSystemArray.append(solarSystemObject);
    }
    gameState["solarSystems"] = solarSystemArray;
    QJsonArray playerArray;
    foreach (Player* player, m_players) {
        QJsonObject playerObject;
        player->writeState(playerObject);
        playerArray.append(playerObject);
    }
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
        player->applyTick(durationInSeconds);
    }
}

void Universe::addPlayer(Player* player)
{
    m_players[player->getId()] = player;
}

QMap<int, SolarSystem *> Universe::getSolarSystems() const
{
    return m_solarSystems;
}

Planet *Universe::getPlanet(int id) const
{
    foreach (auto solarSystem, m_solarSystems)
    {
        if (solarSystem->getPlanets().contains(id))
        {
            return solarSystem->getPlanets()[id];
        }
    }
    return nullptr;
}

QMap<int, Player *> Universe::getPlayers() const
{
    return m_players;
}

QMap<int, Fight *> Universe::getFights() const
{
    return m_fights;
}

SolarSystem*Universe::getCorrespondingSolarSystem(Planet* planet)
{
    foreach (auto solarSystem, m_solarSystems)
    {
        if (solarSystem->getPlanets().key(planet, -1) != -1)
            return solarSystem;
    }
    throw std::logic_error("No SolarSystem exists that contains planet: " + QString::number(planet->getId()).toStdString());
}

void Universe::addCredits(Player* player, double durationInSeconds)
{
    player->addCredits(durationInSeconds*getIncome(player));
}

int Universe::getIncome(Player* player)
{
    int ownedPlanets = getNumberofOwnedPlanets(player);
    if (ownedPlanets == 0)
        return 0;


    auto baseIncome = m_baseIncomePerSecond;
    auto basePlanet = m_incomePerPlanetPerSecond;
    auto shifted =  std::min(ownedPlanets, 50) + 3;
    auto paneltyPerPlanet = 3;
    return baseIncome + (basePlanet - paneltyPerPlanet * shifted)*shifted;
}

int Universe::getNumberofOwnedUfos(Player* player)
{
    return player->getUfos().length();
}

int Universe::getNumberofOwnedPlanets(Player* player)
{
    int ownedPlanets = 0;
    foreach (SolarSystem* solarSystem, m_solarSystems)
    {
        foreach (Planet* planet, solarSystem->getPlanets())
        {
            if (planet->getOwnedBy() == player->getId())
                ownedPlanets++;
        }
    }
    return ownedPlanets;
}

QList<Ufo*> Universe::getUfosNearLocation(const QPointF& location, const Player& player)
{
    QList<Ufo*> result;
    foreach (Ufo* ufo, player.getUfos())
    {
        QPointF distanceDiff = ufo->getCoord() - location;
        double squaredDistance = std::pow(distanceDiff.x(), 2) + std::pow(distanceDiff.y(), 2);
        if ( squaredDistance < std::pow(256, 2) && !ufo->getInFight())
        {
            result << ufo;
        }
    }
    return result;
}


