#include "nextfilegrabber.h"

#include <QFileInfo>

NextFileGrabber::NextFileGrabber(QObject *parent) : QObject(parent)
{
}

QUrl NextFileGrabber::getNextFrameFileUrl(const QUrl &url)
{
    // Extract path and filename
    QFileInfo fileInfo(url.path());
    QString pathAndFilename = url.path();
    QString filename = fileInfo.fileName();
    int fileNameIndex = pathAndFilename.indexOf(filename);
    QString path = pathAndFilename.mid(0, fileNameIndex);

    // Dissect filename
    QString filePrefix = filename.split("_")[0];
    QString fileExtension = filename.split(".")[1];
    int frameNr = filename.split("_")[1].split(".")[0].toInt();

    // Generate next filename
    int nextFrameNr = frameNr + 1;
    QString nextFilename = filePrefix + "_" + QString::number(nextFrameNr) +
            "." + fileExtension;
    QUrl newUrl(url);
    newUrl.setPath(path + nextFilename);

    // Check for existence
    QFileInfo info(newUrl.path());
    if (info.exists() && info.isFile())
        return newUrl;
    else
        return "";
}
