#ifndef MOVETOCOORDCOMMAND_H
#define MOVETOCOORDCOMMAND_H

#include "CommandBase.h"

#include <QPoint>

class MoveToCoordCommand : public CommandBase
{
public:
    explicit MoveToCoordCommand(QObject* parent = nullptr);
    void readCommand(const QJsonObject jsonObject);
    void printCommand();

private:
    QList<int> m_ufos;
    QPoint m_coords;
};

#endif // MOVETOCOORDCOMMAND_H
