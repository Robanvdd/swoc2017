#ifndef PLAYER_H
#define PLAYER_H

#include "GameObject.h"

#include <QColor>
#include <QObject>

class Ufo;
class Player : public GameObject
{
    Q_OBJECT
public:
    explicit Player(int hue, QString name, QObject *parent = nullptr);

    double getCredits() const;
    void addCredits(double credits);
    void removeCredits(double credits);
    void giveUfo(Ufo* ufo);
    void removeUfo(Ufo* ufo);

    bool hasUfo(int id) const;

    void applyTick(double durationInSeconds);

    QString getName() const;

    void writeState(QJsonObject& gameState);

    QList<Ufo*> getUfos() const;

    QColor getColor() const;
    void setColor(const QColor& color);

    Ufo*getUfo(int id) const;
    QString getColorName();
signals:

public slots:

private:
    double m_credits;
    QString m_name;
    QList<Ufo*> m_ufos;
    QColor m_color;
};

#endif // PLAYER_H
