function hueToColorString(hue) {
    var color = Qt.hsla(hue, 1.0, 0.5, 1.0).toString()
    return "#ff" + color.substring(1)
}

function nrUfosToJson(listModel, index) {
    var ufos = []
    for (var i = 0; i < listModel.get(index).nrUfos; i++) {
        ufos.push(i)
    }
    return ufos
}

function playerToJson(listModel, index) {
    var player = {}
    player.id = index
    player.name = listModel.get(index).name
    player.bot = listModel.get(index).bot
    player.hue = listModel.get(index).hue
    player.color = hueToColorString(player.hue)
    player.ufos = nrUfosToJson(listModel, index)
    return player
}

function playersToJson(listModel) {
    var players = []
    for (var i = 0; i < listModel.count; i++) {
        players.push(playerToJson(listModel, i))
    }
    return players
}

function listModelToJson(listModel, ticksPath) {
    var json = {}
    json.players = playersToJson(listModel)
    json.gameId = 1337
    json.ticks = ticksPath
    return json
}
