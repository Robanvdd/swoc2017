#include "SolarSystem.h"

#include <QJsonArray>
#include <QJsonObject>

SolarSystem::SolarSystem(QString name, QPoint coord, QList<Planet *> planets, QObject *parent)
    : GameObject(parent)
    , m_name(name)
    , m_coord(coord)
    , m_planets(planets)
{
    for (auto planetIt = m_planets.begin(); planetIt != m_planets.end(); planetIt++)
    {
        (*planetIt)->setParent(this);
    }
}

void SolarSystem::writeState(QJsonObject& gameState) const
{
    gameState["id"] = m_id;
    gameState["name"] = m_name;
    QJsonObject coords;
    coords["x"] = m_coord.x();
    coords["y"] = m_coord.y();
    gameState["coords"] = coords;
    QJsonArray planetArray;
    foreach (const Planet* planet, m_planets) {
        QJsonObject planetObject;
        planet->writeState(planetObject);
        planetArray.append(planetObject);
    }
    gameState["planets"] = planetArray;
}

void SolarSystem::applyTick(double durationInSeconds)
{
    foreach (Planet* planet, m_planets) {
        planet->applyTick(durationInSeconds);
    }
}

QList<Planet*> SolarSystem::getPlanets()
{
    return m_planets;
}
