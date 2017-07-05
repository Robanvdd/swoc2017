import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0
import QtQuick.Controls 1.4
import QtQuick.Controls.Styles 1.4
import SWOC 1.0

ApplicationWindow {
    visible: true
    width: 1024
    height: 768
    style: ApplicationWindowStyle {
            background: Image {
                source: "qrc:///Images/background.png"
            }
        }
    title: qsTr("MicroVis")

    function parseJson(jsonObject)
    {
        for (var i = 0; i < jsonObject.players.length; i++)
        {
            var bots = jsonObject.players[i].bots
            for (var j = 0; j < bots.length; j++)
            {
                var posShip = bots[j].position.split(',')
                appContext.moveSpaceship(j, posShip[0], posShip[1])
            }

            var bullets = jsonObject.projectiles
            for (var k = 0; k < bullets.length; k++)
            {
                var posBul = bullets[k].position.split(',')
                appContext.moveBullet(k, posBul[0], posBul[1])
            }
        }
    }

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
                fileIO.source = frameUrl
                var content = fileIO.read()
                var jsonObject = JSON.parse(content)
                parseJson(jsonObject)

                appContext.processFrame()

                frameUrl = nextFileGrabber.getNextFrameFileUrl(frameUrl)
            }
        }

        MouseArea {
            acceptedButtons: Qt.LeftButton | Qt.RightButton
            anchors.fill: parent
            onClicked: {
                if (mouse.button === Qt.LeftButton)
                    appContext.addSpaceship(mouseX, mouseY)
                else if (mouse.button === Qt.RightButton)
                    appContext.addBullet(mouseX, mouseY)
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
