#include "gameobject.h"

GameObject::GameObject(QObject *parent) : QObject(parent)
  , m_objectId(-1)
{
}

GameObject::GameObject(int objectId, QObject* parent) : QObject(parent)
  , m_objectId(objectId)
{
}

int GameObject::objectId() const
{
    return m_objectId;
}

void GameObject::setObjectId(int objectId)
{
    m_objectId = objectId;
    emit objectIdChanged();
}
