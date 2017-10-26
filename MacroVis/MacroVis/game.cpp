#include "game.h"

Game::Game(QObject *parent)
    : Game(-1, parent)
{
}

Game::Game(int gameId, QObject* parent)
    : GameObject(gameId, parent)
{
    m_players = new PlayersModel(this);
}

Game::~Game()
{
    qDeleteAll(m_solarSystems);
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

bool Game::playerExists(int playerId) const
{
    return m_players->PlayerExists(playerId);
}

void Game::createPlayer(int playerId)
{
    m_players->CreatePlayer(playerId);
    emit playersChanged();
}

Player* Game::getPlayer(int playerId) const
{
    return m_players->GetPlayer(playerId);
}

void Game::reset()
{
    qDeleteAll(m_solarSystems);
    m_solarSystems.clear();
    m_players->Clear();
}

void Game::createSolarSystem(int solarSystemId)
{
    m_solarSystems.append(new SolarSystem(solarSystemId, this));
    emit solarSystemsChanged();
}
