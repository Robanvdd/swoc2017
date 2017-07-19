#ifndef PLANETIMAGEPROVIDER_H
#define PLANETIMAGEPROVIDER_H

#include <QObject>
#include <QUrl>

class PlanetImageProvider : public QObject
{
    Q_OBJECT
public:
    explicit PlanetImageProvider(QObject *parent = 0);
    Q_INVOKABLE QUrl getRandomPlanet() const;

signals:

public slots:

private:
    QList<QUrl> m_planetUrls;
};

#endif // PLANETIMAGEPROVIDER_H
