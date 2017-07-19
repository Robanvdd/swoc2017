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

    QQmlApplicationEngine engine;
    engine.rootContext()->setContextProperty("appContext", &appContext);
    engine.rootContext()->setContextProperty("nextFileGrabber", &nextFileGrabber);

    qmlRegisterType<FileIO>("SWOC", 1, 0, "FileIO");
    qmlRegisterType<CustomFileDialog>("SWOC", 1, 0, "CustomFileDialog");
    qmlRegisterInterface<Spaceship>("Spaceship");
    qmlRegisterInterface<Bullet>("Bullet");
    qmlRegisterInterface<Player>("Player");

    engine.load(QUrl(QLatin1String("qrc:/main.qml")));

    return app.exec();
}
