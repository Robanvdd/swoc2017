import QtQuick 2.6
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.3
import QtQuick.Controls.Material 2.1
import QtQml 2.2
import QtQuick.Dialogs 1.2

Column {
    property PlayersModel playersModel
    property PlayersListView playersListView

    width: 250

    function getCurrentIndex() {
        var currentIndex = playersListView.listView.currentIndex
        return currentIndex
    }

    function getSelectedPlayer() {
        var player = playersModel.get(getCurrentIndex())
        return playersModel.get(getCurrentIndex())
    }

    function setProperty(property, value) {
        playersModel.setProperty(getCurrentIndex(), property, value)
    }

    function clearTextField(field) {
        field.text = ""
    }

    function getCleanPath(path) {
        path = path.replace(/^(file:\/{3})/,"")
        return decodeURIComponent(path)
    }

    Label {
        text: "Player info:"
        font.bold: true
        font.pixelSize: 20
    }

    RowLayout {
        Label {
            text: "Number of UFOs: "
        }

        TextField {
            id: nrUfosTextField
            Layout.fillWidth: true
            selectByMouse: true
            placeholderText: getCurrentIndex() >= 0 ? getSelectedPlayer().nrUfos : 0
        }

        Button {
            text: "Set"
            onClicked: {
                var nrUfos = parseInt(nrUfosTextField.text)
                setProperty("nrUfos", nrUfos)
                clearTextField(nrUfosTextField)
            }
        }
    }

    RowLayout {
        Label {
            text: "Hue: "
        }

        TextField {
            id: hueTextField
            Layout.fillWidth: true
            selectByMouse: true
            placeholderText: getCurrentIndex() >= 0 ? getSelectedPlayer().hue : 0.0
        }

        Button {
            text: "Set"
            onClicked: {
                var hue = parseFloat(hueTextField.text)
                setProperty("hue", hue)
                clearTextField(hueTextField)
            }
        }

        Rectangle {
            height: hueTextField.height - 8
            width: height
            color: getCurrentIndex() >= 0 ? Qt.hsla(getSelectedPlayer().hue, 1.0, 0.5, 1.0) : "#ffffff"
        }
    }

    FileDialog {
        id: fileDialog
        title: "Please choose a path to a micro bot"
        selectFolder: true
        onAccepted: {
            var path = fileDialog.folder.toString()
            setProperty("bot", getCleanPath(path))
            visible = false
        }
        visible: false
    }

    RowLayout {
        Label {
            text: "Bot: "
        }

        TextField {
            id: botTextField
            Layout.fillWidth: true
            selectByMouse: true
            placeholderText: getCurrentIndex() >= 0 ? getSelectedPlayer().bot : "..."
        }

        Button {
            text: "Browse"
            onClicked: fileDialog.visible = true
        }
    }
}
