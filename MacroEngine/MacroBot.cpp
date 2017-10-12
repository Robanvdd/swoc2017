#include "MacroBot.h"

#include <QDebug>
#include <iostream>

MacroBot::MacroBot(QString executable, QString arguments, QObject *parent)
    : QObject(parent)
    , m_executable(executable)
    , m_arguments(arguments)
{
}

MacroBot::~MacroBot()
{
    m_process->waitForFinished(500);
}

void MacroBot::startProcess()
{
    m_process = new QProcess(this);
    QObject::connect(m_process, &QProcess::errorOccurred, this, [this]()
    {
        std::cerr << "Error occured " << m_process->errorString().toStdString() << std::endl;
        std::cerr << m_executable.toStdString() << std::endl;
    });
    m_process->start(m_executable);
}

void MacroBot::stopProcess()
{
    m_process->kill();
    m_process->waitForFinished(5000);
}

void MacroBot::sendGameState(QString state)
{
    if (!state.contains("\n"))
    {
        state = state + "\n";
    }
    QByteArray ba = state.toLatin1();
    const char *c_str2 = ba.data();
    if (m_process->state() == QProcess::Running)
    {
        m_process->write(c_str2);
    }
}

QStringList MacroBot::receiveCommands()
{
    QStringList result;
    while (m_process->canReadLine())
    {
        result.append(QString::fromLocal8Bit(m_process->readLine()));
    }
    return result;
}

bool MacroBot::running()
{
    return m_process->state() == QProcess::Running;
}
