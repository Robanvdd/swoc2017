#ifndef SPACESHIP_H
#define SPACESHIP_H

#include <QObject>

class Spaceship : public QObject
{
    Q_OBJECT
    Q_PROPERTY(int x MEMBER m_x NOTIFY xChanged)
    Q_PROPERTY(int y MEMBER m_y NOTIFY yChanged)
    Q_PROPERTY(int hp MEMBER m_hp NOTIFY hpChanged)
    Q_PROPERTY(int id MEMBER m_id NOTIFY idChanged)
    Q_PROPERTY(QString name MEMBER m_name NOTIFY nameChanged)
public:
    explicit Spaceship(QObject *parent = nullptr);
    Spaceship(int id, QString name, int x, int y, QObject *parent = nullptr);

    int getId() const;
    void setId(int id);

    QString getName() const;
    void setName(QString name);

    void move(int x, int y);

    void setHp(int hp);
    int getHp() const;

signals:
    void xChanged();
    void yChanged();
    void hpChanged();
    void idChanged();
    void nameChanged();

public slots:

private:
    QString m_name;
    int m_x;
    int m_y;
    int m_hp;
    int m_id;
};

#endif // SPACESHIP_H
