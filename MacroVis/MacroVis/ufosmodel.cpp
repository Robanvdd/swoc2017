#include "ufosmodel.h"

UfosModel::UfosModel(QObject *parent)
    : QAbstractListModel(parent)
{
}


int UfosModel::rowCount(const QModelIndex& parent) const
{
    Q_UNUSED(parent)
    return m_ufos.count();
}

QVariant UfosModel::data(const QModelIndex& index, int role) const
{
    if (index.row() < 0 || index.row() > m_ufos.count())
        return QVariant();

    switch (role) {
    case TypeRole:
        return m_ufos[index.row()]->getType();
    case InFightRole:
        return m_ufos[index.row()]->getInFight();
    case CoordRole:
        return m_ufos[index.row()]->getCoord();
    case HueRole:
        return m_ufos[index.row()]->getHue();
    case ColorRole:
        return m_ufos[index.row()]->getColor();
    }
    return QVariant();
}

QHash<int, QByteArray> UfosModel::roleNames() const
{
    QHash<int, QByteArray> roles;
    roles[TypeRole] = "type";
    roles[InFightRole] = "inFight";
    roles[CoordRole] = "coord";
    roles[HueRole] = "hue";
    roles[ColorRole] = "color";
    return roles;
}
