#include "game.h"

Game::Game(QObject *parent) : GameObject(-1, parent)
{
}

Game::Game(int gameId, QObject* parent) : GameObject(gameId, parent)
{
}

Game::~Game()
{
    qDeleteAll(m_solarSystems);
    qDeleteAll(m_players);
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
    auto player = std::find_if(std::begin(m_players), std::end(m_players), [playerId](QObject* player)
    {
        return dynamic_cast<Player*>(player)->objectId() == playerId;
    });
    return player != std::end(m_players);
}

void Game::createPlayer(int playerId)
{
    m_players.append(new Player(playerId, this));
    emit playersChanged();
}

Player* Game::getPlayer(int playerId) const
{
    auto player = std::find_if(std::begin(m_players), std::end(m_players), [playerId](QObject* player)
    {
        return dynamic_cast<Player*>(player)->objectId() == playerId;
    });
    return (Player*) *player;
}

void Game::reset()
{
    qDeleteAll(m_solarSystems);
    m_solarSystems.clear();
    qDeleteAll(m_players);
    m_players.clear();
}

void Game::createSolarSystem(int solarSystemId)
{
    m_solarSystems.append(new SolarSystem(solarSystemId, this));
    emit solarSystemsChanged();
}
