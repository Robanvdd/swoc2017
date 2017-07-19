#include "spaceship.h"

Spaceship::Spaceship(QObject *parent) :
    QObject(parent), m_x(0), m_y(0)
{}

Spaceship::Spaceship(int x, int y, QObject *parent) :
    QObject(parent), m_x(x), m_y(y)
{}

void Spaceship::move(int x, int y)
{
    m_x = x;
    m_y = y;
    emit xChanged();
    emit yChanged();
}
