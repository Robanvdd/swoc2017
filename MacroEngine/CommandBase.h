#ifndef COMMANDBASE_H
#define COMMANDBASE_H

#include <QObject>

class CommandBase : public QObject
{
    Q_OBJECT
public:
    explicit CommandBase(QObject *parent = nullptr);
    virtual ~CommandBase() = default;
    virtual void readCommand(const QJsonObject jsonObject) = 0;
    virtual void printCommand() = 0;

signals:

public slots:
};

#endif // COMMANDBASE_H
