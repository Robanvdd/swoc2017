#include "MacroBot.h"

#include <QDebug>
#include <iostream>

MacroBot::MacroBot(QString executable, QString arguments, QObject *parent)
    : QObject(parent)
    , m_executable(executable)
    , m_arguments(arguments)
{
}

void MacroBot::startProcess()
{
    m_process = new QProcess(this);
    m_process->start(m_executable);
}

void MacroBot::stopProcess()
{
    m_process->kill();
    m_process->waitForFinished(500);
}

void MacroBot::sendGameState(QString state)
{
    if (!state.contains("\n"))
    {
        state = state + "\n";
    }
    std::cout << "Writing " << state.length() << " characters: " << state.toStdString();
    QByteArray ba = state.toLatin1();
    const char *c_str2 = ba.data();
    if (m_process->state() == QProcess::Running)
    {
        m_process->write(c_str2);
    }
}

QStringList MacroBot::receiveCommands()
{
    //qDebug() << QString::fromLocal8Bit(m_process->readLine();
    QString data(m_process->readAllStandardOutput());
    std::cout << data.toStdString() << std::endl;
    QStringList result;
    //    while (m_process->canReadLine())
    //    {
    //        //result.append(QString::fromLocal8Bit(m_process->readLine()));
    //        //std::cout << "Received " << result.last().length() << " characters" << std::endl;
    //        //std::cout << result.last().toStdString();
    //    }
    return result;
}

bool MacroBot::running()
{
    return m_process->state() == QProcess::Running;
}
