#include "planet.h"

Planet::Planet(QObject *parent) : GameObject(-1, parent)
{
}

Planet::Planet(int planetId, QObject* parent) : GameObject(planetId, parent)
{
}
