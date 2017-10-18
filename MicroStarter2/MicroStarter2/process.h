#ifndef PROCESS_H
#define PROCESS_H

#include <QObject>
#include <QUrl>
#include <QProcess>

class Process : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString program READ program WRITE setProgram NOTIFY programChanged)
    Q_PROPERTY(QString arguments READ arguments WRITE setArguments NOTIFY argumentsChanged)
public:
    explicit Process(QObject *parent = nullptr);

    QString program() const;
    void setProgram(const QString &program);

    QString arguments() const;
    void setArguments(const QString &arguments);

    Q_INVOKABLE void start();
    Q_INVOKABLE void write(const QString &data) const;

signals:
    void programChanged();
    void argumentsChanged();

public slots:

private:
    QString m_program;
    QString m_arguments;
    QProcess* m_pProcess;
};

#endif // PROCESS_H
