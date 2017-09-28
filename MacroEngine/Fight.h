#ifndef FIGHT_H
#define FIGHT_H

#include <QObject>

class Fight : public QObject
{
    Q_OBJECT
public:
    explicit Fight(QObject *parent = nullptr);

signals:

public slots:
};

#endif // FIGHT_H