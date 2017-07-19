import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0
import QtQuick.Dialogs 1.2

import SWOC 1.0

ApplicationWindow {
    visible: true
    visibility: ApplicationWindow.Maximized
    width: 1024
    height: 968
    title: qsTr("MacroVis")

    FileIO {
        id: fileIO
        source: ""
        onError: print(msg)
    }

    property Game game: gameLoader.item

    Component {
        id: gameComponent
        Game {
            objectId: -1
        }
    }

    Loader {
        id: gameLoader
        sourceComponent: gameComponent
    }

    function parseGameJSON(gameJSON) {
        if (game.objectId !== gameJSON.id) {
            gameLoader.sourceComponent = gameComponent
            game.objectId = gameJSON.id
            game.name = gameJSON.name
        }

        game.currentTick = gameJSON.tick

        for (var solarSystemI = 0; solarSystemI < gameJSON.solarSystems.length; solarSystemI++) {
            var solarSystemJSON = gameJSON.solarSystems[solarSystemI]
            var solarSystemId = solarSystemJSON.id
            if (!game.solarSystemExists(solarSystemId))
                game.createSolarSystem(solarSystemId)
            var solarSystemCpp = game.getSolarSystem(solarSystemId)
            parseSolarSystem(solarSystemJSON, solarSystemCpp)
        }

        for (var playerI = 0; playerI < gameJSON.players.length; playerI++) {
            var playerJSON = gameJSON.players[playerI]
            parsePlayer(playerJSON)
        }

        for (var fightI = 0; fightI < gameJSON.fights.length; fightI++) {
            var fightJSON = gameJSON.fights[fightI]
            parseFight(fightJSON)
        }
    }

    function parseFight(fightJSON) {
        print(fightJSON.id + " "
            + fightJSON.player1Id + " "
            + fightJSON.player2Id + " "
            + fightJSON.planetId + " "
            + fightJSON.player1UfoIds + " "
            + fightJSON.player2UfoIds)
    }

    function parsePlayer(playerJSON) {
        print(playerJSON.id + " "
            + playerJSON.name + " "
            + playerJSON.credits)

        for (var ufoI = 0; ufoI < playerJSON.ufos.length; ufoI++) {
            var ufoJSON = playerJSON.ufos[ufoI]
            parseUfo(ufoJSON)
        }
    }

    function parseUfo(ufoJSON) {
        print(ufoJSON.id + " "
            + ufoJSON.type + " "
            + ufoJSON.inFight + " "
            + ufoJSON.coord.x + "," + ufoJSON.coord.y)
    }

    function parseSolarSystem(solarSystemJSON, solarSystemCpp) {
        solarSystemCpp.name = solarSystemJSON.name
        solarSystemCpp.coords.x = solarSystemJSON.coords.x
        solarSystemCpp.coords.y = solarSystemJSON.coords.y

        for (var i = 0; i < solarSystemJSON.planets.length; i++) {
            var planetJSON = solarSystemJSON.planets[i]
            var planetId = planetJSON.id
            if (!solarSystemCpp.planetExists(planetId))
                solarSystemCpp.createPlanet(planetId)
            var planetCpp = solarSystemCpp.getPlanet(planetId)
            parsePlanet(planetJSON, planetCpp)
        }
    }

    function parsePlanet(planetJSON, planetCpp) {
        planetCpp.name = planetJSON.name
        planetCpp.orbitDistance = planetJSON.orbitDistance
        planetCpp.orbitRotation = planetJSON.orbitRotation
        planetCpp.ownedBy = planetJSON.ownedBy
    }

    Loader {
        id: fileDialogLoader
        Connections {
            target: fileDialogLoader.item
            onAccepted: {
                fileIO.source = fileDialogLoader.item.fileUrl
                var content = fileIO.read()
                var jsonObject = JSON.parse(content)
                parseGameJSON(jsonObject)
            }
            onRejected: {
                print("Canceled")
            }
        }
    }

    Rectangle {
        anchors.left: parent.left
        anchors.right: parent.right
        anchors.top: loadFileButton.bottom
        anchors.bottom: parent.bottom
        color: "black"
        clip: true
        Flickable {
            anchors.fill: parent
            contentHeight: universe.height
            contentWidth: universe.width

            Item {
                id: universe
                width: childrenRect.width
                height: childrenRect.height
                Connections {
                    target: game
                    onSolarSystemsChanged: print("solar systems changed: " + game.solarSystems.length + " solar systems")
                }

                Item {
                    Component.onCompleted: print("game created")
                    Repeater {
                        model: game.solarSystems
                        Item {
                            x: modelData.coords.x * 4
                            y: modelData.coords.y * 4
                            Component.onCompleted: print("solar system created")

                            Image {
                                x: -0.5*width
                                y: -0.5*height
                                width: 64
                                height: 64
                                source: "images/Sun.ico"
                            }

                            Rectangle {
                                color: "blue"
                                radius: 0.5*width
                                width: 8
                                height: 8
                                x: -0.5*width
                                y: -0.5*height
                            }

                            Repeater {
                                model: modelData.planets
                                Item {
                                    Rectangle {
                                        id: orbitCircle
                                        radius: 0.5*width
                                        color: "transparent"
                                        border.color: "red"
                                        border.width: 2
                                        x: -0.5*width
                                        y: -0.5*height
                                        width: 2*modelData.orbitDistance
                                        height: 2*modelData.orbitDistance
                                    }
                                    Image {
                                        id: planet
                                        x: orbitDistance * Math.cos(orbitRotation) - 0.5*width
                                        y: orbitDistance * Math.sin(orbitRotation) - 0.5*height
                                        property real orbitDistance: modelData.orbitDistance// - 0.5 * width
                                        property real orbitRotation: modelData.orbitRotation
                                        Behavior on orbitRotation { NumberAnimation { duration: 100 } }
                                        width: 32
                                        height: 32
                                        mipmap: true
                                        source: planetImageProvider.getRandomPlanet()
                                        Component.onCompleted: print("planet created")
                                        Timer {
                                            interval: 100
                                            running: true
                                            repeat: true
                                            onTriggered: planet.orbitRotation += 0.01
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Component {
        id: fileDialogComponent
        CustomFileDialog {
        }
    }

    Button {
        id: loadFileButton
        text: "Load file"
        onClicked: {
            fileDialogLoader.sourceComponent = fileDialogComponent
            fileDialogLoader.item.visible = true
        }
    }

    Column {
        anchors.top: loadFileButton.bottom
        Grid {
            flow: Grid.TopToBottom
            rows: 3
            columnSpacing: 8

            Label {
                text: "ID"
                visible: game.objectId != -1
            }
            Label {
                text: "Game name"
                visible: game.objectId != -1
            }
            Label {
                text: "Current tick"
                visible: game.objectId != -1
            }

            Label {
                id: labelId
                text: game.objectId
                visible: game.objectId != -1
            }

            Label {
                id: labelName
                text: game.name
                visible: game.objectId != -1
            }

            Label {
                id: labelTick
                text: game.currentTick
                visible: game.objectId != -1
            }
        }
    }
}
