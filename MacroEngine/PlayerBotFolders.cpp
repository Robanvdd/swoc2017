#include "PlayerBotFolders.h"

PlayerBotFolders::PlayerBotFolders()
{

}

QString PlayerBotFolders::getPlayerName() const
{
    return m_playerName;
}

void PlayerBotFolders::setPlayerName(const QString& playerName)
{
    m_playerName = playerName;
}

QString PlayerBotFolders::getMacroBotFolder() const
{
    return m_macroBotFolder;
}

void PlayerBotFolders::setMacroBotFolder(const QString& macroBotFolder)
{
    m_macroBotFolder = macroBotFolder;
}

QString PlayerBotFolders::getMicroBotFolder() const
{
    return m_microBotFolder;
}

void PlayerBotFolders::setMicroBotFolder(const QString& microBotFolder)
{
    m_microBotFolder = microBotFolder;
}
