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
