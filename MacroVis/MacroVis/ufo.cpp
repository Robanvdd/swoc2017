#include "ufo.h"
#include "player.h"

Ufo::Ufo(QObject* parent) : GameObject(-1, parent)
{
}

Ufo::Ufo(int ufoId, QObject* parent)
    : GameObject(ufoId, parent)
    , m_hue( ((Player*) parent)->getHue())
    , m_color( ((Player*) parent)->getColor())
{
}

QString Ufo::getType() const
{
    return m_type;
}

bool Ufo::getInFight() const
{
   return m_inFight;
}

QPointF Ufo::getCoord() const
{
   return m_coord;
}

double Ufo::getHue() const
{
     return m_hue;
}

QColor Ufo::getColor() const
{
    return m_color;
}
