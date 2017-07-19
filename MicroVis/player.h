#ifndef PLAYER_H
#define PLAYER_H

#include "spaceship.h"

#include <QObject>
#include <QQmlListProperty>

class Player : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QQmlListProperty<Spaceship> spaceships READ getSpaceships NOTIFY spaceshipsChanged)
    Q_PROPERTY(QString id MEMBER m_id NOTIFY idChanged)
public:
    explicit Player(QObject *parent = nullptr);
    Player(QString id, QObject *parent = nullptr);

    void setId(QString id);
    QString getId();

    Q_INVOKABLE void addSpaceship(int x, int y);
    Q_INVOKABLE void moveSpaceship(int index, int x, int y);
    QQmlListProperty<Spaceship> getSpaceships();

signals:
    void idChanged();
    void spaceshipsChanged();

public slots:

private:
    QString m_id;
    QList<Spaceship*> m_spaceships;
};

#endif // PLAYER_H
