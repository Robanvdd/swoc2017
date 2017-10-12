import QtQuick 2.6
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.3
import QtQuick.Controls.Material 2.1

ApplicationWindow {
    visible: true
    width: 640
    height: 480
    title: qsTr("MicroStarter")

    PlayersModel {
        id: playersModel
    }

    RowLayout {

        PlayersListView {
            id: playersListView
            playersModel: playersModel
        }

        PlayerInfoView {
            playersModel: playersModel
            playersListView: playersListView
        }
    }
}
