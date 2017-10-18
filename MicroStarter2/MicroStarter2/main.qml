import QtQuick 2.7
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.3
import QtQuick.Controls.Material 2.1
import QtQml 2.2
import QtQuick.Dialogs 1.2
import SWOC 1.0
import "Json.js" as Json

ApplicationWindow {
    width: 1024
    height: 768
    visible: true
    title: qsTr("MicroStarter")

    FileIO {
        id: fileIO
        source: ""
    }

    PlayersModel {
        id: playersModel
    }

    ColumnLayout {
        RowLayout {
            PlayersListView {
                id: playersListView
                playersModel: playersModel
            }

            PlayerInfoView {
                Layout.alignment: Qt.AlignTop

                playersModel: playersModel
                playersListView: playersListView
            }
        }

        GameView {
            playersModel: playersModel
            playersListView: playersListView
        }
    }
}
