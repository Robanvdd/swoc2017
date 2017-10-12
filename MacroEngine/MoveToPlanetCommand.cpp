#include "MoveToPlanetCommand.h"

#include <QJsonArray>
#include <QJsonDocument>
#include <QJsonObject>
#include <iostream>

MoveToPlanetCommand::MoveToPlanetCommand(QObject* parent)
    : CommandBase(parent)
    , m_planetId(-1)
{

}

void MoveToPlanetCommand::readCommand(const QJsonObject jsonObject)
{
    m_planetId = jsonObject["PlanetId"].toInt(-1);
    QJsonArray ufoArray = jsonObject["Ufos"].toArray();

    for (int ufoIndex = 0; ufoIndex < ufoArray.size(); ufoIndex++)
    {
        m_ufos << ufoArray[ufoIndex].toInt(-1);
    }
}

void MoveToPlanetCommand::printCommand()
{
    std::cerr << "MoveToPlanet >> Planet: " << m_planetId << "\nUfos: " << std::endl;
    for (int ufo : m_ufos)
    {
        std::cerr << ufo << " ";
    }
    std::cerr << std::endl;

}

QList<int> MoveToPlanetCommand::getUfos() const
{
    return m_ufos;
}

int MoveToPlanetCommand::getPlanetId() const
{
    return m_planetId;
}
