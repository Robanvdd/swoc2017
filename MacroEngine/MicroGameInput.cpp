#include "MicroGameInput.h"

#include <QJsonArray>

MicroGameInput::MicroGameInput(QList<MicroGameInputPlayer> microGameInputPlayers)
    : m_microGameInputPlayers(microGameInputPlayers)
{
}

void MicroGameInput::writePlayerJson(QJsonObject& jsonObject) const
{
    QJsonArray playersArray;
    foreach (auto microGameInputPlayer, m_microGameInputPlayers)
    {
        QJsonObject playerObject;
        playerObject["id"] = microGameInputPlayer.player->getId();
        playerObject["name"] = microGameInputPlayer.player->getName();
        playerObject["color"] = microGameInputPlayer.player->getColorName();
        playerObject["hue"] = microGameInputPlayer.player->getHue();
        QJsonArray playerAUfos;
        foreach (auto ufo, microGameInputPlayer.ufos)
        {
            playerAUfos.append(ufo->getId());
        }
        playerObject["ufos"] = playerAUfos;
        playerObject["bot"] = microGameInputPlayer.microBot + "\\";
        playersArray.append(playerObject);
    }
    jsonObject["players"] = playersArray;
}
