#include "Ufo.h"
#include "UfoShop.h"

UfoShop::UfoShop(QObject *parent)
    : QObject(parent)
    , m_ufoPrice(1e5)
{
}

void UfoShop::buyUfo(Player* player, Planet* planet, Universe* universe)
{
    if (planet == nullptr || player == nullptr || player->getCredits() < m_ufoPrice || planet->getOwnedBy() != player->getId())
        return;
    player->removeCredits(m_ufoPrice);
    auto ufo = new Ufo();
    ufo->setCoord(universe->getCorrespondingSolarSystem(planet)->getPlanetLocation(*planet));
    ufo->setUniverse(universe);
    player->giveUfo(ufo);
}

void UfoShop::giveUfo(Player* player, Universe* universe)
{
    if (player == nullptr || universe == nullptr)
        return;
    auto ufo = new Ufo();
    ufo->setCoord(QPointF(6000, 6000));
    ufo->setUniverse(universe);
    player->giveUfo(ufo);
}

void UfoShop::buyUfos(Player* player, Planet* planet, Universe* universe, int amount)
{
    for (int i=0; i < amount; i++)
    {
        buyUfo(player, planet, universe);
    }
}


