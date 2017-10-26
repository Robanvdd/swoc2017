import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Controls.Material 2.0
import QtQuick.Layouts 1.0
import QtQuick.Dialogs 1.2

import swoc 1.0
import "Json.js" as Json

ApplicationWindow {
    visible: true
    width: 980
    height: 650
    title: qsTr("MicroStarter")

    property string ticksPath: "X:\\path\\to\\ticks\\"
    property string enginePath: "X:\\path\\to\\executable\\"
    property string visPath: "X:\\path\\to\\executable\\"

    function getCleanPath(path) {
        path = path.replace(/^(file:\/{3})/,"")
        return decodeURIComponent(path)
    }

    Timer {
        id: microVisTimer
        interval: 5000
        running: false
        repeat: false
        onTriggered: microVisProcess.start()
    }

    FileIO {
        id: fileIO
        source: ""
    }

    Item {
        id: root
        anchors.fill: parent

        PlayerModel {
            id: playerModel
            Component.onCompleted: {
                addPlayer("Bulldozer Broos", "X:\\path\\to\\bot\\", 3, 0.0)
                addPlayer("Hueling Huegens", "X:\\path\\to\\bot\\", 3, 0.125)
                addPlayer("Brawling Berendse", "X:\\path\\to\\bot\\", 3, 0.25)
                addPlayer("Ferdinator", "X:\\path\\to\\bot\\", 3, 0.375)
                addPlayer("Destruction Dennen", "X:\\path\\to\\bot\\", 3, 0.5)
                addPlayer("Banisher Beurskens", "X:\\path\\to\\bot\\", 3, 0.625)
            }
        }

        property Player currentPlayer: playerModel.getPlayer(playersList.currentIndex)

        ColumnLayout {
            RowLayout {
                ColumnLayout {
                    Label {
                        text: "Players:"
                        font.bold: true
                        font.pixelSize: 20
                    }

                    Rectangle {
                        height: 250
                        width: 250
                        color: "DarkGrey"
                        ListView {
                            id: playersList
                            anchors.top: parent.top
                            anchors.bottom: parent.bottom
                            width: 250
                            model: playerModel
                            delegate: Label {
                                text: name
                                font.pointSize: 12
                                color: playersList.currentIndex == index ? "red" : "black"
                                MouseArea {
                                    anchors.fill: parent
                                    onClicked: playersList.currentIndex = index
                                }
                            }
                            ScrollBar.vertical: ScrollBar {}
                            highlight: Rectangle { color: Material.accent }
                            clip: true
                        }
                    }

                    RowLayout {
                        Button {
                            text: "Add"
                            onClicked: {
                                playerModel.addPlayer("Dumbdumb", "Test", 5, 1.0)
                            }
                        }
                        Button {
                            text: "Remove"
                            onClicked: {
                                playerModel.removePlayer(playersList.currentIndex)
                            }
                        }
                    }
                }

                Column {
                    Layout.alignment: Qt.AlignTop
                    anchors.left: playersList.right
                    width: 700

                    Label {
                        text: "Player info:"
                        font.bold: true
                        font.pixelSize: 20
                    }

                    RowLayout {
                        width: parent.width
                        Label {
                            text: "Name: "
                        }

                        TextField {
                            color: Material.accent
                            font.pointSize: 12
                            placeholderText: root.currentPlayer.name
                            Layout.fillWidth: true
                            Keys.onReturnPressed: {
                                root.currentPlayer.name = text
                                text = ""
                            }
                        }
                    }

                    RowLayout {
                        width: parent.width
                        Label {
                            text: "#UFOs: "
                        }

                        TextField {
                            color: Material.accent
                            font.pointSize: 12
                            placeholderText: root.currentPlayer.nrUfos
                            Layout.fillWidth: true
                            Keys.onReturnPressed: {
                                root.currentPlayer.nrUfos = parseInt(text)
                                text = ""
                            }
                        }
                    }

                    RowLayout {
                        width: parent.width
                        Label {
                            text: "Bot path: "
                        }

                        TextField {
                            color: Material.accent
                            font.pointSize: 12
                            placeholderText: root.currentPlayer.bot
                            Layout.fillWidth: true
                            Keys.onReturnPressed: {
                                root.currentPlayer.bot = text
                                text = ""
                            }
                        }

                        FileDialog {
                            id: fileDialog
                            title: "Please choose a path to a micro bot"
                            selectFolder: true
                            onAccepted: {
                                var path = fileDialog.folder.toString()
                                root.currentPlayer.bot = getCleanPath(path) + "/"
                                visible = false
                            }
                            visible: false
                        }

                        Button {
                            text: "..."
                            onClicked: fileDialog.visible = true
                        }
                    }

                    RowLayout {
                        width: parent.width
                        Label {
                            text: "Hue: "
                        }

                        TextField {
                            color: Material.accent
                            font.pointSize: 12
                            placeholderText: root.currentPlayer.hue
                            Layout.fillWidth: true
                            Keys.onReturnPressed: {
                                root.currentPlayer.hue = parseFloat(text)
                                text = ""
                            }
                        }

                        Rectangle {
                            width: 35
                            height: 35
                            color: Qt.hsla(root.currentPlayer.hue, 1.0, 0.5, 1.0)
                        }
                    }
                }
            }

            ColumnLayout {
                Label {
                    text: "Game info:"
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
                        text: "..."
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
                        text: "..."
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
                        text: "..."
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
                            Json.jsonToListModel(json, playerModel)

                            playersList.model = 0
                            playersList.model = playerModel
                        }
                    }

                    Button {
                        text: "Save settings"
                        onClicked: {
                            var json = Json.listModelToJson(playerModel, ticksPath, enginePath, visPath)
                            var filePath = "file:///" + applicationDirPath + "/settings.json"
                            fileIO.source = filePath
                            var strJson = JSON.stringify(json)
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
                    arguments: "file:///" + ticksPath + "tick_0.json"
                }

                Button {
                    text: "Start Game"
                    onClicked: {
                        microGameProcess.start()
                        var json = Json.listModelToJson(playerModel, ticksPath, enginePath, visPath)
                        microGameProcess.write(JSON.stringify(json))
                        microVisTimer.start()
                    }
                }
            }
        }
    }
}
