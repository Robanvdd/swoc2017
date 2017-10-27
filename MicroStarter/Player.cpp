#include "Player.h"

Player::Player(QObject *parent) : QObject(parent)
{
}

Player::Player(QString name, QString bot, int nrOfUfos, double hue)
    : m_bot(bot)
    , m_nrUfos(nrOfUfos)
    , m_hue(hue)
    , m_name(name)
{
}

QString Player::getBot() const
{
    return m_bot;
}

void Player::setBot(const QString& bot)
{
    m_bot = bot;
    emit botChanged();
}

int Player::getNrUfos() const
{
    return m_nrUfos;
}

void Player::setNrUfos(int nrUfos)
{
    m_nrUfos = nrUfos;
    emit nrUfosChanged();
}

double Player::getHue() const
{
    return m_hue;
}

void Player::setHue(double hue)
{
    m_hue = hue;
    emit hueChanged();
}

QString Player::getName() const
{
    return m_name;
}

void Player::setName(const QString& name)
{
    m_name = name;
    emit nameChanged();
}
