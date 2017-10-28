#ifndef FIGHTCOMMAND_H
#define FIGHTCOMMAND_H

#include "CommandBase.h"

class FightCommand : public CommandBase
{
public:
    explicit FightCommand(QObject* parent = nullptr);

    void readCommand(const QJsonObject jsonObject);
    void printCommand();

    int getUfoId() const;

private:
    int m_ufoId;
};

#endif // FIGHTCOMMAND_H
