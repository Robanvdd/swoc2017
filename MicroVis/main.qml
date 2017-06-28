import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0
import SWOC 1.0

ApplicationWindow {
    visible: true
    width: 1024
    height: 768
    title: qsTr("Hello World")

    Item {
        id: root
        anchors.fill: parent

        FileIO {
            id: fileIO
            source: ""
            onError: console.log(msg)
        }

        Timer {
            id: gameTimer
            interval: 1000/30
            running: frameUrl != ""
            repeat: true
            property url frameUrl: ""
            onTriggered: {
                // Parse frame file
               //fileIO.source = frameUrl
                //var content = fileIO.read()
                //var jsonObject = JSON.parse(content)
                print(frameUrl)

                appContext.processFrame()

                frameUrl = filenameIncrementer.getNextFrameFileUrl(frameUrl)
            }
        }

        MouseArea {
            acceptedButtons: Qt.LeftButton | Qt.RightButton
            anchors.fill: parent
            onClicked: {
                if (mouse.button == Qt.LeftButton)
                    AppContext.addSpaceship(mouseX, mouseY)
                else if (mouse.button == Qt.RightButton)
                    AppContext.addBullet(mouseX, mouseY)
            }
        }

        Loader {
            id: fileDialogLoader
            property CustomFileDialog fileDialog: fileDialogLoader.item
            Connections {
                target: fileDialogLoader.item
                onAccepted: {
                    gameTimer.frameUrl = fileDialogLoader.fileDialog.fileUrl
                }
                onRejected: {
                    console.log("Canceled")
                }
            }
        }

        Component {
            id: fileDialogComponent
            CustomFileDialog {
            }
        }

        Button {
            id: pressMeButton
            text: "Press me"
            onClicked: {
                fileDialogLoader.sourceComponent = fileDialogComponent
                fileDialogLoader.fileDialog.visible = true
            }
        }

        Label {
            id: pressMeLabel
            text: "Default text"
            anchors.left: pressMeButton.right
            anchors.leftMargin: 16
        }

        Repeater {
            model: appContext.spaceships
            delegate: Spaceship {
                x: modelData.x
                y: modelData.y
                source: "qrc:///Images/ufo.png"
                visible: !modelData.dead
            }
        }

        Repeater {
            model: appContext.bullets
            delegate: Bullet {
                x: modelData.x
                y: modelData.y
                source: "qrc:///Images/bullet.png"
                visible: true
            }
        }
    }
}
