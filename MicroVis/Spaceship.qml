import QtQuick 2.0
import QtGraphicalEffects 1.0

Image {
    id: spaceshipImage
    width: 64
    height: 64
    source: "qrc:///Images/ufo.png"
    visible: true

    Behavior on x {
        NumberAnimation {
            id: spaceXAnimation
            duration: 1000/30
        }
    }
    Behavior on y {
        NumberAnimation {
            duration: 1000/30
        }
    }
}
