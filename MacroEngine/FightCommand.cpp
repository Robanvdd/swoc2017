#include "FightCommand.h"
#include <QJsonObject>
#include <iostream>

FightCommand::FightCommand(QObject* parent)
    : CommandBase (parent)
    , m_ufoId(-1)
{
}

void FightCommand::readCommand(const QJsonObject jsonObject)
{
    std::cerr << "Reading fight command" << std::endl;
    m_ufoId = jsonObject["ufoId"].toInt(-1);
}

void FightCommand::printCommand()
{
    std::cerr << "Fight >> UfoId: " << m_ufoId << std::endl;
}

int FightCommand::getUfoId() const
{
    return m_ufoId;
}
