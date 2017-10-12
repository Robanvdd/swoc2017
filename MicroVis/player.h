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
    Q_PROPERTY(int id MEMBER m_id NOTIFY idChanged)
    Q_PROPERTY(QString name MEMBER m_name NOTIFY nameChanged)
    Q_PROPERTY(QColor color MEMBER m_color NOTIFY colorChanged)
    Q_PROPERTY(double hue MEMBER m_hue NOTIFY hueChanged)
public:
    explicit Player(QObject *parent = nullptr);
    Player(int id, QString name, QColor color, double hue, QObject *parent = nullptr);
    virtual ~Player();

    void setId(int id);
    int getId() const;

    void setName(QString name);
    QString getName() const;

    void setColor(QColor color);
    QColor getColor();

    void setHue(double hue);
    double getHue() const;

    Q_INVOKABLE void addSpaceship(int id, QString name, int x, int y);
    Q_INVOKABLE void moveSpaceship(int index, int x, int y);
    Q_INVOKABLE void removeSpaceship();
    Q_INVOKABLE int getSpaceshipCount() const;
    Q_INVOKABLE void clearSpaceships();
    QQmlListProperty<Spaceship> getSpaceships();
    Q_INVOKABLE void setSpaceshipHp(int index, int hp);
    Q_INVOKABLE bool getSpaceshipIsAlive(int index);

signals:
    void idChanged();
    void spaceshipsChanged();
    void colorChanged();
    void nameChanged();
    void hueChanged();

public slots:

private:
    int m_id;
    QString m_name;
    QColor m_color;
    double m_hue;
    QList<Spaceship*> m_spaceships;
};

#endif // PLAYER_H
