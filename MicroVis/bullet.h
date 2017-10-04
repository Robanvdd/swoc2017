#ifndef BULLET_H
#define BULLET_H

#include <QObject>

class Bullet : public QObject
{
    Q_OBJECT
    Q_PROPERTY(int x MEMBER m_x NOTIFY xChanged)
    Q_PROPERTY(int y MEMBER m_y NOTIFY yChanged)
public:
    explicit Bullet(QObject *parent = nullptr);
    Bullet(int id, int x, int y, QObject *parent = nullptr);

    int x() const;
    void setX(int x);

    int y() const;
    void setY(int y);

    void move(int x, int y);

signals:
    void xChanged();
    void yChanged();

public slots:

private:
    int m_id;
    int m_x;
    int m_y;
};

#endif // BULLET_H
