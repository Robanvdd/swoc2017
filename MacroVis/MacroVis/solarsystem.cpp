#include "solarsystem.h"

SolarSystem::SolarSystem(QObject *parent) : GameObject(-1, parent)
{
}

SolarSystem::SolarSystem(int id, QObject *parent) : GameObject(id, parent)
{
}

SolarSystem::~SolarSystem()
{
    qDeleteAll(m_planets);
}

bool SolarSystem::planetExists(int planetId) const
{
    auto planet = std::find_if(std::begin(m_planets), std::end(m_planets), [planetId](QObject* planet)
    {
        return dynamic_cast<Planet*>(planet)->objectId() == planetId;
    });
    return planet != std::end(m_planets);
}

void SolarSystem::createPlanet(int planetId)
{
    m_planets.append(new Planet(planetId, this));
    emit planetsChanged();
}

Planet* SolarSystem::getPlanet(int planetId) const
{
    auto planet = std::find_if(std::begin(m_planets), std::end(m_planets), [planetId](QObject* planet)
    {
        return dynamic_cast<Planet*>(planet)->objectId() == planetId;
    });
    return (Planet*) *planet;
}
