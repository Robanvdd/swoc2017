#ifndef MICROGAMEINPUTPLAYER_H
#define MICROGAMEINPUTPLAYER_H

#include "Player.h"
#include "Ufo.h"

class MicroGameInputPlayer
{
public:
    MicroGameInputPlayer(Player* player, QList<Ufo*> ufos, QString microBot);
    Player* player;
    QList<Ufo*> ufos;
    QString microBot;
};

#endif // MICROGAMEINPUTPLAYER_H
