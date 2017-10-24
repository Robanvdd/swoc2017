#include "player.h"

#include <QDebug>

Player::Player(QObject *parent) : GameObject(-1, parent)
{   
}

Player::Player(int playerId, QObject* parent) : GameObject(playerId, parent)
{
}

Player::~Player()
{
    qDeleteAll(m_ufos);
}

bool Player::ufoExists(int ufoId) const
{
    auto ufo = std::find_if(std::begin(m_ufos), std::end(m_ufos), [ufoId](QObject* ufo)
    {
        return dynamic_cast<Ufo*>(ufo)->objectId() == ufoId;
    });
    return ufo != std::end(m_ufos);
}

void Player::createUfo(int ufoId)
{
    m_ufos.append(new Ufo(ufoId, this));
    emit ufosChanged();
}

Ufo* Player::getUfo(int ufoId) const
{
    auto ufo = std::find_if(std::begin(m_ufos), std::end(m_ufos), [ufoId](QObject* ufo)
    {
        return dynamic_cast<Ufo*>(ufo)->objectId() == ufoId;
    });
    return (Ufo*) *ufo;
}

void Player::destroyUfo(int ufoId)
{
    if (ufoExists(ufoId))
    {
        auto ufo = getUfo(ufoId);
        m_ufos.removeOne(ufo);
        emit ufosChanged();
        ufo->deleteLater();
    }
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
