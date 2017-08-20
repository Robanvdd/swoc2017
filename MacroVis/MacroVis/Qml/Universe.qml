import QtQuick 2.7

Flickable {
    contentHeight: universe.height
    contentWidth: universe.width

    Item {
        id: universe
        width: childrenRect.width + childrenRect.x
        height: childrenRect.height + childrenRect.y
        Connections {
            target: gameBackend
            onSolarSystemsChanged: print("solar systems changed: " + gameBackend.solarSystems.length + " solar systems")
        }

        Item {
            width: childrenRect.width
            height: childrenRect.height
            Component.onCompleted: print("game created")
            Repeater {
                model: gameBackend.solarSystems
                delegate: SolarSystem { }
            }
        }
    }
}
