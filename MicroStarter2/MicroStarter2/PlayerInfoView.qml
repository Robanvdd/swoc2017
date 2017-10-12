import QtQuick 2.6
import QtQuick.Controls 2.1
import QtQuick.Layouts 1.3
import QtQuick.Controls.Material 2.1

Column {
    property PlayersModel playersModel
    property PlayersListView playersListView

    RowLayout {
        Label {
            text: "Number of bots: "
        }

        TextField {
            id: nrBotsTextField
            Layout.fillWidth: true
            selectByMouse: true
            placeholderText: playersModel.get(playersListView.listView.currentIndex).nrBots
        }
    }
}
