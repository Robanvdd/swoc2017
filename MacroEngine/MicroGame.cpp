#include "MicroGame.h"

MicroGame::MicroGame(QString executablePathMicroEngine,
                     QString executablePathMicroBot1,
                     QString executablePathMicroBot2,
                     QObject *parent)
    : QObject(parent)
    , m_executablePathMicroEngine(executablePathMicroEngine)
    , m_executablePathMicroBot1(executablePathMicroBot1)
    , m_executablePathMicroBot2(executablePathMicroBot2)
{
}

void MicroGame::startProcess()
{
    m_process = new QProcess(this);
    m_process->setProgram(m_executablePathMicroEngine);
    QStringList arguments;
    arguments << m_executablePathMicroBot1 << m_executablePathMicroBot2;
    m_process->setArguments(arguments);
    m_process->start();
}

void MicroGame::stopProcess()
{
    m_process->kill();
}

bool MicroGame::running()
{
    return m_process->state() == QProcess::Running;
}
