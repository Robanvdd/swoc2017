#include <QApplication>
#include <QQmlApplicationEngine>
#include <QQmlContext>
#include "spaceship.h"
#include "appcontext.h"
#include "fileio.h"
#include "customfiledialog.h"
#include "nextfilegrabber.h"

int main(int argc, char *argv[])
{
    AppContext appContext;
    NextFileGrabber nextFileGrabber;

    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
    QApplication app(argc, argv);

    QUrl firstTick("");
    if (argc > 1)
        firstTick = argv[1];

    QQmlApplicationEngine engine;
    engine.rootContext()->setContextProperty("appContext", &appContext);
    engine.rootContext()->setContextProperty("nextFileGrabber", &nextFileGrabber);
    engine.rootContext()->setContextProperty("firstTick", firstTick);

    qmlRegisterType<FileIO>("SWOC", 1, 0, "FileIO");
    qmlRegisterType<CustomFileDialog>("SWOC", 1, 0, "CustomFileDialog");
    qmlRegisterInterface<Spaceship>("Spaceship");
    qmlRegisterInterface<Bullet>("Bullet");
    qmlRegisterInterface<Player>("Player");

    engine.load(QUrl(QLatin1String("qrc:/main.qml")));

    return app.exec();
}
