#ifndef PROCESS_H
#define PROCESS_H

#include <QObject>
#include <QUrl>
#include <QProcess>

class Process : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString commands READ commands WRITE setCommands NOTIFY commandsChanged)
public:
    explicit Process(QObject *parent = nullptr);

    QString commands() const;
    void setCommands(const QString &commands);

    Q_INVOKABLE void start();
    Q_INVOKABLE void write(const QString& data) const;

signals:
    void commandsChanged();

public slots:

private:
    QString m_commands;
    QProcess* m_pProcess;
};

#endif // PROCESS_H
