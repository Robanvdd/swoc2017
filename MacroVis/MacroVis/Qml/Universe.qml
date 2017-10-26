import QtQuick 2.7
import QtQuick.Controls 2.2

Flickable {
    id: flick
    contentHeight: universe.height*universe.scale
    contentWidth: universe.width*universe.scale
    property var universe: universe

    property var following: undefined

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
                        model: ufos
                        delegate: UfoImage {
                            id: image
                            hue: model.hue
                            property int ufoId: objectId
                            property bool inFight: inFight
                            x: coord.x - 0.5*width
                            y: coord.y - 0.5*height
                            Behavior on x { NumberAnimation { duration: tickDuration } }
                            Behavior on y { NumberAnimation { duration: tickDuration } }
                            width: 64
                            height: 64

                            Label {
                                anchors.horizontalCenter: parent.horizontalCenter
                                anchors.bottom: parent.top
                                text: image.ufoId
                                font.pointSize: 64
                            }

                            BattleImage {
                                visible: image.inFight
                                anchors.centerIn: parent
                                width: image.width
                                height: image.height
                            }

                            MouseArea {
                                anchors.fill: parent
                                acceptedButtons: Qt.LeftButton
                                onClicked: {
                                    if (flick.following === image)
                                    {
                                        flick.contentX = flick.contentX
                                        flick.contentY = flick.contentY
                                        flick.following = undefined
                                    }
                                    else
                                    {
                                        flick.contentX = Qt.binding(function() { return image.x * universe.scale - (0.5*flick.width) - 32 })
                                        flick.contentY = Qt.binding(function() { return image.y * universe.scale - (0.5*flick.height) - 32 })
                                        flick.following = image
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
