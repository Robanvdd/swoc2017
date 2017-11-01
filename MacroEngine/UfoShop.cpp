#include "Ufo.h"
#include "UfoShop.h"

#ifndef M_PI
    #define M_PI 3.14159265358979323846
#endif

UfoShop::UfoShop(QObject *parent)
    : QObject(parent)
    , m_ufoPrice(2e4)
{
}

void UfoShop::buyUfo(Player* player, Planet* planet, Universe* universe)
{
    if (player == nullptr)
        return;

    int nUfos = player->getUfos().count();

    int nOwnedPlanets = 0;
    foreach (SolarSystem* solarSystem, universe->getSolarSystems()) {
        foreach (Planet* planet, solarSystem->getPlanets()) {
            if (planet->getOwnedBy() == player->getId())
                nOwnedPlanets++;
        }
    }

    if ( player->getCredits() < m_ufoPrice && (nUfos > 0 || nOwnedPlanets > 0))
        return;

    if (planet == nullptr)
    {
        player->removeCredits(m_ufoPrice);
        auto ufo = new Ufo();
        ufo->setCoord(QPointF(rand() % 15000, rand() % 15000));
        ufo->setUniverse(universe);
        player->giveUfo(ufo);
        return;
    }

    if (planet->getOwnedBy() != player->getId())
        return;
    player->removeCredits(m_ufoPrice);
    auto ufo = new Ufo();
    ufo->setCoord(universe->getCorrespondingSolarSystem(planet)->getPlanetLocation(*planet));
    ufo->setUniverse(universe);
    player->giveUfo(ufo);
}

void UfoShop::giveUfo(Player* player, Universe* universe, double ufoPlacement)
{
    if (player == nullptr || universe == nullptr)
        return;

    auto startingSolarSystem = universe->getSolarSystems().first();
    auto outerPlanet = startingSolarSystem->getPlanets().first();
    foreach (auto planet, startingSolarSystem->getPlanets())
    {
        if (planet->getOrbitDistance() > outerPlanet->getOrbitDistance())
            outerPlanet = planet;
    }

    double distance = outerPlanet->getOrbitDistance() + 100;

    auto ufo = new Ufo();
    ufo->setCoord(QPointF(startingSolarSystem->getCoord().x() + distance * cos(ufoPlacement * 2 * M_PI), startingSolarSystem->getCoord().y() + distance * sin(ufoPlacement * 2 * M_PI)));
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

int UfoShop::getUfoPrice() const
{
    return m_ufoPrice;
}


