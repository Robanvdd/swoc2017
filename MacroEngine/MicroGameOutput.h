#ifndef MICROGAMEOUTPUT_H
#define MICROGAMEOUTPUT_H

#include <QList>
#include <QString>

class MicroGameOutputPlayer {
public:
    MicroGameOutputPlayer();
    int getId() const;
    void setId(int id);

    QString getName() const;
    void setName(const QString& name);

    QList<int> getCasualties() const;
    void appendCasualty(int);
    void setCasualties(const QList<int>& ufos);

private:
    int m_id;
    QString m_name;
    QList<int> m_casualties;
};

class MicroGameOutput
{
public:
    MicroGameOutput();
    void readOutput(const QString& jsonString);

    int getGameId() const;

    QList<MicroGameOutputPlayer> getPlayers() const;

    int getWinner() const;

private:
    int m_gameId;
    QList<MicroGameOutputPlayer> m_players;
    int m_winner;
};

#endif // MICROGAMEOUTPUT_H
