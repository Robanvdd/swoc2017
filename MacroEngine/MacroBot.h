#ifndef MACROBOT_H
#define MACROBOT_H

#include <QObject>
#include <QProcess>

class MacroBot : public QObject
{
    Q_OBJECT
public:
    explicit MacroBot(QString executable, QString arguments, QObject *parent = nullptr);
    void startProcess();
    void stopProcess();
    void sendGameState(QString state);
    QStringList receiveCommands();
    bool running();

signals:

public slots:

private:
    QString m_executable;
    QString m_arguments;

    QProcess* m_process;
};

#endif // MACROBOT_H
