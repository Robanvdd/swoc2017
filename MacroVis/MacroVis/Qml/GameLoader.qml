import QtQuick 2.7
import Qt.labs.platform 1.0

import SWOC 1.0
import "ParseFunctions.js" as ParseFunctions

Item {
    width: 200
    height: 200
    FileIO {
        id: fileIO
        //source: fileDialog.file
        onError: print(msg)
    }

    FileDialog {
        id: fileDialog
        nameFilters: ["JSON (*.json)"]
        fileMode: FileDialog.OpenFile
        onAccepted: {
            readAndParseFile(file)
            tickTimer.start()
        }
        onRejected: {
            print("Canceled")
        }
    }

    Timer {
        id: tickTimer
        interval: 1000
        repeat: true
        onTriggered: loadNextTick()
    }

    function readAndParseFile(fileName) {
        if (fileIO.fileExists(fileName))
        {
            fileIO.source = fileName
            var content = fileIO.read()
            var jsonObject = JSON.parse(content)
            ParseFunctions.parseGameJSON(jsonObject)
        }
    }

    function loadNextTick()
    {
        var currentFileUrl = fileIO.source
        var filename = currentFileUrl.toString().replace(/^.*[\\\/]/, '')
        var path = currentFileUrl.toString().replace(filename, '')
        var numb = filename.match(/\d/g)
        numb = numb.join("")
        var newNumb = parseInt(numb) + 1
        var newFilename = filename.replace(numb, newNumb)
        var newCompleteFilename = path + newFilename

        readAndParseFile(newCompleteFilename)
    }

    function load()
    {
        fileDialog.open()
    }
}
