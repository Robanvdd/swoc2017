#ifndef MOVETOPLANETCOMMAND_H
#define MOVETOPLANETCOMMAND_H

#include "CommandBase.h"

class MoveToPlanetCommand : public CommandBase
{
public:
    explicit MoveToPlanetCommand(QObject* parent = nullptr);

    void readCommand(const QJsonObject jsonObject) override;
    void printCommand() override;

    QList<int> getUfos() const;

    int getPlanetId() const;

private:
    QList<int> m_ufos;
    int m_planetId;
};

#endif // MOVETOPLANETCOMMAND_H
