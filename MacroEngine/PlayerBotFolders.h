#ifndef PLAYERBOTFOLDERS_H
#define PLAYERBOTFOLDERS_H

#include <QObject>

class PlayerBotFolders : public QObject
{
    Q_OBJECT
public:
    PlayerBotFolders();

    QString getPlayerName() const;
    void setPlayerName(const QString& playerName);

    QString getMacroBotFolder() const;
    void setMacroBotFolder(const QString& macroBotFolder);

    QString getMicroBotFolder() const;
    void setMicroBotFolder(const QString& microBotFolder);

private:
    QString m_playerName;
    QString m_macroBotFolder;
    QString m_microBotFolder;
};

#endif // PLAYERBOTFOLDERS_H
