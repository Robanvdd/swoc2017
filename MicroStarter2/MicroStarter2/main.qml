import QtQuick 2.7
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.3
import QtQuick.Controls.Material 2.1
import QtQml 2.2
import SWOC 1.0
import "Json.js" as Json

ApplicationWindow {
    visible: true
    width: 640
    height: 480
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
                playersModel: playersModel
                playersListView: playersListView
            }
        }

        Label {
            text: "Game: "
            font.bold: true
            font.pixelSize: 20
        }

        Button {
            text: "Save settings"
            onClicked: {
                var json = Json.listModelToJson(playersModel, "C:\\Bla")
                var filePath = "file:///" + applicationDirPath + "/settings.json"
                fileIO.source = filePath
                fileIO.write(JSON.stringify(json))
            }
        }
    }
}
