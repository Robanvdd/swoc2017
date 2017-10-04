#include "bullet.h"

Bullet::Bullet(QObject *parent) :
    QObject(parent)
{
}

Bullet::Bullet(int id, int x, int y, QObject *parent) :
    QObject(parent), m_id(id), m_x(x), m_y(y)
{
}

int Bullet::x() const
{
    return m_x;
}

void Bullet::setX(int x)
{
    m_x = x;
    emit xChanged();
}

int Bullet::y() const
{
    return m_y;
}

void Bullet::setY(int y)
{
    m_y = y;
    emit yChanged();
}

void Bullet::move(int x, int y)
{
    m_x = x;
    m_y = y;
    emit xChanged();
    emit yChanged();
}
