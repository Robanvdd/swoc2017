#ifndef UFO_H
#define UFO_H

#include "GameObject.h"

#include <QObject>
#include <QPoint>

class Ufo : public GameObject
{
    Q_OBJECT
public:
    explicit Ufo(QObject *parent = nullptr);

    void applyTick(double durationInSeconds);
    void writeState(QJsonObject& gameState);

    QPoint getCoord() const;

    bool getInFight() const;

    void setInFight(bool inFight);

signals:

public slots:

private:
    bool m_inFight;
    QPoint m_coord;
};

#endif // UFO_H
