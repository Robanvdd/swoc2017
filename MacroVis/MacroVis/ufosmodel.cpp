#include "ufosmodel.h"

UfosModel::UfosModel(QObject *parent)
    : QAbstractListModel(parent)
{
}

UfosModel::~UfosModel()
{
    qDeleteAll(m_ufos);
}

bool UfosModel::ufoExists(int ufoId) const
{
    auto ufo = std::find_if(std::begin(m_ufos), std::end(m_ufos), [ufoId](QObject* ufo)
    {
        return dynamic_cast<Ufo*>(ufo)->objectId() == ufoId;
    });
    return ufo != std::end(m_ufos);
}

void UfosModel::createUfo(int ufoId)
{
    beginInsertRows(QModelIndex(), rowCount(), rowCount());
    m_ufos.append(new Ufo(ufoId, this));
    endInsertRows();
}

void UfosModel::destroyUfo(int ufoId)
{

    if (ufoExists(ufoId))
    {
        auto ufo = getUfo(ufoId);
        auto row = m_ufos.indexOf(ufo);
        beginRemoveRows(QModelIndex(), row, row);
        m_ufos.removeOne(ufo);
        endRemoveRows();
    }
}

Ufo* UfosModel::getUfo(int ufoId) const
{
    auto ufo = std::find_if(std::begin(m_ufos), std::end(m_ufos), [ufoId](QObject* ufo)
    {
        return dynamic_cast<Ufo*>(ufo)->objectId() == ufoId;
    });
    return (Ufo*) *ufo;
}

void UfosModel::onlyKeepUfos(const QList<int> ufoIdsToKeep)
{
    std::vector<int> ufosToRemove;
    for (auto ufo : m_ufos)
    {
        if (!ufoIdsToKeep.contains(ufo->objectId()))
                ufosToRemove.push_back(ufo->objectId());
    }

    for (auto ufoId : ufosToRemove)
        destroyUfo(ufoId);
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
