#ifndef PLAYER_H
#define PLAYER_H

#include <QObject>

class Player : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString name READ getName WRITE setName NOTIFY nameChanged)
    Q_PROPERTY(QString bot READ getBot WRITE setBot NOTIFY botChanged)
    Q_PROPERTY(int nrUfos READ getNrUfos WRITE setNrUfos NOTIFY nrUfosChanged)
    Q_PROPERTY(double hue READ getHue WRITE setHue NOTIFY hueChanged)
public:
    explicit Player(QObject *parent = 0);
    Player(QString name, QString bot, int nrOfUfos, double hue);

    QString getBot() const;
    void setBot(const QString& bot);

    int getNrUfos() const;
    void setNrUfos(int nrUfos);

    double getHue() const;
    void setHue(double hue);

    QString getName() const;
    void setName(const QString& name);

signals:
    void nameChanged();
    void botChanged();
    void nrUfosChanged();
    void hueChanged();

public slots:

private:
    QString m_bot;
    int m_nrUfos;
    double m_hue;
    QString m_name;
};

#endif // PLAYER_H
