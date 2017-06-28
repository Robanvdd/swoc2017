#include "filenameincrementer.h"

FilenameIncrementer::FilenameIncrementer(QObject *parent) : QObject(parent)
{
}

QUrl FilenameIncrementer::getNextFrameFileUrl(const QUrl &url)
{
    // Assumes file name structure:
    QString filename = url.fileName();
    QString filePrefix = filename.split("_")[0];
    QString fileExtension = filename.split(".")[1];
    int frameNr = filename.split("_")[1].split(".")[0].toInt();
    int nextFrameNr = frameNr + 1;
    QUrl newUrl = url;
    newUrl.set = filePrefix + "_" + nextFrameNr + "." + fileExtension;
    return newUrl;
}
