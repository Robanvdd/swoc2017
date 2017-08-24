import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0
import QtQuick.Controls 1.4
import QtQuick.Controls.Styles 1.4
import QtQuick.Dialogs 1.2
import QtGraphicalEffects 1.0
import SWOC 1.0

ApplicationWindow {
    id: appWindow
    visible: true
    width: 1024
    height: 768
    style: ApplicationWindowStyle {
            background: Image {
                source: "qrc:///Images/background.png"
            }
        }
    title: qsTr("MicroVis")
    property real zoomFactor: 1.0
    property int horizontalOffset: 0
    property int verticalOffset: 0
    property int arenaWidth: 1000
    property int arenaHeight: 700

    function calculateZoomFactor()
    {
        var margin = 50
        var widthRatio = appWindow.width / (appWindow.arenaWidth + margin)
        var heightRatio = appWindow.height / (appWindow.arenaHeight + margin)
        var smallestRatio = widthRatio < heightRatio ? widthRatio : heightRatio
        if (smallestRatio < 1.0)
            appWindow.zoomFactor = smallestRatio

        if (smallestRatio < 1.0 && heightRatio === smallestRatio)
        {
            appWindow.horizontalOffset = (appWindow.width - (appWindow.arenaWidth + margin) * smallestRatio) / 2
        }
        else if (smallestRatio < 1.0 && widthRatio === smallestRatio)
        {
            appWindow.verticalOffset = (appWindow.height - (appWindow.arenaHeight + margin) * smallestRatio) / 2
        }
    }

    function xTransformForZoom(value)
    {
        return value * appWindow.zoomFactor + horizontalOffset
    }

    function yTransformForZoom(value)
    {
        return value * appWindow.zoomFactor + verticalOffset
    }

    function sizeTransformForZoom(value)
    {
        return value * appWindow.zoomFactor
    }

    function parseJson(jsonObject)
    {
        try
        {
            appWindow.arenaWidth = jsonObject.arena.width
            appWindow.arenaHeight = jsonObject.arena.height

            calculateZoomFactor()

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
                    if (appContext.getBulletCount() <= k)
                        appContext.addBullet(posBul[0], posBul[1])
                    else
                        appContext.moveBullet(k, posBul[0], posBul[1])
                }
                while (appContext.getBulletCount() > jsonObject.projectiles)
                {
                    appContext.removeBullet()
                }
            }
        }
        catch (error)
        {
            messageDialog.text = "Error parsing json: " + error.message
            messageDialog.visible = true
        }
    }

    function parseJsonFirstFrame(jsonObject)
    {
        try
        {
            appWindow.arenaWidth = jsonObject.arena.width
            appWindow.arenaHeight = jsonObject.arena.height

            calculateZoomFactor()

            for (var l = 0; l < jsonObject.players.length; l++)
            {
                appContext.addPlayer(jsonObject.players[l].name, jsonObject.players[l].color)
                var spaceships = jsonObject.players[l].bots
                for (var m = 0; m < spaceships.length; m++)
                {
                    var posShip = spaceships[m].position.split(',')
                    var player = appContext.players[l]
                    player.addSpaceship(posShip[0], posShip[1])
                }
            }
        }
        catch (error)
        {
            messageDialog.text = "Error parsing json: " + error.message
            messageDialog.visible = true
        }
    }

    Item {
        id: root
        anchors.fill: parent

        FileIO {
            id: fileIO
            source: ""
            onError: {
                messageDialog.text = msg
                messageDialog.visible = true
            }
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
                        x: xTransformForZoom(modelData.x)
                        y: yTransformForZoom(modelData.y)
                        width: sizeTransformForZoom(64)
                        height: sizeTransformForZoom(64)

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
                x: xTransformForZoom(modelData.x)
                y: yTransformForZoom(modelData.y)
                width: sizeTransformForZoom(16)
                height: sizeTransformForZoom(16)
            }
        }
    }

    LaserFence {
        id: laserFence
        visible: true
        x: xTransformForZoom(15)
        y: yTransformForZoom(35)
        width: sizeTransformForZoom(appWindow.arenaWidth)
        height: sizeTransformForZoom(appWindow.arenaHeight)
    }

    MessageDialog {
        id: messageDialog
        title: "Error occurred!"
        onAccepted: {
            visible = false
        }

        visible: false
    }
}
