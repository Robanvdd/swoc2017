#ifndef PLAYERMODEL_H
#define PLAYERMODEL_H

#include <QAbstractListModel>
#include "player.h"

class PlayersModel : public QAbstractListModel
{
    Q_OBJECT
public:
    enum PlayerRoles {
            NameRole = Qt::UserRole + 1,
            CreditsRole,
            UfosRole,
            HueRole,
            ColorRole,
        };
    explicit PlayersModel(QObject *parent = nullptr);

    int rowCount(const QModelIndex& parent) const;
    QVariant data(const QModelIndex& index, int role) const;
    QHash<int, QByteArray> roleNames() const;

private:
    QList<Player*> m_players;
};

#endif // PLAYERMODEL_H
