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
    std::cerr << "Buy >> Amount: " << m_amount << ", PlanetId: " << m_planetId << std::endl;
}

int BuyCommand::getAmount() const
{
    return m_amount;
}

int BuyCommand::getPlanetId() const
{
    return m_planetId;
}
