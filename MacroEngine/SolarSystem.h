#ifndef SOLARSYSTEM_H
#define SOLARSYSTEM_H

#include "Planet.h"

#include <QObject>
#include <QPoint>

class SolarSystem : public GameObject
{
    Q_OBJECT
public:
    explicit SolarSystem(QString name, QPoint coord, QList<Planet*> planets, QObject *parent = nullptr);
    void writeState(QJsonObject& gameState) const;
    void applyTick(double durationInSeconds);
    QList<Planet*> getPlanets();

signals:

public slots:
private:
    QString m_name;
    QPoint m_coord;
    QList<Planet*> m_planets;
};

#endif // SOLARSYSTEM_H
