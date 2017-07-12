#ifndef CUSTOMFILEDIALOG_H
#define CUSTOMFILEDIALOG_H

#include <QObject>
#include <QFileDialog>
#include <QTimer>

class CustomFileDialog : public QObject
{
    Q_OBJECT
    Q_PROPERTY(bool visible MEMBER m_visible NOTIFY visibleChanged)
    Q_PROPERTY(QUrl fileUrl READ fileUrl NOTIFY accepted)
public:
    explicit CustomFileDialog(QObject *parent = nullptr);
    QUrl fileUrl() const;

signals:
    void visibleChanged();
    void accepted();
    void rejected();
public slots:

private:
    QFileDialog m_fileDialog;
    bool m_visible;
    QUrl m_fileUrl;
};

#endif // CUSTOMFILEDIALOG_H
