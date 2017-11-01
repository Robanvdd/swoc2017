#ifndef PLAYER_H
#define PLAYER_H

#include "GameObject.h"

#include <QColor>
#include <QObject>

class Ufo;
class Universe;
class Player : public GameObject
{
    Q_OBJECT
public:
    Player(QString name, int hue, Universe* universe, QObject *parent = nullptr);

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

    Ufo* getUfo(int id) const;
    QString getColorName();
    double getHue() const;

signals:

public slots:

private:
    double m_credits;
    QString m_name;
    QList<Ufo*> m_ufos;
    QColor m_color;
    double m_hue;
    Universe* m_universe;
};

#endif // PLAYER_H
