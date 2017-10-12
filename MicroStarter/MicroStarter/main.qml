import QtQuick 2.6
import QtQuick.Window 2.2

Window {
    visible: true
    width: 640
    height: 480
    title: qsTr("MicroStarter")

    Column {
        width: 200

        Text {
            text: "Players:"
        }

        ListView {
            id: playerList
            width: 200
            height: 200

            model: PlayersModel {}
            delegate: Text {
                text: name
            }
        }
    }
}
