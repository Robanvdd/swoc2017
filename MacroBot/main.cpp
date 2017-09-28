#include <QCoreApplication>
#include <QTimer>

#include <Bot.h>
#include <iostream>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    std::cout << "Starting Bot" << std::endl;
    Bot bot(&a);
    QObject::connect(&bot, &Bot::finished, &a, [&a]() { a.quit(); });
    QObject::connect(&bot, &Bot::errorOccured, &a, [&a]() { a.quit(); });
    QTimer::singleShot(0, &bot, SLOT(run()));

    return a.exec();
}
