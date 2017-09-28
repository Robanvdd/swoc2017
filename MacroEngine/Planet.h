#ifndef PLANET_H
#define PLANET_H

#include "GameObject.h"

#include <QObject>

class Planet : public GameObject
{
    Q_OBJECT
public:
    Planet(QString name, int orbitDistance, double orbitRotation, double orbitSpeed, QObject *parent = nullptr);
    void writeState(QJsonObject& gameState) const;
    void applyTick(double durationInSeconds);
    int getOwnedBy();

signals:

public slots:

private:
    QString m_name;
    int m_orbitDistance;
    double m_orbitRotation;
    double m_orbitSpeed;
    int m_ownedBy;
};

#endif // PLANET_H
