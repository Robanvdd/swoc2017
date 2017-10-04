#ifndef APPCONTEXT_H
#define APPCONTEXT_H

#include <QObject>
#include <QQmlListProperty>
#include "spaceship.h"
#include "bullet.h"
#include "player.h"

class AppContext : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QQmlListProperty<Bullet> bullets READ getBullets NOTIFY bulletsChanged)
    Q_PROPERTY(QQmlListProperty<Player> players READ getPlayers NOTIFY playersChanged)

public:
    explicit AppContext(QObject *parent = nullptr);

    Q_INVOKABLE void addPlayer(int id, QString name, QColor color);
    Q_INVOKABLE void clearPlayers();
    QQmlListProperty<Player> getPlayers();

    Q_INVOKABLE void addBullet(int id, int x, int y);
    Q_INVOKABLE void moveBullet(int id, int x, int y);
    Q_INVOKABLE void removeBullet(int id);
    Q_INVOKABLE int getBulletCount() const;
    Q_INVOKABLE void clearBullets();
    Q_INVOKABLE bool hasBullet(int id);
    QQmlListProperty<Bullet> getBullets();

signals:
    void helloWorldChanged();
    void bulletsChanged();
    void playersChanged();

public slots:

private:
    void ReconstructBulletList();

    QList<Player*> m_players;
    QMap<int, Bullet*> m_bulletMap;
    QList<Bullet*> m_bullets;
};

#endif // APPCONTEXT_H
