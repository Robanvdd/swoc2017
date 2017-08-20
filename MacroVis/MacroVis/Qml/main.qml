import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0

import SWOC 1.0

ApplicationWindow {
    visible: true
    visibility: ApplicationWindow.FullScreen
    width: 1024
    height: 968
    title: qsTr("MacroVis")
    color: "black"

    GameLoader {
        id: fileDialogLoader
        Component.onCompleted: print("Check")
    }

    Game {
        id: gameBackend
    }

    Universe {
        id: universe
        anchors.fill: parent
    }

    Button {
        id: loadFileButton
        text: "Load file"
        onClicked: {
            fileDialogLoader.load()
        }
    }

    GameStateTable {
        id: gameStateTable
        anchors.top: loadFileButton.bottom
    }

}
