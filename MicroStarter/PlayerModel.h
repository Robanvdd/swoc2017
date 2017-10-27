#ifndef PLAYERMODEL_H
#define PLAYERMODEL_H

#include "Player.h"

#include <QAbstractListModel>
#include <QObject>

class PlayerModel : public QAbstractListModel
{
    Q_OBJECT
public:
    enum PlayerRole {
        NameRole = Qt::UserRole + 1,
        HueRole,
        NrOfUfosRole,
        BotRole
    };

    explicit PlayerModel(QObject *parent = 0);

signals:

public slots:

    // QAbstractItemModel interface
public:
    int rowCount(const QModelIndex& parent) const;
    QVariant data(const QModelIndex& index, int role) const;

private:
    QList<Player*> m_players;

    // QAbstractItemModel interface
public:
    Q_INVOKABLE Player* getPlayer(int row);
    Q_INVOKABLE void addPlayer(const QString& name, const QString& bot, int nrOfUfos, double hue);
    Q_INVOKABLE void removePlayer(int row);
    Q_INVOKABLE void clear();
    Q_INVOKABLE int count();
    bool setData(const QModelIndex& index, const QVariant& value, int role);
    QHash<int, QByteArray> roleNames() const;
};

#endif // PLAYERMODEL_H
