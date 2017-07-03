#ifndef NEXTFILEGRABBER_H
#define NEXTFILEGRABBER_H

#include <QObject>
#include <QUrl>

class NextFileGrabber : public QObject
{
    Q_OBJECT
public:
    explicit NextFileGrabber(QObject *parent = nullptr);

    Q_INVOKABLE QUrl getNextFrameFileUrl(const QUrl& url);

signals:

public slots:
};

#endif // NEXTFILEGRABBER_H
