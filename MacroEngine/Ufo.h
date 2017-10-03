#ifndef UFO_H
#define UFO_H

#include "GameObject.h"
#include "Planet.h"
#include "Universe.h"

#include <QObject>
#include <QPoint>

class Ufo : public GameObject
{
    Q_OBJECT
public:
    explicit Ufo(QObject *parent = nullptr);

    void applyTick(double durationInSeconds);
    void writeState(QJsonObject& gameState);

    QPointF getCoord() const;

    bool getInFight() const;

    void setInFight(bool inFight);

    void setUniverse(Universe* universe);

    void setTargetPlanet(Planet* targetPlanet);

    void setTargetCoord(const QPoint& targetCoord);

    void setFlyingToCoord(bool FlyingToCoord);

    void setFlyingToPlanet(bool FlyingToPlanet);

    void setCoord(const QPointF& coord);

signals:

public slots:

private:
    bool m_inFight;
    QPointF m_coord;
    bool m_FlyingToPlanet;
    bool m_FlyingToCoord;
    QPointF m_targetCoord;
    Planet* m_targetPlanet;
    Universe* m_universe;
    const double m_speedInUnitsPerSecond;
    const double m_epsilon;
};

#endif // UFO_H
