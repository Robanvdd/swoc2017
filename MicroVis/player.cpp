#include "player.h"

Player::Player(QObject *parent) : QObject(parent)
{

}

Player::Player(QString id, QColor color, QObject *parent) :
    QObject(parent), m_id(id), m_color(color)
{}

void Player::setId(QString id)
{
    m_id = id;
    emit idChanged();
}

QString Player::getId()
{
    return m_id;
}

void Player::setColor(QColor color)
{
    m_color = color;
    emit colorChanged();
}

QColor Player::getColor()
{
    return m_color;
}

void Player::addSpaceship(int x, int y)
{
    m_spaceships << new Spaceship(x, y);
    emit spaceshipsChanged();
}

void Player::moveSpaceship(int index, int x, int y)
{
    m_spaceships.at(index)->move(x, y);
    emit spaceshipsChanged();
}

QQmlListProperty<Spaceship> Player::getSpaceships()
{
    return QQmlListProperty<Spaceship>(this, m_spaceships);
}
