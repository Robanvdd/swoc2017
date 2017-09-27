#include "player.h"

Player::Player(QObject *parent) : QObject(parent)
{}

Player::Player(QString id, QColor color, QObject *parent) :
    QObject(parent), m_id(id), m_color(color)
{
}

Player::~Player()
{
    clearSpaceships();
}

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

void Player::removeSpaceship()
{
    delete m_spaceships.back();
    m_spaceships.pop_back();
    emit spaceshipsChanged();
}

int Player::getSpaceshipCount() const
{
    return m_spaceships.size();
}

void Player::clearSpaceships()
{
    auto backup = m_spaceships;
    m_spaceships.clear();
    emit spaceshipsChanged();
    qDeleteAll(backup);
}

QQmlListProperty<Spaceship> Player::getSpaceships()
{
    return QQmlListProperty<Spaceship>(this, m_spaceships);
}

void Player::setSpaceshipHp(int index, int hp)
{
    m_spaceships.at(index)->setHp(hp);
}
