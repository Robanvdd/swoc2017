import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0
import QtQuick.Controls 1.4
import QtQuick.Controls.Styles 1.4
import QtGraphicalEffects 1.0
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
                var posBot = bots[j].position.split(',')
                appContext.players[i].moveSpaceship(j, posBot[0], posBot[1])
            }

            var bullets = jsonObject.projectiles
            for (var k = 0; k < bullets.length; k++)
            {
                var posBul = bullets[k].position.split(',')
                appContext.moveBullet(k, posBul[0], posBul[1])
            }
        }
    }

    function parseJsonFirstFrame(jsonObject)
    {
        for (var l = 0; l < jsonObject.players.length; l++)
        {
            appContext.addPlayer("SomeName", jsonObject.players[l].color)
            var spaceships = jsonObject.players[l].bots
            for (var m = 0; m < spaceships.length; m++)
            {
                var posShip = spaceships[m].position.split(',')
                var player = appContext.players[l];
                player.addSpaceship(posShip[0], posShip[1])
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
            property bool firstTrigger: false
            onTriggered: {
                // Parse frame file
                fileIO.source = frameUrl
                var content = fileIO.read()
                var jsonObject = JSON.parse(content)

                if (firstTrigger)
                {
                    firstTrigger = false
                    parseJsonFirstFrame(jsonObject)
                }

                parseJson(jsonObject)

                frameUrl = nextFileGrabber.getNextFrameFileUrl(frameUrl)
            }
        }

        Loader {
            id: fileDialogLoader
            property CustomFileDialog fileDialog: fileDialogLoader.item
            Connections {
                target: fileDialogLoader.item
                onAccepted: {
                    gameTimer.firstTrigger = true
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
            id: loadGameButton
            text: "Load Game"
            onClicked: {
                appContext.clearPlayers()
                appContext.clearBullets()
                fileDialogLoader.sourceComponent = fileDialogComponent
                fileDialogLoader.fileDialog.visible = true
            }
            z: 1
        }

        Repeater {
            id: outerRepeater
            model: appContext.players
            delegate: Item {
                property color playerColor: color
                Repeater {
                    model: modelData.spaceships
                    delegate: Spaceship {
                        property color playerColor: parent.playerColor
                        id: aSpaceShip
                        x: modelData.x
                        y: modelData.y
                        source: "qrc:///Images/ufo.png"
                        visible: true

                        ColorOverlay {
                            anchors.fill: aSpaceShip
                            source: aSpaceShip
                            color: aSpaceShip.playerColor
                        }
                    }
                }
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
