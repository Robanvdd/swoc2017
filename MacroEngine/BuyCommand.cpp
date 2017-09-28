#include "BuyCommand.h"

#include <QJsonObject>
#include <iostream>

BuyCommand::BuyCommand(QObject* parent)
    : CommandBase(parent)
    , m_amount(0)
    , m_planetId(-1)
{
}

void BuyCommand::readCommand(const QJsonObject jsonObject)
{
    m_amount = jsonObject["Amount"].toInt(0);
    m_planetId = jsonObject["PlanetId"].toInt(-1);
}

void BuyCommand::printCommand()
{
    std::cout << "Buy >> Amount: " << m_amount << ", PlanetId: " << m_planetId << std::endl;
}
