import QtQuick 2.6
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.3
import QtQuick.Controls.Material 2.1

Column {
    property PlayersModel playersModel
    property ListView listView: playersListView

    width: 250

    Label {
        text: "Players:"
        font.bold: true
        font.pixelSize: 20
    }

    Component {
        id: playerDelegate
        Item {
            height: playerLabel.height + 8
            width: parent.width

            Label {
                id: playerLabel
                text: name
                width: parent.width
                anchors.centerIn: parent
            }

            MouseArea {
                anchors.fill: parent
                onClicked: playersListView.currentIndex = index
            }
        }
    }

    Rectangle {
        height: 250
        width: parent.width
        color: "DarkGrey"
        ListView {
            currentIndex: 0
            id: playersListView
            height: parent.height
            width: parent.width
            ScrollBar.vertical: ScrollBar {}
            model: playersModel
            delegate: playerDelegate
            highlight: Rectangle { color: Material.accent }
            clip: true
        }
    }

    RowLayout {
        width: parent.width

        Button {
            Layout.alignment: Qt.AlignLeft
            text: "Add"
            onClicked: {
                if (playerTextField.text != "")
                    playersModel.append({ "name": playerTextField.text })
                playerTextField.text = ""
            }
        }

        TextField {
            id: playerTextField
            Layout.fillWidth: true
            placeholderText: "Enter player name..."
            selectByMouse: true
        }
    }

    Button {
        width: parent.width
        text: "Remove selected"
        onClicked: {
            if (playersListView.currentIndex != -1)
                playersModel.remove(playersListView.currentIndex)
        }
    }
}
