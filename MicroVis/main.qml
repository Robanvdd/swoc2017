import QtQuick 2.8
import QtQuick.Controls 2.1
import QtQuick.Controls.Material 2.1
import QtQuick.Layouts 1.0
import QtQuick.Dialogs 1.2
import QtGraphicalEffects 1.0
import SWOC 1.0

ApplicationWindow {
    id: appWindow
    visible: true
    width: 1024
    height: 768
    title: qsTr("MicroVis")
    property real zoomFactor: 1.0
    property int horizontalOffset: 0
    property int verticalOffset: 0
    property int arenaWidth: 1000
    property int arenaHeight: 700
    property int tick: 0
    property int nrUfos: 0
    property int nrBullets: 0
    property bool showDebug: false

    function calculateTransforms(jsonObject)
    {
        appWindow.arenaWidth = jsonObject.arena.width
        appWindow.arenaHeight = jsonObject.arena.height

        var margin = 50
        var widthRatio = appWindow.width / (appWindow.arenaWidth + margin)
        var heightRatio = appWindow.height / (appWindow.arenaHeight + margin)
        var smallestRatio = widthRatio < heightRatio ? widthRatio : heightRatio
        if (smallestRatio < 1.0)
            appWindow.zoomFactor = smallestRatio
        var biggestZoomFactor = 2
        if (appWindow.zoomFactor > biggestZoomFactor)
            appWindow.zoomFactor = biggestZoomFactor

        if (smallestRatio < 1.0 && heightRatio === smallestRatio)
        {
            appWindow.horizontalOffset = (appWindow.width - (appWindow.arenaWidth + margin) * appWindow.zoomFactor) / 2
        }
        else if (smallestRatio < 1.0 && widthRatio === smallestRatio)
        {
            appWindow.verticalOffset = (appWindow.height - (appWindow.arenaHeight + margin) * appWindow.zoomFactor) / 2
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

    function initializePlayers(jsonObject)
    {
        for (var l = 0; l < jsonObject.players.length; l++)
        {
            var player = jsonObject.players[l]
            appContext.addPlayer(player.id, player.name, player.color)
        }
    }

    function initializeShips(jsonObject)
    {
        for (var l = 0; l < jsonObject.players.length; l++)
        {
            var player = appContext.players[l]
            var spaceships = jsonObject.players[l].bots
            for (var m = 0; m < spaceships.length; m++)
            {
                var spaceship = spaceships[m]
                player.addSpaceship(spaceship.id, spaceship.name,
                  spaceship.position.x, spaceship.position.y)
                nrUfos++;
            }
        }
    }

    function updateSpaceships(jsonObject)
    {
        for (var i = 0; i < jsonObject.players.length; i++)
        {
            var player = jsonObject.players[i]
            var bots = player.bots
            for (var j = 0; j < bots.length; j++)
            {
                var hp = bots[j].hitpoints;
                var posBot = bots[j].position
                appContext.players[i].moveSpaceship(j, posBot.x, posBot.y)
                if (appContext.players[i].getSpaceshipIsAlive(j) && hp <= 0)
                    nrUfos--
                appContext.players[i].setSpaceshipHp(j, hp);
            }
        }
    }

    function updateProjectiles(jsonObject)
    {
        var bullets = jsonObject.projectiles
        for (var k = 0; k < bullets.length; k++)
        {
            var bullet = bullets[k];
            var posBul = bullet.position
            if (!appContext.hasBullet(bullet.id))
            {
                appContext.addBullet(bullet.id, posBul.x, posBul.y)
                nrBullets++
            }
            else
            {
                appContext.moveBullet(bullet.id, posBul.x, posBul.y)
            }
        }
        var removedBulletIds = jsonObject.hits;
        for (var l = 0; l < removedBulletIds.length; l++)
        {
            appContext.removeBullet(removedBulletIds[l])
            nrBullets--
        }
    }

    function parseJson(jsonObject, firstFrame)
    {
        try
        {
            tick = jsonObject.tick

            if (firstFrame)
            {
                laserFence.visible = true
                initializePlayers(jsonObject)
                initializeShips(jsonObject)
                // projectiles init and destroy dynamically
            }

            calculateTransforms(jsonObject)

            // players do not update (only their spaceships)
            updateSpaceships(jsonObject)
            updateProjectiles(jsonObject)
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

        Image {
            id: background
            anchors.fill: parent
            fillMode: Image.Tile
            source: "qrc:///Images/background.png"
        }

        LaserFence {
            id: laserFence
            visible: false
            x: xTransformForZoom(0)
            y: yTransformForZoom(0)
            width: sizeTransformForZoom(appWindow.arenaWidth)
            height: sizeTransformForZoom(appWindow.arenaHeight)
        }

        Rectangle {
            id: debugFence
            visible: showDebug
            x: xTransformForZoom(0)
            y: yTransformForZoom(0)
            width: sizeTransformForZoom(appWindow.arenaWidth)
            height: sizeTransformForZoom(appWindow.arenaHeight)
            border.color: "red"
            color: "transparent"
        }

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
                fileIO.source = frameUrl
                var content = fileIO.read()
                var jsonObject = JSON.parse(content)

                parseJson(jsonObject, firstTrigger)
                if (firstTrigger) firstTrigger = false

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
                        width: sizeTransformForZoom(32)
                        height: sizeTransformForZoom(32)
                        visible: modelData.hp > 0

                        Column {
                            anchors.bottom: parent.top
                            anchors.horizontalCenter: parent.horizontalCenter
                            anchors.bottomMargin: 5

                            Label {
                                visible: showDebug
                                anchors.horizontalCenter: parent.horizontalCenter
                                text: "id: " + modelData.id + ", pos: (" + modelData.x + ", " + modelData.y + ")"
                            }

                            Rectangle {
                                anchors.horizontalCenter: parent.horizontalCenter
                                color: "red"
                                width: 50
                                height: 8
                                border.color: "white"
                                border.width: 1

                                Rectangle {
                                    color: "green"
                                    anchors.left: parent.left
                                    anchors.top: parent.top
                                    anchors.bottom: parent.bottom
                                    anchors.margins: 1
                                    width: (parent.width * modelData.hp / 100.0) - 2 * parent.border.width
                                }
                            }
                        }

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

                Label {
                    anchors.horizontalCenter: parent.horizontalCenter
                    anchors.bottom: parent.top
                    text: "id: " + modelData.id + ", pos: (" + modelData.x + ", " + modelData.y + ")"
                    visible: showDebug
                }
            }
        }

        Column {
            anchors.top: parent.top
            anchors.left: parent.left
            anchors.margins: 16

            Button {
                id: loadGameButton
                text: "Load Game"

                onClicked: {
                    gameTimer.frameUrl = ""
                    tick = 0
                    nrUfos = 0
                    nrBullets = 0
                    appContext.clearPlayers()
                    appContext.clearBullets()
                    fileDialogLoader.sourceComponent = fileDialogComponent
                    fileDialogLoader.fileDialog.visible = true
                }
                Material.background: "#3F51B5"
            }

            Button {
                id: debugButton
                text: showDebug ? "Hide debug" : "Show debug"
                onClicked: showDebug = !showDebug
                Material.background: "#3F51B5"
            }

            Label {
                id: tickCounter
                text: "Ticks: " + tick
                visible: showDebug
            }

            Label {
                id: ufoCounter
                text: "UFOs: " + nrUfos
                visible: showDebug
            }

            Label {
                id: bulletCounter
                text: "Bullets: " + nrBullets
                visible: showDebug
            }
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
}
