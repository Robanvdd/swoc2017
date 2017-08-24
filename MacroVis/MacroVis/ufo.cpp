#include "ufo.h"

Ufo::Ufo(QObject* parent) : GameObject(-1, parent)
{
}

Ufo::Ufo(int ufoId, QObject* parent) : GameObject(ufoId, parent)
{
}
