import QtQuick 2.8
import QtGraphicalEffects 1.0

Item {
    id: spaceshipImage
    width: 64
    height: 64
    visible: true
    property int defaultWidth: 64
    property int defaultHeight: 64
    property real hue
    property int myRotation

    Item {
        rotation: spaceshipImage.myRotation
        transformOrigin: Item.Center
        width: parent.width
        height: parent.height

        AnimatedImage {
            id: image
            anchors.fill: parent
            fillMode: Image.Stretch
            playing: true
            smooth: true
            mipmap: true
            source: "qrc:/Images/spaceship.gif"
        }

        Colorize {
            anchors.fill: image
            source: image
            hue: spaceshipImage.hue
            saturation: 0.5
            lightness: -0.3
        }

        Image {
            anchors.fill: parent
            fillMode: Image.Stretch
            source: "qrc:/Images/spaceship-cockpit.png"
        }
    }
}
