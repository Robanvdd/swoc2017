#include "playersmodel.h"

PlayersModel::PlayersModel(QObject *parent)
    : QAbstractListModel(parent)
{
}

void PlayersModel::CreatePlayer(int playerId)
{
    beginInsertRows(QModelIndex(), rowCount(), rowCount());
    m_players.append(new Player(playerId, this));
    endInsertRows();
}

void PlayersModel::Clear()
{
    beginResetModel();
    qDeleteAll(m_players);
    m_players.clear();
    endResetModel();
}

bool PlayersModel::PlayerExists(int playerId) const
{
    auto player = std::find_if(std::begin(m_players), std::end(m_players), [playerId](QObject* player)
    {
        return dynamic_cast<Player*>(player)->objectId() == playerId;
    });
    return player != std::end(m_players);
}

Player* PlayersModel::GetPlayer(int playerId) const
{
    auto player = std::find_if(std::begin(m_players), std::end(m_players), [playerId](QObject* player)
    {
        return dynamic_cast<Player*>(player)->objectId() == playerId;
    });
    return (Player*) *player;
}

int PlayersModel::rowCount(const QModelIndex& parent) const
{
    Q_UNUSED(parent);
    return m_players.count();
}

QVariant PlayersModel::data(const QModelIndex& index, int role) const
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
        return QVariant::fromValue((void*)m_players[index.row()]->getUfos());
    case HueRole:
        return m_players[index.row()]->getHue();
    case ColorRole:
        return m_players[index.row()]->getColor();
    }
    return QVariant();
}

QHash<int, QByteArray> PlayersModel::roleNames() const
{
    QHash<int, QByteArray> roles;
    roles[NameRole] = "name";
    roles[CreditsRole] = "credits";
    roles[UfosRole] = "ufos";
    roles[HueRole] = "hue";
    roles[ColorRole] = "color";
    return roles;
}
