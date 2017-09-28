#include "Ufo.h"

#include <QJsonObject>

Ufo::Ufo(QObject *parent) : GameObject(parent)
{

}

void Ufo::applyTick(double durationInSeconds)
{
    // TODO
    Q_UNUSED(durationInSeconds)
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
