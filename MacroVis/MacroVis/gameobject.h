#ifndef GAMEOBJECT_H
#define GAMEOBJECT_H

#include <QObject>

class GameObject : public QObject
{
    Q_OBJECT
    Q_PROPERTY(int objectId READ objectId WRITE setObjectId NOTIFY objectIdChanged)
public:
    explicit GameObject(QObject* parent = 0);
    explicit GameObject(int objectId, QObject* parent = 0);

    int objectId() const;
    void setObjectId(int objectId);

signals:
    void objectIdChanged();

public slots:

private:
    int m_objectId;
};

#endif // GAMEOBJECT_H
