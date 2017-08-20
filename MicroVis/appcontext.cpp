#include "appcontext.h"

#include <QFile>

AppContext::AppContext(QObject *parent) : QObject(parent)
{
}

void AppContext::addPlayer(QString id, QColor color)
{
    m_players << new Player(id, color);
    emit playersChanged();
}

void AppContext::clearPlayers()
{
    auto backup = m_players;
    m_players.clear();
    emit playersChanged();
    qDeleteAll(backup);
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
    emit bulletsChanged();
}

void AppContext::removeBullet()
{
    delete m_bullets.back();
    m_bullets.pop_back();
    emit bulletsChanged();
}

int AppContext::getBulletCount() const
{
    return m_bullets.size();
}

void AppContext::clearBullets()
{
    auto backup = m_bullets;
    m_bullets.clear();
    emit bulletsChanged();
    qDeleteAll(backup);
}

QQmlListProperty<Bullet> AppContext::getBullets()
{
    return QQmlListProperty<Bullet>(this, m_bullets);
}

void AppContext::processFrame()
{

}
