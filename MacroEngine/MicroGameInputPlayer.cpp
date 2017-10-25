#include "MicroGameInputPlayer.h"

MicroGameInputPlayer::MicroGameInputPlayer(Player* player, QList<Ufo*> ufos, QString microBot)
    : player(player)
    , ufos(ufos)
    , microBot(microBot)
{
}
