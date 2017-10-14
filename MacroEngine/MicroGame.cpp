#include "MicroGame.h"

#include <QJsonDocument>
#include <QDebug>
#include <QDir>

MicroGame::MicroGame(QString executablePathMicroEngine,
                     MicroGameInput input,
                     QObject *parent)
    : GameObject(parent)
    , m_executablePathMicroEngine(executablePathMicroEngine)
    , m_input(input)
    , m_process(new QProcess(this))
    , m_dataAvailable(false)
{
}

void MicroGame::startProcess()
{
    m_process->setReadChannel(QProcess::StandardOutput);

    QObject::connect(m_process, &QProcess::readyReadStandardOutput, this, [this]()
    {
        m_dataAvailable = true;
        emit dataAvailable();
    });

    QObject::connect(m_process, &QProcess::readyReadStandardError, this, [this]()
    {
        qDebug() << m_process->readAllStandardError();
    });

    QObject::connect(m_process, &QProcess::stateChanged, this, [this]()
    {
        if (m_process->state() == QProcess::Running)
        {
            QJsonObject jsonInput;
            jsonInput["gameId"] = m_id;
            jsonInput["ticks"] = m_process->workingDirectory() + "/";
            m_input.writePlayerJson(jsonInput);
            QJsonDocument doc(jsonInput);
            m_process->write(doc.toJson(QJsonDocument::Compact) + "\n");
        }
    });

    QObject::connect(m_process, &QProcess::errorOccurred, this, [this]()
    {
        qDebug() << "Error occured " << m_process->errorString();
    });

    m_process->start("java -jar D:\\micro.jar");

    m_process->waitForStarted(1000);

    if (m_process->state() != QProcess::Running)
    {
        qDebug() << m_process->errorString();
        throw std::runtime_error("Could not start micro.jar");
    }
}

void MicroGame::setWorkingDir(const QString& workingDir)
{
    m_process->setWorkingDirectory(workingDir);
}

void MicroGame::stopProcess()
{
    m_process->disconnect();
    m_process->kill();
    m_process->waitForFinished(500);
}

bool MicroGame::running()
{
    return m_process->state() == QProcess::Running;
}

bool MicroGame::getFinished() const
{
    return m_dataAvailable;
}

bool MicroGame::canReadLine() const
{
    return m_process->canReadLine();
}

QString MicroGame::readLine() const
{
    if (m_process->canReadLine())
    {
        return QString::fromLocal8Bit(m_process->readLine());
    }
    return QString();
}
