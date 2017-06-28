import QtQuick 2.7
import QtQuick.Controls 2.0
import QtQuick.Layouts 1.0
import SWOC 1.0

ApplicationWindow {
    visible: true
    width: 1024
    height: 768
    title: qsTr("Hello World")

    FileIO {
        id: fileIO
        source: ""
        onError: console.log(msg)
    }

    Timer {
        interval: 1000/30
        running: true
        repeat: true
        onTriggered: AppContext.processFrame()
    }

    MouseArea {
        acceptedButtons: Qt.LeftButton | Qt.RightButton
        anchors.fill: parent
        onClicked: {
            if (pressedButtons & Qt.LeftButton)
                AppContext.addSpaceship(mouseX, mouseY)
            else if (pressedButtons & Qt.RightButton)
                AppContext.addBullet(mouseX, mouseY)
        }

    }

    Loader {
        id: fileDialogLoader
        property CustomFileDialog fileDialog: fileDialogLoader.item
        Connections {
            target: fileDialogLoader.item
            onAccepted: {
                fileIO.source = fileDialogLoader.fileDialog.fileUrl
                var content = fileIO.read()
                var jsonObject = JSON.parse(content)

                console.log(jsonObject.x)
                console.log(jsonObject.y)
            }
            onRejected: {
                console.log("Canceled")
            }
        }
    }

    Component {
        id: fileDialogComponent
        CustomFileDialog {
        }
    }

    Button {
        id: pressMeButton
        text: "Press me"
        onClicked: {
            fileDialogLoader.sourceComponent = fileDialogComponent
            fileDialogLoader.fileDialog.visible = true
        }
    }

    Label {
        id: pressMeLabel
        text: "Default text"
        anchors.left: pressMeButton.right
        anchors.leftMargin: 16
    }

    Repeater {
        model: AppContext.spaceships
        delegate: Spaceship {
            x: modelData.x
            y: modelData.y
            source: "qrc:///Images/ufo.png"
            visible: !modelData.dead
        }
    }

    Repeater {
        model: AppContext.bullets
        delegate: Bullet {
            x: modelData.x
            y: modelData.y
            source: "qrc:///Images/bullet.png"
            visible: true
        }
    }
}
