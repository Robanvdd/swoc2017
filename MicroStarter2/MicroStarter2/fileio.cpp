#include "fileio.h"
#include <QFile>
#include <QTextStream>

FileIO::FileIO(QObject *parent) : QObject(parent)
{
}

QUrl FileIO::source() const
{
    return m_source;
}

void FileIO::setSource(const QUrl &source)
{
    m_source = source;
}

QString FileIO::read()
{
    if (m_source.isEmpty()){
        emit error("source is empty");
        return QString();
    }

    auto localFile = m_source.toLocalFile();
    QFile file(localFile);
    QString fileContent;
    if ( file.open(QIODevice::ReadOnly) ) {
        QString line;
        QTextStream t( &file );
        do {
            line = t.readLine();
            fileContent += line;
        } while (!line.isNull());

        file.close();
    } else {
        emit error("Unable to open the file");
        return QString();
    }
    return fileContent;
}

bool FileIO::write(const QString& data) const
{
    if (m_source.isEmpty())
        return false;

    QString source = m_source.toLocalFile();
    QFile file(source);
    if (!file.open(QFile::WriteOnly | QFile::Truncate))
        return false;

    QTextStream out(&file);
    out << data;

    file.close();

    return true;
}
