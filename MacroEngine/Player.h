#ifndef PLAYER_H
#define PLAYER_H

#include "GameObject.h"
#include "Ufo.h"

#include <QObject>

class Player : public GameObject
{
    Q_OBJECT
public:
    explicit Player(QString name, QObject *parent = nullptr);

    double getCredits() const;
    void addCredits(double credits);
    void removeCredits(double credits);
    void giveUfo(Ufo* ufo);
    void removeUfo(Ufo* ufo);

    void applyTick(double durationInSeconds);

    QString getName() const;

    void writeState(QJsonObject& gameState);

signals:

public slots:

private:
    double m_credits;
    QString m_name;
    QList<Ufo*> m_ufos;
};

#endif // PLAYER_H
