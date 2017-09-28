#ifndef BOT_H
#define BOT_H

#include <QObject>
#include <QTextStream>

class Bot : public QObject
{
    Q_OBJECT
public:
    explicit Bot(QObject *parent = 0);

signals:
    void finished();
    void errorOccured();

public slots:
    void run();

private:
    QTextStream in;
};

#endif // BOT_H
