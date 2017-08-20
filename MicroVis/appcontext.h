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

    Q_INVOKABLE void addPlayer(QString id, QColor color);
    Q_INVOKABLE void clearPlayers();
    QQmlListProperty<Player> getPlayers();

    Q_INVOKABLE void addBullet(int x, int y);
    Q_INVOKABLE void moveBullet(int index, int x, int y);
    Q_INVOKABLE void removeBullet();
    Q_INVOKABLE int getBulletCount() const;
    Q_INVOKABLE void clearBullets();
    QQmlListProperty<Bullet> getBullets();

    Q_INVOKABLE void processFrame();

signals:
    void helloWorldChanged();
    void bulletsChanged();
    void playersChanged();

public slots:

private:
    QList<Player*> m_players;
    QList<Bullet*> m_bullets;
};

#endif // APPCONTEXT_H
