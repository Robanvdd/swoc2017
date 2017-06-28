#include "appcontext.h"

#include <QFile>

AppContext::AppContext(QObject *parent) : QObject(parent),
    m_helloWorld("Hello, world!")
{
    m_spaceships << new Spaceship(100, 200, this) << new Spaceship(200, 300, this);
    m_bullets << new Bullet(50, 50, this) << new Bullet(300, 200, this);
}

void AppContext::addSpaceship(int x, int y)
{
    m_spaceships << new Spaceship(x, y, this);
    emit spaceshipsChanged();
}

QQmlListProperty<Spaceship> AppContext::getSpaceships()
{
    return QQmlListProperty<Spaceship>(this, m_spaceships);
}

void AppContext::addBullet(int x, int y)
{
    m_bullets << new Bullet(x, y, this);
    emit bulletsChanged();
}

void AppContext::processFrame()
{
    for (auto spaceship : m_spaceships)
    {
        spaceship->setX(spaceship->x() + 1);
        if (spaceship->x() > 600)
        {
            spaceship->setDead(true);
        }
    }

    for (auto bullet : m_bullets)
    {
        bullet->setX(bullet->x() - 1);
        bullet->setY(bullet->y() + 1);
    }
}
