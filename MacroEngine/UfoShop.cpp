#include "UfoShop.h"

UfoShop::UfoShop(QObject *parent)
    : QObject(parent)
    , m_ufoPrice(1e5)
{
}

void UfoShop::buyUfo(Player* player, const Planet* planet)
{
    if (planet == nullptr || player == nullptr || player->getCredits() < m_ufoPrice)
        return;
    player->removeCredits(m_ufoPrice);
    auto ufo = new Ufo();
    // TODO planet location
    player->giveUfo(ufo);
}

void UfoShop::buyUfos(Player* player, const Planet* planet, int amount)
{
    for (int i=0; i < amount; i++)
    {
        buyUfo(player, planet);
    }
}
