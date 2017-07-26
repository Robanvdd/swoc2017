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
    Q_PROPERTY(QString helloWorld MEMBER m_helloWorld NOTIFY helloWorldChanged)
    Q_PROPERTY(QQmlListProperty<Spaceship> spaceships READ getSpaceships NOTIFY spaceshipsChanged)
    Q_PROPERTY(QQmlListProperty<Bullet> bullets READ getBullets NOTIFY bulletsChanged)
    Q_PROPERTY(QQmlListProperty<Player> players READ getPlayers NOTIFY playersChanged)

public:
    explicit AppContext(QObject *parent = nullptr);

    Q_INVOKABLE void addSpaceship(int x, int y);
    QQmlListProperty<Spaceship> getSpaceships();

    Q_INVOKABLE void addPlayer(QString id, QColor color);
    QQmlListProperty<Player> getPlayers();

    Q_INVOKABLE void addBullet(int x, int y);
    Q_INVOKABLE void moveBullet(int index, int x, int y);
    QQmlListProperty<Bullet> getBullets();

    Q_INVOKABLE void processFrame();

signals:
    void helloWorldChanged();
    void spaceshipsChanged();
    void bulletsChanged();
    void playersChanged();

public slots:

private:
    QString m_helloWorld;
    QList<Player*> m_players;
    QList<Spaceship*> m_spaceships;
    QList<Bullet*> m_bullets;
};

#endif // APPCONTEXT_H
