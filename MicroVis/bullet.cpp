#include "bullet.h"

Bullet::Bullet(QObject *parent) :
    QObject(parent), m_id(0), m_x(0), m_y(0)
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

int Bullet::id() const
{
    return m_id;
}

void Bullet::setId(int id)
{
    m_id = id;
}

void Bullet::move(int x, int y)
{
    m_x = x;
    m_y = y;
    emit xChanged();
    emit yChanged();
}
