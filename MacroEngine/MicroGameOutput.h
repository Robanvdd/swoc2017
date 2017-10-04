#ifndef MICROGAMEOUTPUT_H
#define MICROGAMEOUTPUT_H

#include <QList>
#include <QString>

class MicroGameOutputPlayer {
public:
    MicroGameOutputPlayer();
private:
    int m_id;
    QString m_name;
    QList<int> m_ufos;
};

class MicroGameOutput
{
public:
    MicroGameOutput();
    void readOutput(const QString& jsonString);

private:
    int m_gameId;
    QList<MicroGameOutputPlayer> m_players;
    int m_winner;
};

#endif // MICROGAMEOUTPUT_H
