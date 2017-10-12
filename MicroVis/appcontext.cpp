#include "appcontext.h"

#include <QFile>

AppContext::AppContext(QObject *parent) : QObject(parent)
{
}

void AppContext::addPlayer(int id, QString name, QColor color, double hue)
{
    m_players << new Player(id, name, color, hue);
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
}

void AppContext::moveBullet(int id, int x, int y)
{
    m_bulletMap[id]->move(x, y);
}

void AppContext::removeBullet(int id)
{
    if (!hasBullet(id))
        return;
    Bullet* bullet = m_bulletMap[id];
    m_bulletMap.remove(id);
    bullet->deleteLater();
}

int AppContext::getBulletCount() const
{
    return m_bullets.size();
}

void AppContext::clearBullets()
{
    auto bulletMap = m_bulletMap;
    m_bulletMap.clear();
    qDeleteAll(bulletMap);
}

bool AppContext::hasBullet(int id)
{
    return m_bulletMap.contains(id);
}

QQmlListProperty<Bullet> AppContext::getBullets()
{
    return QQmlListProperty<Bullet>(this, m_bullets);
}

void AppContext::reconstructBulletList()
{
    m_bullets.clear();
    for (auto bullet : m_bulletMap.keys())
    {
        m_bullets << m_bulletMap.value(bullet);
    }
    emit bulletsChanged();
}
