#include "Player.h"

#include <QJsonArray>
#include <QJsonObject>

Player::Player(QString name, QObject *parent)
    : GameObject(parent)
    , m_name(name)
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
    QJsonArray ufoArray;
    foreach (Ufo* ufo, m_ufos) {
        QJsonObject ufoObject;
        ufo->writeState(ufoObject);
        ufoArray.append(ufoObject);
    }
    gameState["ufos"] = ufoArray;
}
