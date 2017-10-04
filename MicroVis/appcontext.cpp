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

void AppContext::addBullet(int id, int x, int y)
{
    m_bulletMap.insert(id, new Bullet(id, x, y, this));
    ReconstructBulletList();
    emit bulletsChanged();
}

void AppContext::moveBullet(int id, int x, int y)
{
    m_bulletMap[id]->move(x, y);
}

void AppContext::removeBullet(int id)
{
    Bullet* bullet = m_bulletMap[id];
    m_bulletMap.remove(id);
    ReconstructBulletList();
    emit bulletsChanged();
    bullet->deleteLater();
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

void AppContext::ReconstructBulletList()
{
    m_bullets.clear();
    for (auto bullet : m_bulletMap.keys())
    {
        m_bullets << m_bulletMap.value(bullet);
    }
}
