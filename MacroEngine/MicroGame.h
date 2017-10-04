#ifndef MICROGAME_H
#define MICROGAME_H

#include "GameObject.h"
#include "MicroGameInput.h"

#include <QObject>
#include <QProcess>

class MicroGame : public GameObject
{
    Q_OBJECT
public:
    MicroGame(QString executablePathMicroEngine,
              MicroGameInput input,
              QObject *parent = nullptr);
    void startProcess();
    void stopProcess();
    bool running();
    bool getFinished() const;
    bool canReadLine() const;
    QString readLine() const;

signals:
    void dataAvailable();

public slots:

private:
    QString m_executablePathMicroEngine;
    MicroGameInput m_input;
    QProcess* m_process;
    bool m_dataAvailable;
};

#endif // MICROGAME_H
