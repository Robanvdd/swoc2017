import QtQuick 2.7

Item {
    x: modelData.coords.x * 4
    y: modelData.coords.y * 4
    width: childrenRect.width + childrenRect.x
    height: childrenRect.height + childrenRect.y

    Image {
        x: -0.5*width
        y: -0.5*height
        width: 64
        height: 64
        source: "../images/Sun.ico"
    }

    Rectangle {
        visible: false
        color: "blue"
        radius: 0.5*width
        width: 8
        height: 8
        x: -0.5*width
        y: -0.5*height
    }

    Repeater {
        model: modelData.planets
        delegate: Planet { }
    }
}
