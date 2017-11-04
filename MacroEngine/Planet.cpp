#include "Planet.h"
#include "Player.h"

#include <QJsonObject>
#include <QColor>
#define _USE_MATH_DEFINES
#include <math.h>


Planet::Planet(QString name, int orbitDistance, double orbitRotation, double orbitSpeed, QObject *parent)
    : GameObject(parent)
    , m_name(name)
    , m_orbitDistance(orbitDistance)
    , m_orbitRotation(orbitRotation)
    , m_orbitSpeed(orbitSpeed)
    , m_ownedBy(-1)
{
}

void Planet::writeState(QJsonObject& gameState) const
{
    gameState["id"] = m_id;
    gameState["name"] = m_name;
    gameState["orbitDistance"] = m_orbitDistance;
    gameState["orbitRotation"] = m_orbitRotation;
    gameState["ownedBy"] = m_ownedBy;
    gameState["color"] = m_color.name(QColor::HexArgb);
}

void Planet::applyTick(double durationInSeconds)
{
    m_orbitRotation += 360.0*durationInSeconds / m_orbitSpeed;
}

int Planet::getOwnedBy() const
{
    return m_ownedBy;
}

void Planet::takeOverBy(Player* player)
{
    if (player)
    {
        m_ownedBy = player->getId();
        m_color = player->getColor();
    }
}

double Planet::getOrbitRotation() const
{
    return m_orbitRotation;
}

int Planet::getOrbitDistance() const
{
    return m_orbitDistance;
}

QPointF Planet::getCartesianCoordinates() const
{
    return QPointF(m_orbitDistance * cos( M_PI * (m_orbitRotation / 180.0)), -m_orbitDistance * sin(M_PI * (m_orbitRotation/ 180.0)));
}
