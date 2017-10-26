#include "PlayerModel.h"

PlayerModel::PlayerModel(QObject *parent) : QAbstractListModel(parent)
{

}

int PlayerModel::rowCount(const QModelIndex& parent) const
{
    Q_UNUSED(parent)
    return m_players.count();
}

QVariant PlayerModel::data(const QModelIndex& index, int role) const
{
    if (index.row() < 0 || index.row() >= m_players.count())
        return QVariant();

    switch (role) {
    case NameRole:
        return m_players[index.row()]->getName();
    case NrOfUfosRole:
        return m_players[index.row()]->getNrUfos();
    case HueRole:
        return m_players[index.row()]->getHue();
    case BotRole:
        return m_players[index.row()]->getBot();
    }
    return QVariant();
}

Player* PlayerModel::getPlayer(int row)
{
    if (row < 0 || row >= m_players.count())
        return nullptr;
    return m_players[row];
}

void PlayerModel::addPlayer(const QString& name, const QString& bot, int nrOfUfos, double hue)
{
    beginInsertRows(QModelIndex(), m_players.count(), m_players.count());
    m_players << new Player(name, bot, nrOfUfos, hue);
    endInsertRows();
}

void PlayerModel::removePlayer(int row)
{
    if (row >= 0 && row < m_players.count())
    {
        beginRemoveRows(QModelIndex(), row, row);
        m_players.at(row)->deleteLater();
        m_players.removeAt(row);
        endRemoveRows();
    }
}

void PlayerModel::clear()
{
    beginResetModel();
    qDeleteAll(m_players);
    m_players.clear();
    endResetModel();
}

int PlayerModel::count()
{
    return m_players.count();
}

bool PlayerModel::setData(const QModelIndex& index, const QVariant& value, int role)
{
    if (index.row() < 0 || index.row() >= m_players.count())
        return false;

    switch (role) {
    case NameRole:
        m_players[index.row()]->setName(value.toString());
        emit dataChanged(index, index);
        return true;
    case NrOfUfosRole:
        m_players[index.row()]->setNrUfos(value.toInt());
        emit dataChanged(index, index);
        return true;
    case HueRole:
        m_players[index.row()]->setHue(value.toDouble());
        emit dataChanged(index, index);
        return true;
    case BotRole:
        m_players[index.row()]->setBot(value.toString());
        emit dataChanged(index, index);
        return true;
    }
    return false;
}

QHash<int, QByteArray> PlayerModel::roleNames() const
{
    QHash<int, QByteArray> roles;
    roles[NameRole] = "name";
    roles[NrOfUfosRole] = "nrOfUfos";
    roles[HueRole] = "hue";
    roles[BotRole] = "bot";
    return roles;
}
