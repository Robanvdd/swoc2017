#include "MicroGame.h"

#include <QJsonDocument>

MicroGame::MicroGame(QString executablePathMicroEngine,
                     MicroGameInput input,
                     QObject *parent)
    : GameObject(parent)
    , m_executablePathMicroEngine(executablePathMicroEngine)
    , m_input(input)
{
}

void MicroGame::startProcess()
{
    m_process = new QProcess(this);
    m_process->setProgram(m_executablePathMicroEngine);
//    QStringList arguments;
//    arguments << m_executablePathMicroBot1 << m_executablePathMicroBot2;
//    m_process->setArguments(arguments);
    m_process->start();
    auto started = m_process->waitForStarted(500);
    if (!started)
        throw std::runtime_error("Could not start Microgame");

    QJsonObject jsonInput;
    jsonInput["gameId"] = m_id;
    m_input.writePlayerJson(jsonInput);
    QJsonDocument doc(jsonInput);
    m_process->write(doc.toJson(QJsonDocument::Compact) + "\n");
}

void MicroGame::stopProcess()
{
    m_process->kill();
}

bool MicroGame::running()
{
    return m_process->state() == QProcess::Running;
}
