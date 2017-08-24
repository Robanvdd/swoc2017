#ifndef UFO_H
#define UFO_H

#include "gameobject.h"
#include <QPointF>

class Ufo : public GameObject
{
    Q_OBJECT
    Q_PROPERTY(QString type MEMBER m_type NOTIFY typeChanged)
    Q_PROPERTY(bool inFight MEMBER m_inFight NOTIFY inFightChanged)
    Q_PROPERTY(QPointF coord MEMBER m_coord NOTIFY coordChanged)
public:
    explicit Ufo(QObject *parent = nullptr);
    explicit Ufo(int ufoId, QObject* parent = nullptr);

signals:
    void typeChanged();
    void inFightChanged();
    void coordChanged();

public slots:
private:
    QString m_type;
    bool m_inFight;
    QPointF m_coord;
};

#endif // UFO_H
