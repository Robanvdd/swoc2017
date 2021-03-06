#include "ConquerCommand.h"

#include <QJsonObject>
#include <iostream>

ConquerCommand::ConquerCommand(QObject* parent)
    : CommandBase(parent)
    , m_planetId(-1)
{
}

void ConquerCommand::readCommand(const QJsonObject jsonObject)
{
    m_planetId = jsonObject["planetId"].toInt(-1);
}

void ConquerCommand::printCommand()
{
    std::cerr << "Conquer >> PlanetId: " << m_planetId << std::endl;
}

int ConquerCommand::getPlanetId() const
{
    return m_planetId;
}
