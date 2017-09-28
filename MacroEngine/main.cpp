#include <QCoreApplication>
#include <QTimer>
#include <iostream>

#include <Engine.h>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    if (argc < 2)
    {
        std::cout << "Need path to executable as argument" << std::endl;
        return -1;
    }

    Engine* engine = new Engine(argv[1], &a);
    QObject::connect(engine, &Engine::finished, &a, [&a]() { a.quit(); });
    QObject::connect(engine, &Engine::errorOccured, &a, [&a]() { a.quit(); });
    QObject::connect(engine, &Engine::destroyed, &a, [&a]() { a.quit(); });
    QTimer::singleShot(0, engine, SLOT(startNewMacroGame()));

    return a.exec();
}
