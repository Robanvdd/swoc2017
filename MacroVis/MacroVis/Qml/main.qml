import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0

import SWOC 1.0

ApplicationWindow {
    visible: true
    visibility: ApplicationWindow.Maximized
    width: 1024
    height: 968
    title: qsTr("MacroVis")
    color: "black"
    property int tickDuration: 250
    GameLoader {
        id: fileDialogLoader
    }

    Universe {
        id: universeFlick
        anchors.fill: parent
    }

    MouseArea {
        anchors.fill: parent
        acceptedButtons: Qt.NoButton
        onWheel: {
            universeFlick.universe.scale *= 1 + 0.1*(wheel.angleDelta.y / 120)
        }
    }

    Column {

        Button {
            id: loadFileButton
            text: "Load file"
            onClicked: {
                fileDialogLoader.load()
            }
        }

        Button {
            id: zoomInButton
            text: "Zoom in"
            onClicked: {
                universeFlick.universe.scale *= 1.1;

                universeFlick.returnToBounds()
            }
        }

        Button {
            id: zoomOutButton
            text: "Zoom out"
            onClicked: {
                universeFlick.universe.scale *= 0.9;

                universeFlick.returnToBounds()
            }
        }

        GameStateTable {
            id: gameStateTable
        }
    }
}
