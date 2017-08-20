#include "game.h"

Game::Game(QObject *parent) : GameObject(-1, parent)
{
}

Game::Game(int gameId, QObject* parent) : GameObject(gameId, parent)
{
}

bool Game::solarSystemExists(int solarSystemId) const
{
    auto solarSystem = std::find_if(std::begin(m_solarSystems), std::end(m_solarSystems), [solarSystemId](QObject* solarSystem)
    {
        return dynamic_cast<SolarSystem*>(solarSystem)->objectId() == solarSystemId;
    });
    return solarSystem != std::end(m_solarSystems);
}

SolarSystem *Game::getSolarSystem(int solarSystemId) const
{
    auto solarSystem = std::find_if(std::begin(m_solarSystems), std::end(m_solarSystems), [solarSystemId](QObject* solarSystem)
    {
        return dynamic_cast<SolarSystem*>(solarSystem)->objectId() == solarSystemId;
    });
    if (solarSystem != std::end(m_solarSystems))
        return (SolarSystem*) *solarSystem;
    else
        return nullptr;
}

void Game::reset()
{
    qDeleteAll(m_solarSystems);
    m_solarSystems.clear();
}

void Game::createSolarSystem(int solarSystemId)
{
    m_solarSystems.append(new SolarSystem(solarSystemId, this));
    emit solarSystemsChanged();
}
