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
    m_process->setProgram(m_executable);
    QStringList arguments;
    arguments.append(m_arguments);
    //m_process->setArguments(arguments);
    //m_process->start("cmd");
    //m_process->setReadChannel(QProcess::StandardOutput);
    //m_process->start("cmd.exe /c Z:\\Release\\MacroBot.exe");
    //m_process->start("python D:\\helloWorld.py");
    m_process->start("D:\\Debug\\ConsoleApplication1.exe");
    //m_process->start(QProcess::Unbuffered | QProcess::ReadWrite);
    //m_process->waitForStarted();
    //m_process->write("Z:\\Release\\MacroBot.exe");
    //    QObject::connect(m_process, &QProcess::readyReadStandardError, [this]() {
    //        qDebug()<<"Error: "<< m_process->readAllStandardError();
    //    });
    //    QObject::connect(m_process, &QProcess::readyReadStandardOutput, [this]() {
    //        qDebug()<<"Output: "<< m_process->readAllStandardOutput();
    //    });
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
    std::cout << "Writing " << state.length() << " characters: " << state.toStdString() << std::endl;
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
