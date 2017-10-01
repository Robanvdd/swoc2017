#ifndef UFOSHOP_H
#define UFOSHOP_H

#include "Player.h"
#include "Planet.h"

#include <QObject>

class UfoShop : public QObject
{
    Q_OBJECT
public:
    explicit UfoShop(QObject *parent = nullptr);
    void buyUfo(Player* player, const Planet *planet);

    void buyUfos(Player *player, const Planet *planet, int amount=1);
signals:

public slots:

private:
    int m_ufoPrice;
};

#endif // UFOSHOP_H
