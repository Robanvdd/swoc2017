#ifndef UFO_H
#define UFO_H

#include "gameobject.h"
#include <QPointF>
#include <QColor>

class Ufo : public GameObject
{
    Q_OBJECT
    Q_PROPERTY(QString type MEMBER m_type NOTIFY typeChanged)
    Q_PROPERTY(bool inFight MEMBER m_inFight NOTIFY inFightChanged)
    Q_PROPERTY(QPointF coord MEMBER m_coord NOTIFY coordChanged)
    Q_PROPERTY(double hue MEMBER m_hue NOTIFY hueChanged)
    Q_PROPERTY(QColor color MEMBER m_color NOTIFY colorChanged)
public:
    explicit Ufo(QObject *parent = nullptr);
    explicit Ufo(int ufoId, QObject* parent = nullptr);

    QString getType() const;
    bool getInFight() const;
    QPointF getCoord() const;
    double getHue() const;
    QColor getColor() const;

signals:
    void typeChanged();
    void inFightChanged();
    void coordChanged();
    void hueChanged();
    void colorChanged();

public slots:
private:
    QString m_type;
    bool m_inFight;
    QPointF m_coord;
    double m_hue;
    QColor m_color;
};

#endif // UFO_H
