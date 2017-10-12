#include "player.h"

Player::Player(QObject *parent) : QObject(parent),
    m_id(0), m_name(""), m_hue(0.0)
{}

Player::Player(int id, QString name, QColor color, double hue, QObject *parent) :
    QObject(parent), m_id(id), m_name(name), m_color(color), m_hue(hue)
{
}

Player::~Player()
{
    clearSpaceships();
}

void Player::setId(int id)
{
    m_id = id;
    emit idChanged();
}

void Player::setName(QString name)
{
    m_name = name;
}

QString Player::getName() const
{
    return m_name;
}

int Player::getId() const
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

void Player::setHue(double hue)
{
    m_hue = hue;
    emit hueChanged();
}

double Player::getHue() const
{
    return m_hue;
}

void Player::addSpaceship(int id, QString name, int x, int y)
{
    m_spaceships << new Spaceship(id, name, x, y);
    emit spaceshipsChanged();
}

void Player::moveSpaceship(int index, int x, int y)
{
    m_spaceships.at(index)->move(x, y);
}

void Player::removeSpaceship()
{
    Spaceship* spaceship = m_spaceships.back();
    m_spaceships.pop_back();
    emit spaceshipsChanged();
    spaceship->deleteLater();
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

bool Player::getSpaceshipIsAlive(int index)
{
    return m_spaceships.at(index)->getHp() > 0;
}
