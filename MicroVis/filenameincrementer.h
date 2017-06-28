#ifndef FILENAMEINCREMENTER_H
#define FILENAMEINCREMENTER_H

#include <QObject>
#include <QUrl>

class FilenameIncrementer : public QObject
{
    Q_OBJECT
public:
    explicit FilenameIncrementer(QObject *parent = nullptr);

    Q_INVOKABLE QUrl getNextFrameFileUrl(const QUrl& url);

signals:

public slots:
};

#endif // FILENAMEINCREMENTER_H
