#ifndef APPCONTEXT_H
#define APPCONTEXT_H

#include <QObject>
#include <QQmlListProperty>
#include "spaceship.h"
#include "bullet.h"

class AppContext : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString helloWorld MEMBER m_helloWorld NOTIFY helloWorldChanged)
    Q_PROPERTY(QQmlListProperty<Spaceship> spaceships READ getSpaceships NOTIFY spaceshipsChanged)
    Q_PROPERTY(QQmlListProperty<Bullet> bullets READ getBullets NOTIFY bulletsChanged)

public:
    explicit AppContext(QObject *parent = 0);

    Q_INVOKABLE void addSpaceship(int x, int y);
    QQmlListProperty<Spaceship> getSpaceships();

    Q_INVOKABLE void addBullet(int x, int y);
    QQmlListProperty<Bullet> getBullets();

    Q_INVOKABLE void processFrame();

signals:
    void helloWorldChanged();
    void spaceshipsChanged();
    void bulletsChanged();

public slots:

private:
    QString m_helloWorld;
    QList<Spaceship*> m_spaceships;
    QList<Bullet*> m_bullets;
};

#endif // APPCONTEXT_H
