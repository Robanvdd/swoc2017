import QtQuick 2.7
import QtGraphicalEffects 1.0
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.1

import SWOC 1.0

Column {
    spacing: 16
    Label {
        text: "Game Info"
        font.pointSize: 18
    }
    Grid {
        id: gameGrid
        flow: Grid.TopToBottom
        rows: 3
        columnSpacing: 8

        Label {
            text: "ID"
            visible: gameBackend.objectId != -1
        }
        Label {
            text: "Game name"
            visible: gameBackend.objectId != -1
        }
        Label {
            text: "Current tick"
            visible: gameBackend.objectId != -1
        }

        Label {
            id: labelId
            text: gameBackend.objectId
            visible: gameBackend.objectId != -1
        }

        Label {
            id: labelName
            text: gameBackend.name
            visible: gameBackend.objectId != -1
        }

        Label {
            id: labelTick
            text: gameBackend.currentTick
            visible: gameBackend.objectId != -1
        }
    }
    Label {
        text: "Credits"
        font.pointSize: 18
    }

    GridLayout {
        id: playerCreditGrid
        rows: gameBackend.players.rowCount()
        columns: 3
        rowSpacing: gameGrid.rowSpacing
        flow: GridLayout.TopToBottom
        Repeater {
            model: gameBackend.players
            delegate: Label {
                parent: playerCreditGrid
                text: name
                color: model.color
            }
        }
        Repeater {
            model: gameBackend.players
            delegate: Label {
                parent: playerCreditGrid
                id: creditsLabel
                text: credits
            }
        }
        Repeater {
            model: gameBackend.players
            delegate: Label {
                parent: playerCreditGrid
                text: income
            }
        }
    }
    Label {
        text: "Ufos"
        font.pointSize: 18
    }
    GridLayout {
        id: playerUfosGrid
        rows: gameBackend.players.rowCount()
        columns: 2
        rowSpacing: gameGrid.rowSpacing
        flow: GridLayout.TopToBottom
        Repeater {
            model: gameBackend.players
            delegate: Label {
                parent: playerUfosGrid
                text: name
                color: model.color
            }
        }
        Repeater {
            model: gameBackend.players
            delegate: Label {
                parent: playerUfosGrid
                text: ufos.length
            }
        }
    }
    Label {
        text: "Number of Planets"
        font.pointSize: 18
    }
    GridLayout {
        id: playerPlanetsGrid
        rows: gameBackend.players.rowCount()
        columns: 2
        rowSpacing: gameGrid.rowSpacing
        flow: GridLayout.TopToBottom
        Repeater {
            model: gameBackend.players
            delegate: Label {
                parent: playerPlanetsGrid
                text: name
                color: model.color
            }
        }
        Repeater {
            model: gameBackend.players
            delegate: Label {
                parent: playerPlanetsGrid
                text: ownedPlanets
            }
        }
    }
}
