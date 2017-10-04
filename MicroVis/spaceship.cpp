#include "spaceship.h"

Spaceship::Spaceship(QObject *parent) :
    QObject(parent), m_id(0), m_name(""), m_x(0), m_y(0)
{}

int Spaceship::getId() const
{
    return m_id;
}

void Spaceship::setId(int id)
{
    m_id = id;
}

QString Spaceship::getName() const
{
    return m_name;
}

void Spaceship::setName(QString name)
{
    m_name = name;
}

Spaceship::Spaceship(int id, QString name, int x, int y, QObject *parent) :
    QObject(parent), m_id(id), m_name(name), m_x(x), m_y(y)
{}

void Spaceship::move(int x, int y)
{
    m_x = x;
    m_y = y;
    emit xChanged();
    emit yChanged();
}

void Spaceship::setHp(int hp)
{
    m_hp = hp;
    emit hpChanged();
}

int Spaceship::getHp() const
{
    return m_hp;
}
