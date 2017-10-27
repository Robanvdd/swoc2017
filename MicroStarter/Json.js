function hueToColorString(hue) {
    var color = Qt.hsla(hue, 1.0, 0.5, 1.0).toString()
    return "#ff" + color.substring(1)
}

function nrUfosToJson(playerModel, index) {
    var ufos = []
    for (var i = 0; i < playerModel.getPlayer(index).nrUfos; i++) {
        ufos.push(i)
    }
    return ufos
}

function playerToJson(playerModel, index) {
    var player = {}
    player.id = index
    player.name = playerModel.getPlayer(index).name
    player.bot = playerModel.getPlayer(index).bot
    player.hue = playerModel.getPlayer(index).hue
    player.color = hueToColorString(player.hue)
    player.ufos = nrUfosToJson(playerModel, index)
    return player
}

function playersToJson(playerModel) {
    var players = []
    for (var i = 0; i < playerModel.count(); i++) {
        players.push(playerToJson(playerModel, i))
    }
    return players
}

function listModelToJson(playerModel, ticksPath, enginePath, visPath) {
    var json = {}
    json.players = playersToJson(playerModel)
    json.gameId = 1337
    json.ticks = ticksPath
    json.enginePath = enginePath
    json.visPath = visPath
    return json
}

function jsonToListModel(json, playerModel) {
    playerModel.clear()
    for (var i = 0; i < json.players.length; i++) {
        var player = json.players[i]
        playerModel.addPlayer(player.name, player.bot, player.ufos.length, player.hue)
    }
}
