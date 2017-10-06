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
        for (int i = 0; i < playerArray.count(); i++)
        {
            QJsonObject playerJson = playerArray[i].toObject();
            MicroGameOutputPlayer player;
            player.setId(playerJson["id"].toInt());
            player.setName(playerJson["name"].toString());
            QJsonArray casualtiesArray = playerJson["casualties"].toArray();
            for (int j = 0; j < casualtiesArray.size(); j++)
            {
                player.appendCasualty(casualtiesArray[j].toInt());
            }
            m_players.append(player);
        }
    }
}

int MicroGameOutput::getGameId() const
{
    return m_gameId;
}

QList<MicroGameOutputPlayer> MicroGameOutput::getPlayers() const
{
    return m_players;
}

int MicroGameOutput::getWinner() const
{
    return m_winner;
}

MicroGameOutputPlayer::MicroGameOutputPlayer()
    : m_id(-1)
    , m_name("")
    , m_casualties(QList<int>())
{
}

int MicroGameOutputPlayer::getId() const
{
    return m_id;
}

void MicroGameOutputPlayer::setId(int id)
{
    m_id = id;
}

QString MicroGameOutputPlayer::getName() const
{
    return m_name;
}

void MicroGameOutputPlayer::setName(const QString& name)
{
    m_name = name;
}

QList<int> MicroGameOutputPlayer::getCasualties() const
{
    return m_casualties;
}

void MicroGameOutputPlayer::appendCasualty(int id)
{
    m_casualties.append(id);
}

void MicroGameOutputPlayer::setCasualties(const QList<int>& ufos)
{
    m_casualties = ufos;
}
