#include "customfiledialog.h"

CustomFileDialog::CustomFileDialog(QObject *parent) : QObject(parent)
{
    m_fileDialog.setOption(QFileDialog::DontUseNativeDialog, true);
    m_fileDialog.setFileMode(QFileDialog::ExistingFile);
    m_fileDialog.setNameFilter("JSON Files (*.json)");
    m_fileDialog.setViewMode(QFileDialog::Detail);
    connect(this, &CustomFileDialog::visibleChanged, this, [this]() {
        if (m_visible)
        {
            m_visible = false;
            if (m_fileDialog.exec())
            {
                auto files = m_fileDialog.selectedFiles();
                if (files.count() == 1)
                {
                    m_fileUrl = QUrl::fromLocalFile(files.first());
                    emit accepted();
                    return;
                }
            }
        }
        emit rejected();
    });

}

QUrl CustomFileDialog::fileUrl() const
{
    return m_fileUrl;
}


