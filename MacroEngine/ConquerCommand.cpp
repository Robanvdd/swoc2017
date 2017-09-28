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
    m_planetId = jsonObject["PlanetId"].toInt(-1);
}

void ConquerCommand::printCommand()
{
    std::cout << "Conquer >> PlanetId: " << m_planetId << std::endl;
}
