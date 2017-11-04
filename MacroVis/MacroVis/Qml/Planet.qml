import QtQuick 2.7

Item {
    width: 2*modelData.orbitDistance + planet.width
    height: width
    Rectangle {
        id: orbitCircle
        radius: 0.5*width
        color: "transparent"
        border.color: "red"
        border.width: 4
        x: -0.5*width
        y: -0.5*height
        width: 2*modelData.orbitDistance
        height: 2*modelData.orbitDistance
    }
    Image {
        id: planet
        x: orbitDistance * Math.cos(orbitRotation * (Math.PI / 180.0)) - 0.5*width
        y: orbitDistance * -Math.sin(orbitRotation * (Math.PI / 180.0)) - 0.5*height
        property real orbitDistance: modelData.orbitDistance
        property real orbitRotation: modelData.orbitRotation
        Behavior on orbitRotation { NumberAnimation { duration: tickDuration } }
        width: 64
        height: 64
        mipmap: true
        source: planetImageProvider.getRandomPlanet()

        Rectangle {
            anchors.centerIn: parent
            z: planet.z - 1
            opacity: 0.75
            width: planet.width+20
            height: width
            radius: 0.5*width
            color: modelData.color
        }
    }
}
