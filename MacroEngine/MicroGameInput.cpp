#include "MicroGameInput.h"

#include <QJsonArray>

MicroGameInput::MicroGameInput(Player* playerA, QList<Ufo*> ufosPlayerA, QString botAFolder, Player* playerB, QList<Ufo*> ufosPlayerB, QString botBFolder)
    : m_playerA(playerA)
    , m_playerB(playerB)
    , m_ufosPlayerA(ufosPlayerA)
    , m_ufosPlayerB(ufosPlayerB)
    , m_botAFolder(botAFolder)
    , m_botBFolder(botBFolder)
{
}

void MicroGameInput::writePlayerJson(QJsonObject& jsonObject) const
{
    QJsonArray playersArray;
    QJsonObject playerAObject;
    playerAObject["id"] = m_playerA->getId();
    playerAObject["name"] = m_playerA->getName();
    playerAObject["color"] = m_playerA->getColorName();
    playerAObject["hue"] = m_playerA->getHue();
    QJsonArray playerAUfos;
    foreach (auto ufo, m_ufosPlayerA)
    {
        playerAUfos.append(ufo->getId());
    }
    playerAObject["ufos"] = playerAUfos;
    playerAObject["bot"] = m_botAFolder + "\\";
    playersArray.append(playerAObject);

    QJsonObject playerBObject;
    playerBObject["id"] = m_playerB->getId();
    playerBObject["name"] = m_playerB->getName();
    playerBObject["color"] = m_playerB->getColorName();
    playerBObject["hue"] = m_playerB->getHue();
    QJsonArray playerBUfos;
    foreach (auto ufo, m_ufosPlayerB)
    {
        playerBUfos.append(ufo->getId());
    }
    playerBObject["ufos"] = playerBUfos;
    playerBObject["bot"] = m_botBFolder + "\\";
    playersArray.append(playerBObject);
    jsonObject["players"] = playersArray;
}
