import QtQuick 2.7

Item {
    width: 2*modelData.orbitDistance + planet.width
    height: width
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
        x: orbitDistance * Math.cos(orbitRotation * (Math.PI / 180.0)) - 0.5*width
        y: orbitDistance * -Math.sin(orbitRotation * (Math.PI / 180.0)) - 0.5*height
        property real orbitDistance: modelData.orbitDistance// - 0.5 * width
        property real orbitRotation: modelData.orbitRotation
        Behavior on orbitRotation { NumberAnimation { duration: tickDuration } }
        width: 48
        height: 48
        mipmap: true
        source: planetImageProvider.getRandomPlanet()
    }
}
