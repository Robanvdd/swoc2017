#include "playersmodel.h"

PlayersModel::PlayersModel(QObject *parent)
    : QAbstractListModel(parent)
{
}

void PlayersModel::CreatePlayer(int playerId)
{
    auto player = new Player(playerId, this);

    QObject::connect(player, &Player::nameChanged, this, [this, player]()
    {
        if (m_players.contains(player))
        {
            int rowId = m_players.indexOf(player);
            QVector<int> roles;
            roles << NameRole;
            emit dataChanged(index(rowId), index(rowId), roles);
        }
    });
    QObject::connect(player, &Player::creditsChanged, this, [this, player]()
    {
        if (m_players.contains(player))
        {
            int rowId = m_players.indexOf(player);
            QVector<int> roles;
            roles << CreditsRole;
            emit dataChanged(index(rowId), index(rowId), roles);
        }
    });
    QObject::connect(player, &Player::ufosChanged, this, [this, player]()
    {
        if (m_players.contains(player))
        {
            int rowId = m_players.indexOf(player);
            QVector<int> roles;
            roles << UfosRole;
            emit dataChanged(index(rowId), index(rowId), roles);
        }
    });
    QObject::connect(player, &Player::hueChanged, this, [this, player]()
    {
        if (m_players.contains(player))
        {
            int rowId = m_players.indexOf(player);
            QVector<int> roles;
            roles << HueRole;
            emit dataChanged(index(rowId), index(rowId), roles);
        }
    });
    QObject::connect(player, &Player::colorChanged, this, [this, player]()
    {
        if (m_players.contains(player))
        {
            int rowId = m_players.indexOf(player);
            QVector<int> roles;
            roles << ColorRole;
            emit dataChanged(index(rowId), index(rowId), roles);
        }
    });

    beginInsertRows(QModelIndex(), rowCount(), rowCount());
    m_players.append(player);
    endInsertRows();
    emit lengthChanged();
}

void PlayersModel::Clear()
{
    beginResetModel();
    qDeleteAll(m_players);
    m_players.clear();
    endResetModel();
    emit lengthChanged();
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
    if (index.row() < 0 || index.row() >= m_players.count())
        return QVariant();

    switch (role)
    {
    case NameRole:
        return m_players[index.row()]->getName();
    case CreditsRole:
        return m_players[index.row()]->getCredits();
    case UfosRole:
        // m_players[index.row()]->getUfos()
        return QVariant::fromValue(m_players[index.row()]->getUfos());
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
