#ifndef MICROGAMEINPUT_H
#define MICROGAMEINPUT_H

#include "MicroGameInputPlayer.h"
#include "Player.h"
#include "Ufo.h"

#include <QJsonObject>

class MicroGameInput
{
public:
    MicroGameInput(QList<MicroGameInputPlayer> microGameInputPlayers);
    void writePlayerJson(QJsonObject& jsonObject) const;

    QList<MicroGameInputPlayer> m_microGameInputPlayers;
};

#endif // MICROGAMEINPUT_H
