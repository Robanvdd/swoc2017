#include <random>

#include "planetimageprovider.h"

PlanetImageProvider::PlanetImageProvider(QObject *parent) : QObject(parent)
{
    m_planetUrls << QUrl("qrc:/images/Ceres.ico");
    m_planetUrls << QUrl("qrc:/images/Earth.ico");
    m_planetUrls << QUrl("qrc:/images/Eris.ico");
    m_planetUrls << QUrl("qrc:/images/Jupiter.ico");
    m_planetUrls << QUrl("qrc:/images/Mars.ico");
    m_planetUrls << QUrl("qrc:/images/Mercury.ico");
    m_planetUrls << QUrl("qrc:/images/Moon.ico");
    m_planetUrls << QUrl("qrc:/images/Neptune.ico");
    m_planetUrls << QUrl("qrc:/images/Pluto.ico");
    m_planetUrls << QUrl("qrc:/images/Saturn.ico");
    m_planetUrls << QUrl("qrc:/images/Uranus.ico");
    m_planetUrls << QUrl("qrc:/images/Venus.ico");
}

QUrl PlanetImageProvider::getRandomPlanet() const
{
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(0, m_planetUrls.length() - 1);

    int index = dis(gen);
    return m_planetUrls[index];
}
