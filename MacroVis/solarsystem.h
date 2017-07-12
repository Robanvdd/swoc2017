#ifndef SOLARSYSTEM_H
#define SOLARSYSTEM_H

#include <QObject>
#include <QVector2D>

#include "gameobject.h"
#include "planet.h"

class SolarSystem : public GameObject
{
    Q_OBJECT
    Q_PROPERTY(QString name MEMBER m_name NOTIFY nameChanged)
    Q_PROPERTY(QVector2D coords MEMBER m_coords NOTIFY coordsChanged)
    Q_PROPERTY(QList<QObject*> planets MEMBER m_planets NOTIFY planetsChanged)
public:
    explicit SolarSystem(QObject *parent = 0);
    explicit SolarSystem(int id, QObject *parent = 0);

    Q_INVOKABLE bool planetExists(int planetId) const;
    Q_INVOKABLE void createPlanet(int planetId);
    Q_INVOKABLE Planet* getPlanet(int planetId) const;

signals:
    void nameChanged();
    void coordsChanged();
    void planetsChanged();
public slots:
private:
    QString m_name;
    QVector2D m_coords;
    QList<QObject*> m_planets;
};

#endif // SOLARSYSTEM_H
