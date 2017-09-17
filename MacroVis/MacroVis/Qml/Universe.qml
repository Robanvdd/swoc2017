import QtQuick 2.7
import QtQuick.Controls 2.2

Flickable {
    id: flick
    contentHeight: universe.height*universe.scale
    contentWidth: universe.width*universe.scale
    property var universe: universe

    ScrollBar.vertical: ScrollBar { }
    ScrollBar.horizontal: ScrollBar { }

    Item {
        id: universe

        transformOrigin: Item.TopLeft
        width: childrenRect.width
        height: childrenRect.height
        scale: 0.1

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
