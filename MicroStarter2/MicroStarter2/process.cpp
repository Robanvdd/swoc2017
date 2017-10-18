#include "process.h"
#include <QDebug>
#include <QThread>

Process::Process(QObject *parent) :
   QObject(parent), m_program(""), m_arguments(""), m_pProcess(new QProcess(this)) {}

QString Process::program() const {
    return m_program;
}

void Process::setProgram(const QString &program) {
    m_program = program;
}

QString Process::arguments() const
{
    return m_arguments;
}

void Process::setArguments(const QString &arguments)
{
    m_arguments = arguments;
}

void Process::start() {
    m_pProcess->setReadChannel(QProcess::StandardOutput);

    QObject::connect(m_pProcess, &QProcess::readyReadStandardOutput, this, [this]()
    {
        qDebug() << m_pProcess->readAllStandardOutput();
    });

    QObject::connect(m_pProcess, &QProcess::readyReadStandardError, this, [this]()
    {
        qDebug() << m_pProcess->readAllStandardError();
    });

    QObject::connect(m_pProcess, &QProcess::errorOccurred, this, [this]()
    {
        QString errorString = m_pProcess->errorString();
        qDebug() << "Error occurred " << errorString;
    });

    m_pProcess->start(m_program, m_arguments.split(' '));
    m_pProcess->waitForStarted(1000);

    if (m_pProcess->state() != QProcess::Running)
    {
        QString errorString = m_pProcess->errorString();
        qDebug() << "Error occurred " << errorString;
        throw std::runtime_error("Could not start micro.jar");
    }
}

void Process::write(const QString& data) const {
    m_pProcess->write(data.toLocal8Bit() + '\n');
}
