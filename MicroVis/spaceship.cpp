#include "spaceship.h"

Spaceship::Spaceship(QObject *parent) :
    QObject(parent), m_x(0), m_y(0), m_dead(false)
{

}

Spaceship::Spaceship(int x, int y, QObject *parent) :
    QObject(parent), m_x(x), m_y(y), m_dead(false)
{

}

int Spaceship::x() const
{
    return m_x;
}

void Spaceship::setX(int x)
{
    m_x = x;
    emit xChanged();
}

int Spaceship::y() const
{
    return m_y;
}

void Spaceship::setY(int y)
{
    m_y = y;
    emit yChanged();
}

void Spaceship::move(int x, int y)
{
    m_x = x;
    m_y = y;
    emit xChanged();
    emit yChanged();
}

bool Spaceship::dead() const
{
    return m_dead;
}

void Spaceship::setDead(bool dead)
{
    m_dead = dead;
    emit deadChanged();
}
