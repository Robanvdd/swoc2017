#ifndef MICROGAMEINPUT_H
#define MICROGAMEINPUT_H

#include "Player.h"
#include "Ufo.h"

#include <QJsonObject>

class MicroGameInput
{
public:
    MicroGameInput(Player* playerA, QList<Ufo*> ufosPlayerA, QString botAFolder, Player* playerB, QList<Ufo*> ufosPlayerB, QString botBFolder);
    void writePlayerJson(QJsonObject& jsonObject) const;

private:
    Player* m_playerA;
    Player* m_playerB;
    QList<Ufo*> m_ufosPlayerA;
    QList<Ufo*> m_ufosPlayerB;
    QString m_botAFolder;
    QString m_botBFolder;
};

#endif // MICROGAMEINPUT_H
