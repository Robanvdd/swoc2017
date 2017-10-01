#include "UniverseBuilder.h"
#include <QMap>
#include <math.h>

UniverseBuilder::UniverseBuilder()
{
    std::random_device rd;
    m_randomGenerator = std::mt19937(rd());
}

Universe* UniverseBuilder::buildUniverse()
{
    std::uniform_int_distribution<> disSolarSystem(4, 9);
    auto nSolarSystems = disSolarSystem(m_randomGenerator);

    auto solarSystemLocations = buildSolarSystemLocations(nSolarSystems);

    QMap<int, SolarSystem*> solarSystems;
    for (int i = 0; i < nSolarSystems; i++)
    {
        auto solarSystem = buildSolarSystem(solarSystemLocations[i], "S" + QString::number(i) );
        solarSystems[solarSystem->getId()] = solarSystem;
    }

    return new Universe(solarSystems);
}

SolarSystem* UniverseBuilder::buildSolarSystem(QPoint location, QString name)
{
    std::uniform_int_distribution<> dis(5, 13);
    auto nPlanets = dis(m_randomGenerator);
    std::uniform_int_distribution<> shiftDis(-32, 32);
    std::uniform_int_distribution<> rotationDis(0, 360);
    std::uniform_real_distribution<> speedDis(0.9, 1.1);
    const int fastestPlanetRotation = 60;

    QMap<int, Planet*> planets;
    for (int i = 0; i < nPlanets; i++)
    {
        auto orbitDistance = (i+1)*96 + shiftDis(m_randomGenerator);
        auto orbitRotation = rotationDis(m_randomGenerator);
        auto orbitSpeed = (i+1)*fastestPlanetRotation*speedDis(m_randomGenerator);
        auto planet = new Planet(name + "P" + QString::number(i), orbitDistance, orbitRotation, orbitSpeed);
        planets[planet->getId()] = planet;
    }
    return new SolarSystem(name, location, planets);
}

int lengthSquared(QPoint p)
{
    return static_cast<int>(std::pow(p.x(), 2) + std::pow(p.y(), 2));
}

bool UniverseBuilder::overlapsWithOthers(QList<QPoint>& others, QPoint newLocation)
{
    for (auto otherIt = others.begin(); otherIt != others.end(); otherIt++)
    {
        if ( lengthSquared(*otherIt - newLocation) < std::pow(3000, 2) )
            return true;
    }
    return false;
}

QPoint getNewLocation(std::uniform_int_distribution<>& disSsLoc, std::mt19937& gen )
{
    return QPoint(disSsLoc(gen), disSsLoc(gen));
}

QList<QPoint> UniverseBuilder::buildSolarSystemLocations(int nSolarSystems)
{
    std::uniform_int_distribution<> disSsLoc(1500, 13500);
    QList<QPoint> result;
    for (auto i = 0; i < nSolarSystems; i++)
    {
        QPoint newLocation;
        do {
            newLocation = getNewLocation(disSsLoc, m_randomGenerator);
        } while (overlapsWithOthers(result, newLocation));
        result << newLocation;
    }
    return result;
}
