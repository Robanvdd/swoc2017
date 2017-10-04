#ifndef UFOSHOP_H
#define UFOSHOP_H

#include "Player.h"
#include "Planet.h"
#include "Universe.h"

#include <QObject>

class UfoShop : public QObject
{
    Q_OBJECT
public:
    explicit UfoShop(QObject *parent = nullptr);
    void buyUfo(Player* player, Planet* planet, Universe* universe);
    void giveUfo(Player* player, Universe* universe);

    void buyUfos(Player *player, Planet* planet, Universe* universe, int amount=1);
signals:

public slots:

private:
    int m_ufoPrice;
};

#endif // UFOSHOP_H
