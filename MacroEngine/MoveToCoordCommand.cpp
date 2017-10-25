#include "MoveToCoordCommand.h"

#include <QJsonArray>
#include <QJsonObject>
#include <iostream>

MoveToCoordCommand::MoveToCoordCommand(QObject* parent)
    : CommandBase(parent)
    , m_coords(-1, -1)
{

}

void MoveToCoordCommand::readCommand(const QJsonObject jsonObject)
{
    QJsonArray ufoArray = jsonObject["ufos"].toArray();
    for (int ufoIndex = 0; ufoIndex < ufoArray.size(); ufoIndex++)
    {
        m_ufos << ufoArray[ufoIndex].toInt(-1);
    }

    QJsonObject coords = jsonObject["coord"].toObject();
    m_coords.setX(coords["x"].toInt(-1));
    m_coords.setY(coords["y"].toInt(-1));
}

void MoveToCoordCommand::printCommand()
{
    std::cerr << "MoveToCoord >>  Coord: ("  << m_coords.x() << "," << m_coords.y()  << ")\nUfos: " << std::endl;
    for (int ufo : m_ufos)
    {
        std::cerr << ufo << " ";
    }
    std::cerr << std::endl;
}

QList<int> MoveToCoordCommand::getUfos() const
{
    return m_ufos;
}

QPoint MoveToCoordCommand::getCoords() const
{
    return m_coords;
}
