#ifndef PLAYER_H
#define PLAYER_H

#include "spaceship.h"

#include <QObject>
#include <QQmlListProperty>
#include <QColor>

class Player : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QQmlListProperty<Spaceship> spaceships READ getSpaceships NOTIFY spaceshipsChanged)
    Q_PROPERTY(QString id MEMBER m_id NOTIFY idChanged)
    Q_PROPERTY(QColor color MEMBER m_color NOTIFY colorChanged)
public:
    explicit Player(QObject *parent = nullptr);
    Player(QString id, QColor color, QObject *parent = nullptr);
    virtual ~Player();

    void setId(QString id);
    QString getId();

    void setColor(QColor color);
    QColor getColor();

    Q_INVOKABLE void addSpaceship(int x, int y);
    Q_INVOKABLE void moveSpaceship(int index, int x, int y);
    Q_INVOKABLE void removeSpaceship(int index);
    Q_INVOKABLE void clearSpaceships();
    QQmlListProperty<Spaceship> getSpaceships();

signals:
    void idChanged();
    void spaceshipsChanged();
    void colorChanged();

public slots:

private:
    QString m_id;
    QColor m_color;
    QList<Spaceship*> m_spaceships;
};

#endif // PLAYER_H
