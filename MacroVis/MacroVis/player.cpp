#include "player.h"

#include <QDebug>

Player::Player(QObject *parent)
    : Player(-1, parent)
{
}

Player::Player(int playerId, QObject* parent)
    : GameObject(playerId, parent)
{
    m_ufos = new UfosModel(this);
    emit ufosChanged();
}

Player::~Player()
{
}

bool Player::ufoExists(int ufoId) const
{
    return m_ufos->ufoExists(ufoId);
}

void Player::createUfo(int ufoId)
{
    m_ufos->createUfo(ufoId);
}

Ufo* Player::getUfo(int ufoId) const
{
    return m_ufos->getUfo(ufoId);
}

void Player::destroyUfo(int ufoId)
{
    m_ufos->destroyUfo(ufoId);
}

void Player::onlyKeepUfos(const QList<int> ufosToKeep)
{
    m_ufos->onlyKeepUfos(ufosToKeep);
}

double Player::getHue() const
{
    return m_hue;
}

QColor Player::getColor() const
{
    return m_color;
}

void Player::setColor(const QColor& color)
{
    m_color = color;
    emit colorChanged();
}

QString Player::getName() const
{
    return m_name;
}

int Player::getCredits() const
{
    return m_credits;
}

UfosModel* Player::getUfos()
{
    return m_ufos;
}
