#include "Player.h"
#include "PlayerModel.h"
#include "process.h"
#include "fileio.h"

#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>

int main(int argc, char *argv[])
{
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
    QGuiApplication app(argc, argv);

    QQmlApplicationEngine engine;
    qmlRegisterType<Player>("swoc", 1, 0, "Player");
    qmlRegisterType<PlayerModel>("swoc", 1, 0, "PlayerModel");
    qmlRegisterType<FileIO>("swoc", 1, 0, "FileIO");
    qmlRegisterType<Process>("swoc", 1, 0, "Process");
    engine.rootContext()->setContextProperty("applicationDirPath", QGuiApplication::applicationDirPath());

    engine.load(QUrl(QLatin1String("qrc:/main.qml")));

    return app.exec();
}
