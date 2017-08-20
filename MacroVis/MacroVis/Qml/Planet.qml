import QtQuick 2.7

Item {
    width: childrenRect.width + childrenRect.x + planet.width
    height: childrenRect.height + childrenRect.y + planet.height
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
