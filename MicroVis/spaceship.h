#ifndef SPACESHIP_H
#define SPACESHIP_H

#include <QObject>

class Spaceship : public QObject
{
    Q_OBJECT
    Q_PROPERTY(int x MEMBER m_x NOTIFY xChanged)
    Q_PROPERTY(int y MEMBER m_y NOTIFY yChanged)
public:
    explicit Spaceship(QObject *parent = nullptr);
    Spaceship(int x, int y, QObject *parent = nullptr);

    void move(int x, int y);

signals:
    void xChanged();
    void yChanged();

public slots:

private:
    int m_x;
    int m_y;
};

#endif // SPACESHIP_H
