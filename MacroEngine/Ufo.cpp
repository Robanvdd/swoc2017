#include "Ufo.h"

#include <QJsonObject>
#include <QVector2D>

Ufo::Ufo(QObject *parent)
    : GameObject(parent)
    , m_inFight(false)
    , m_FlyingToPlanet(false)
    , m_FlyingToCoord(false)
    , m_speedInUnitsPerSecond(128.0)
    , m_epsilon(24)
{
}

void Ufo::applyTick(double durationInSeconds)
{
    if (m_FlyingToCoord)
    {
        QVector2D directionVector(m_targetCoord - m_coord);
        if (directionVector.lengthSquared() < 32*32)
            return;
        directionVector.normalize();
        directionVector *= (m_speedInUnitsPerSecond * durationInSeconds);
        m_coord += directionVector.toPointF();
    }
    else if (m_FlyingToPlanet)
    {
        auto solarSystem = m_universe->getCorrespondingSolarSystem(m_targetPlanet);
        auto targetCoord = solarSystem->getPlanetLocation(*m_targetPlanet);
        QVector2D directionVector(targetCoord - m_coord);
        if (directionVector.lengthSquared() < 32*32)
            return;
        directionVector.normalize();
        directionVector *= (m_speedInUnitsPerSecond * durationInSeconds);
        m_coord += directionVector.toPointF();
    }
}

void Ufo::writeState(QJsonObject& gameState)
{
    gameState["id"] = m_id;
    gameState["inFight"] = m_inFight;
    QJsonObject coords;
    coords["x"] = m_coord.x();
    coords["y"] = m_coord.y();
    gameState["coord"] = coords;
}

QPointF Ufo::getCoord() const
{
    return m_coord;
}

bool Ufo::getInFight() const
{
    return m_inFight;
}

void Ufo::setInFight(bool inFight)
{
    m_inFight = inFight;
}

void Ufo::setUniverse(Universe* universe)
{
    m_universe = universe;
}

void Ufo::setTargetPlanet(Planet* targetPlanet)
{
    m_targetPlanet = targetPlanet;
}

void Ufo::setTargetCoord(const QPoint& targetCoord)
{
    m_targetCoord = targetCoord;
}

void Ufo::setFlyingToCoord(bool FlyingToCoord)
{
    m_FlyingToCoord = FlyingToCoord;
    m_FlyingToPlanet = !FlyingToCoord;
}

void Ufo::setFlyingToPlanet(bool FlyingToPlanet)
{
    m_FlyingToPlanet = FlyingToPlanet;
    m_FlyingToCoord = !FlyingToPlanet;
}

void Ufo::setCoord(const QPointF& coord)
{
    m_coord = coord;
}
