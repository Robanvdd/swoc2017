#ifndef CONQUERCOMMAND_H
#define CONQUERCOMMAND_H

#include "CommandBase.h"

class ConquerCommand : public CommandBase
{
public:
    explicit ConquerCommand(QObject* parent = nullptr);
    void readCommand(const QJsonObject jsonObject);
    void printCommand();
private:
    int m_planetId;
};

#endif // CONQUERCOMMAND_H
