#ifndef MICROGAME_H
#define MICROGAME_H

#include <QObject>
#include <QProcess>

class MicroGame : public QObject
{
    Q_OBJECT
public:
    MicroGame(QString executablePathMicroEngine,
              QString executablePathMicroBot1,
              QString executablePathMicroBot2,
              QObject *parent = nullptr);
    void startProcess();
    void stopProcess();
    bool running();

signals:

public slots:

private:
    QString m_executablePathMicroEngine;
    QString m_executablePathMicroBot1;
    QString m_executablePathMicroBot2;
    QProcess* m_process;
};

#endif // MICROGAME_H
