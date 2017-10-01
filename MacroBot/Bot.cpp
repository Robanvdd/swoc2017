#include "Bot.h"
#include "MoveToPlanetCommand.h"

#include <iostream>

Bot::Bot(QObject *parent) : QObject(parent)
  , in(stdin)
{
}

void Bot::run()
{
    forever {
        QString line = in.readLine();
        QList<int> ufos;
        ufos << 1 << 2 << 4;

        QString command = MoveToPlanetCommand(ufos, 5).toJson();
        std::cout << command.toStdString() << std::endl;
    }
}
