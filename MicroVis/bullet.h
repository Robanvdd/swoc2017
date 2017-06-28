#ifndef BULLET_H
#define BULLET_H

#include <QObject>

class Bullet : public QObject
{
    Q_OBJECT
public:
    explicit Bullet(QObject *parent = nullptr);
    Bullet(int x, int y, QObject *parent = nullptr);

    int x() const;
    void setX(int x);

    int y() const;
    void setY(int y);

signals:
    void xChanged();
    void yChanged();

public slots:

private:
    int m_x;
    int m_y;
};

#endif // BULLET_H
