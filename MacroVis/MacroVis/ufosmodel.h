#ifndef UFOMODEL_H
#define UFOMODEL_H

#include <QAbstractListModel>
#include "ufo.h"

class UfosModel : public QAbstractListModel
{
    Q_OBJECT
public:
    enum UfoRoles {
        TypeRole = Qt::UserRole + 1,
        InFightRole,
        CoordRole,
        HueRole,
        ColorRole
    };

    explicit UfosModel(QObject *parent = nullptr);
    ~UfosModel();

    bool ufoExists(int ufoId) const;
    void createUfo(int ufoId);
    void destroyUfo(int ufoId);
    Ufo* getUfo(int ufoId) const;
    void onlyKeepUfos(const QList<int> ufosToKeep);

    int rowCount(const QModelIndex& parent = QModelIndex()) const;
    QVariant data(const QModelIndex& index, int role) const;
    QHash<int, QByteArray> roleNames() const;

private:
    QList<Ufo*> m_ufos;
};

#endif // UFOMODEL_H
