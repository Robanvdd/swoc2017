#include "process.h"

Process::Process(QObject *parent) :
   QObject(parent), m_commands(""), m_pProcess(new QProcess(this)) {}

QString Process::commands() const {
    return m_commands;
}

void Process::setCommands(const QString &path) {
    m_commands = path;
}

void Process::start() {
    m_pProcess->start(m_commands);
    m_pProcess->waitForStarted(1000);
}

void Process::write(const QString& data) const {
    m_pProcess->write(data.toLocal8Bit());
}
