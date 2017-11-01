#ifndef PLANET_H
#define PLANET_H

#include "GameObject.h"

#include <QObject>
#include <QColor>
#include <QPoint>

class Player;
class Planet : public GameObject
{
    Q_OBJECT
public:
    Planet(QString name, int orbitDistance, double orbitRotation, double orbitSpeed, QObject *parent = nullptr);
    void writeState(QJsonObject& gameState) const;
    void applyTick(double durationInSeconds);
    int getOwnedBy() const;
    void takeOverBy(Player* player);

    double getOrbitRotation() const;

    int getOrbitDistance() const;

    QPointF getCartesianCoordinates() const;

signals:

public slots:

private:
    QString m_name;
    int m_orbitDistance;
    double m_orbitRotation;
    double m_orbitSpeed;
    int m_ownedBy;
    QColor m_color;
};

#endif // PLANET_H
