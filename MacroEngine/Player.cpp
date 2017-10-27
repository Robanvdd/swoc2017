#include "Player.h"
#include "Ufo.h"

#include <QJsonArray>
#include <QJsonObject>

Player::Player(QString name, int hue, QObject *parent)
    : GameObject(parent)
    , m_credits(0)
    , m_name(name)
    , m_color(QColor::fromHsl(hue, 255, 127).toRgb())
    , m_hue(hue / 359.0)
{
}

double Player::getCredits() const
{
    return m_credits;
}

void Player::addCredits(double credits)
{
    m_credits += credits;
}

void Player::removeCredits(double credits)
{
    m_credits -= credits;
}

void Player::giveUfo(Ufo* ufo)
{
    ufo->setParent(this);
    m_ufos << ufo;
}

void Player::removeUfo(Ufo* ufo)
{
    m_ufos.removeOne(ufo);
    ufo->deleteLater();
}

bool Player::hasUfo(int id) const
{
    foreach (auto ufo, m_ufos)
    {
        if (ufo->getId() == id)
            return true;
    }
    return false;
}

Ufo* Player::getUfo(int id) const
{
    foreach (auto ufo, m_ufos)
    {
        if (ufo->getId() == id)
            return ufo;
    }
    throw std::logic_error("Player does not have ufo requested ufo");
}

void Player::applyTick(double durationInSeconds)
{
    foreach (Ufo* ufo, m_ufos) {
        ufo->applyTick(durationInSeconds);
    }
}

QString Player::getName() const
{
    return m_name;
}

void Player::writeState(QJsonObject& gameState)
{
    gameState["id"] = m_id;
    gameState["name"] = m_name;
    gameState["credits"] = m_credits;
    gameState["color"] = m_color.name(QColor::HexArgb);
    gameState["hue"] = m_hue;
    QJsonArray ufoArray;
    foreach (Ufo* ufo, m_ufos) {
        QJsonObject ufoObject;
        ufo->writeState(ufoObject);
        ufoArray.append(ufoObject);
    }
    gameState["ufos"] = ufoArray;
}

QList<Ufo*> Player::getUfos() const
{
    return m_ufos;
}

QString Player::getColorName()
{
    return m_color.name(QColor::HexArgb);
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
}
