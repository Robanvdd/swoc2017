#ifndef GAMEOBJECT_H
#define GAMEOBJECT_H

#include <QObject>
#include <mutex>

class GameObject : public QObject
{
    Q_OBJECT
public:
    explicit GameObject(QObject *parent = nullptr);
    int getId() const;

signals:

public slots:
protected:
    int m_id;
private:
    static int nextId;
    std::mutex m_idMutex;
};

#endif // GAMEOBJECT_H
