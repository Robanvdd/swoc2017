import QtQuick 2.7

Flickable {
    contentHeight: universe.height
    contentWidth: universe.width

    Item {
        id: universe
        width: childrenRect.width + childrenRect.x
        height: childrenRect.height + childrenRect.y

        Item {
            width: childrenRect.width + childrenRect.x
            height: childrenRect.height + childrenRect.y
            Repeater {
                model: gameBackend.solarSystems
                delegate: SolarSystem { }
            }
        }

        Item {
            width: childrenRect.width + childrenRect.x
            height: childrenRect.height + childrenRect.y
            Repeater {
                model: gameBackend.players
                delegate: Item {
                    id: player
                    width: childrenRect.width + childrenRect.x
                    height: childrenRect.height + childrenRect.y
                    Repeater {
                        model: modelData.ufos
                        delegate: Image {
                            x: modelData.coord.x
                            y: modelData.coord.y
                            width: 20
                            height: 20
                            smooth: true
                            mipmap: true
                            source: "qrc:/images/spaceship.png"
                        }
                    }
                }
            }
        }
    }
}
