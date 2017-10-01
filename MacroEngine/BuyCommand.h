#ifndef BUYCOMMAND_H
#define BUYCOMMAND_H

#include "CommandBase.h"

class BuyCommand : public CommandBase
{
public:
    explicit BuyCommand(QObject* parent = nullptr);
    void readCommand(const QJsonObject jsonObject);
    void printCommand();

    int getAmount() const;

    int getPlanetId() const;

private:
    int m_amount;
    int m_planetId;
};

#endif // BUYCOMMAND_H
