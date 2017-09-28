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
//        if (line == "Ping?")
//            std::cout << "Pong!" << std::endl;
//        else if (line == "quit")
//            break;
//        else
//            std::cout << "Ik snap het niet :(." << std::endl;
        QList<int> ufos;
        ufos << 1 << 2 << 4;

        QString command = MoveToPlanetCommand(ufos, 5).toJson();
        std::cout << command.toStdString() << std::endl;
    }
}
