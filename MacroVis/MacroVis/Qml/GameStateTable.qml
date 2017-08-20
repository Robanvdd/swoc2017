import QtQuick 2.7
import QtQuick.Controls 2.1

Column {
    Grid {
        flow: Grid.TopToBottom
        rows: 3
        columnSpacing: 8

        Label {
            text: "ID"
            visible: gameBackend.objectId != -1
        }
        Label {
            text: "Game name"
            visible: gameBackend.objectId != -1
        }
        Label {
            text: "Current tick"
            visible: gameBackend.objectId != -1
        }

        Label {
            id: labelId
            text: gameBackend.objectId
            visible: gameBackend.objectId != -1
        }

        Label {
            id: labelName
            text: gameBackend.name
            visible: gameBackend.objectId != -1
        }

        Label {
            id: labelTick
            text: gameBackend.currentTick
            visible: gameBackend.objectId != -1
        }
    }
}
