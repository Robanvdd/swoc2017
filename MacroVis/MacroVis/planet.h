#ifndef PLANET_H
#define PLANET_H

#include <QColor>
#include <QObject>
#include <gameobject.h>

class Planet : public GameObject
{
    Q_OBJECT
    Q_PROPERTY(QString name MEMBER m_name NOTIFY nameChanged)
    Q_PROPERTY(double orbitDistance MEMBER m_orbitDistance NOTIFY orbitDistanceChanged)
    Q_PROPERTY(double orbitRotation MEMBER m_orbitRotation NOTIFY orbitRotationChanged)
    Q_PROPERTY(int ownedBy MEMBER m_ownedBy NOTIFY ownedByChanged)
    Q_PROPERTY(QColor color MEMBER m_color NOTIFY colorChanged)
public:
    explicit Planet(QObject *parent = 0);
    explicit Planet(int planetId, QObject *parent = 0);

signals:
    void nameChanged();
    void orbitDistanceChanged();
    void orbitRotationChanged();
    void ownedByChanged();
    void colorChanged();

public slots:
private:
    QString m_name;
    double m_orbitDistance;
    double m_orbitRotation;
    int m_ownedBy;
    QColor m_color;
};

#endif // PLANET_H
