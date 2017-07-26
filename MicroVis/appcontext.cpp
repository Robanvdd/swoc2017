#include "appcontext.h"

#include <QFile>

AppContext::AppContext(QObject *parent) : QObject(parent),
    m_helloWorld("Hello, world!")
{
    m_spaceships << new Spaceship(100, 200, this) << new Spaceship(200, 300, this);
    m_spaceships << new Spaceship(800, 550, this) << new Spaceship(400, 400, this);
    m_bullets << new Bullet(650, 500, this) << new Bullet(300, 200, this);
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

void AppContext::addPlayer(QString id, QColor color)
{
    m_players << new Player(id, color);
    emit playersChanged();
}

QQmlListProperty<Player> AppContext::getPlayers()
{
    return QQmlListProperty<Player>(this, m_players);
}

void AppContext::addBullet(int x, int y)
{
    m_bullets << new Bullet(x, y, this);
    emit bulletsChanged();
}

void AppContext::moveBullet(int index, int x, int y)
{
    m_bullets.at(index)->move(x, y);
}

QQmlListProperty<Bullet> AppContext::getBullets()
{
    return QQmlListProperty<Bullet>(this, m_bullets);
}

void AppContext::processFrame()
{

}
