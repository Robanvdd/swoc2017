#ifndef SPACESHIP_H
#define SPACESHIP_H

#include <QObject>

class Spaceship : public QObject
{
    Q_OBJECT
    Q_PROPERTY(int x MEMBER m_x NOTIFY xChanged)
    Q_PROPERTY(int y MEMBER m_y NOTIFY yChanged)
    Q_PROPERTY(bool dead MEMBER m_dead NOTIFY deadChanged)
public:
    explicit Spaceship(QObject *parent = 0);
    Spaceship(int x, int y, QObject *parent = 0);

    int x() const;
    void setX(int x);

    int y() const;
    void setY(int y);

    bool dead() const;
    void setDead(bool dead);

signals:
    void xChanged();
    void yChanged();
    void deadChanged();

public slots:

private:
    int m_x;
    int m_y;
    bool m_dead;
};

#endif // SPACESHIP_H
