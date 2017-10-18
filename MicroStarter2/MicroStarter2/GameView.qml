import QtQuick 2.7
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.3
import QtQuick.Controls.Material 2.1
import QtQml 2.2
import QtQuick.Dialogs 1.2
import SWOC 1.0
import "Json.js" as Json

ColumnLayout {
    property PlayersModel playersModel
    property PlayersListView playersListView

    property string ticksPath: "X:\\path\\to\\ticks\\"
    property string enginePath: "X:\\path\\to\\executable\\"
    property string visPath: "X:\\path\\to\\executable\\"

    function toggleListViewIndex() {
        var index = playersListView.listView.currentIndex
        playersListView.listView.currentIndex = -1
        playersListView.listView.currentIndex = index
    }

    function getCleanPath(path) {
        path = path.replace(/^(file:\/{3})/,"")
        return decodeURIComponent(path)
    }

    Label {
        text: "Game: "
        font.bold: true
        font.pixelSize: 20
    }

    FileDialog {
        id: ticksFileDialog
        title: "Please choose a folder to save the game ticks in"
        selectFolder: true
        onAccepted: {
            ticksPath = getCleanPath(ticksFileDialog.folder.toString()) + "/"
            visible = false
        }
        visible: false
    }

    FileDialog {
        id: enginePathFileDialog
        title: "Please select the MicroGame executable"
        onAccepted: {
            enginePath = getCleanPath(enginePathFileDialog.fileUrl.toString())
            visible = false
        }
        visible: false
    }

    FileDialog {
        id: visPathFileDialog
        title: "Please select the MicroVis executable"
        onAccepted: {
            visPath = getCleanPath(visPathFileDialog.fileUrl.toString())
            visible = false
        }
        visible: false
    }

    RowLayout {
        Label {
            text: "Ticks folder: "
        }

        TextField {
            id: ticksTextField
            Layout.fillWidth: true
            selectByMouse: true
            placeholderText: ticksPath
        }

        Button {
            text: "Browse"
            onClicked: ticksFileDialog.visible = true
        }
    }

    RowLayout {
        Label {
            text: "MicroGame executable: "
        }

        TextField {
            id: enginePathTextField
            Layout.fillWidth: true
            selectByMouse: true
            placeholderText: enginePath
        }

        Button {
            text: "Browse"
            onClicked: enginePathFileDialog.visible = true
        }
    }

    RowLayout {
        Label {
            text: "MicroVis executable: "
        }

        TextField {
            id: visPathTextField
            Layout.fillWidth: true
            selectByMouse: true
            placeholderText: visPath
        }

        Button {
            text: "Browse"
            onClicked: visPathFileDialog.visible = true
        }
    }

    RowLayout {
        Button {
            text: "Load settings"
            onClicked: {
                var filePath = "file:///" + applicationDirPath + "/settings.json"
                fileIO.source = filePath
                var json = JSON.parse(fileIO.read(filePath))
                ticksPath = json.ticks
                enginePath = json.enginePath
                visPath = json.visPath
                Json.jsonToListModel(json, playersModel)
                toggleListViewIndex()
            }
        }

        Button {
            text: "Save settings"
            onClicked: {
                var json = Json.listModelToJson(playersModel, ticksPath, enginePath, visPath)
                var filePath = "file:///" + applicationDirPath + "/settings.json"
                fileIO.source = filePath
                fileIO.write(JSON.stringify(json))
            }
        }
    }

    Process {
        id: microGameProcess
        program: "java"
        arguments: "-jar " + enginePath + " --debug"
    }

    Process {
        id: microVisProcess
        program: visPath
        arguments: "tick_0.json"
    }

    Button {
        text: "Start Game"
        onClicked: {
            microGameProcess.start()
            var json = Json.listModelToJson(playersModel, ticksPath, enginePath, visPath)
            microGameProcess.write(JSON.stringify(json))

            microVisProcess.start()
        }
    }
}
