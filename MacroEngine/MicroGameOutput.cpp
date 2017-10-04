#include "MicroGameOutput.h"

#include <QJsonArray>
#include <QJsonDocument>
#include <QJsonObject>

MicroGameOutput::MicroGameOutput()
    : m_gameId(-1)
    , m_players(QList<MicroGameOutputPlayer>())
    , m_winner(-1)
{
}

void MicroGameOutput::readOutput(const QString& jsonString)
{
    QJsonParseError error;
    auto doc = QJsonDocument::fromJson(jsonString.toUtf8(), &error);
    if (error.error == QJsonParseError::NoError)
    {
        auto object = doc.object();
        m_winner = object["winner"].toInt(-1);
        m_gameId = object["gameId"].toInt(-1);
        QJsonArray playerArray = object["players"].toArray();
//        for
//        foreach (QJsonObject player, playerArray)
//        {

//        }
    }
}

MicroGameOutputPlayer::MicroGameOutputPlayer()
    : m_id(-1)
    , m_name("")
    , m_ufos(QList<int>())
{
}
