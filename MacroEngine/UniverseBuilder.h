#ifndef UNIVERSEBUILDER_H
#define UNIVERSEBUILDER_H

#include "Universe.h"
#include <random>
#include <QPoint>

class UniverseBuilder
{
public:
    UniverseBuilder();
    Universe* buildUniverse();

signals:

public slots:

private:
    SolarSystem* buildSolarSystem(QPoint location, QString name);
    QList<QPoint> buildSolarSystemLocations(int nSolarSystems);
    std::mt19937 m_randomGenerator;
    bool overlapsWithOthers(QList<QPoint> &others, QPoint newLocation);
};

#endif // UNIVERSEBUILDER_H
