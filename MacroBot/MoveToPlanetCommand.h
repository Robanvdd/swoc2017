#ifndef MOVETOPLANETCOMAND_H
#define MOVETOPLANETCOMAND_H

#include <QObject>

class MoveToPlanetCommand : public QObject
{
    Q_OBJECT
public:
    MoveToPlanetCommand(QList<int> ufos, int planetId, QObject *parent = nullptr);
    QString toJson() const;

signals:

public slots:

private:
    QString m_command;
    QList<int> m_ufos;
    int m_planetId;
};

#endif // MOVETOPLANETCOMAND_H
