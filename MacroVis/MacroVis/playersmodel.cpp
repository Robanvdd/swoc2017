#include "playermodel.h"

PlayerModel::PlayerModel(QObject *parent)
    : QAbstractListModel(parent)
{
}

int PlayerModel::rowCount(const QModelIndex& parent) const
{
    Q_UNUSED(parent)
    return m_players.count();
}

QVariant PlayerModel::data(const QModelIndex& index, int role) const
{
    if (index.row() < 0 || index.row() < m_players.count())
        return QVariant();

    switch (role)
    {
    case NameRole:
        return m_players[index.row()]->getName();
    case CreditsRole:
        return m_players[index.row()]->getCredits();
    case UfosRole:
        return m_players[index.row()]->getUfos();
    case HueRole:
        return m_players[index.row()]->getHue();
    case ColorRole:
        return m_players[index.row()]->getColor();
    }
    return QVariant();
}

QHash<int, QByteArray> PlayerModel::roleNames() const
{
    QHash<int, QByteArray> roles;
    roles[NameRole] = "name";
    roles[CreditsRole] = "credits";
    roles[UfosRole] = "ufos";
    roles[HueRole] = "hue";
    roles[ColorRole] = "color";
    return roles;
}
