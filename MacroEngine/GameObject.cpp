#include "GameObject.h"

int GameObject::nextId = 0;

GameObject::GameObject(QObject *parent)
    : QObject(parent)
{
    std::lock_guard<std::mutex> lock(m_idMutex);
    m_id = ++nextId;
}

int GameObject::getId() const
{
    return m_id;
}
