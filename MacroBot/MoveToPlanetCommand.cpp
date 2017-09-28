#include "MoveToPlanetCommand.h"

#include <QJsonArray>
#include <QJsonDocument>
#include <QJsonObject>

MoveToPlanetCommand::MoveToPlanetCommand(QList<int> ufos, int planetId, QObject *parent)
    : QObject(parent)
    , m_command("moveToPlanet")
    , m_ufos(ufos)
    , m_planetId(planetId)
{
}

QString MoveToPlanetCommand::toJson() const
{
    QJsonObject jsonObject;
    jsonObject["Command"] = m_command;
    QJsonArray ufoArray;
    foreach (int ufo, m_ufos)
    {
        ufoArray << ufo;
    }
    jsonObject["Ufos"] = ufoArray;
    jsonObject["PlanetId"] = m_planetId;
    return QJsonDocument(jsonObject).toJson(QJsonDocument::Compact);
}
