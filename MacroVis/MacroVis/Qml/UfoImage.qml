import QtQuick 2.8
import QtGraphicalEffects 1.0

Item {
    id: control
    property real hue
    AnimatedImage {
        id: image
        anchors.fill: parent
        fillMode: Image.Stretch
        playing: true
        smooth: true
        mipmap: true
        source: "qrc:/images/spaceship.gif"
    }

    Colorize {
        anchors.fill: image
        source: image
        hue: control.hue
        saturation: 0.5
        lightness: -0.3
    }

    Image {
        anchors.fill: parent
        fillMode: Image.Stretch
        source: "qrc:/images/spaceship-cockpit.png"
    }
}
