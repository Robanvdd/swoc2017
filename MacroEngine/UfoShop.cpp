#include "UfoShop.h"

UfoShop::UfoShop(QObject *parent)
    : QObject(parent)
    , m_ufoPrice(1e5)
{
}

void UfoShop::buyUfo(Player* player, Planet planet)
{
    Q_UNUSED(planet)
    if (player->getCredits() > m_ufoPrice)
    {
        player->removeCredits(m_ufoPrice);
        auto ufo = new Ufo();
        // TODO planet location
        player->giveUfo(ufo);
    }
}
