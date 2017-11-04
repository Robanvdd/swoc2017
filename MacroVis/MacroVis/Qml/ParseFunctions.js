function parseGameJSON(gameJSON) {
    gameBackend.objectId = gameJSON.id
    gameBackend.name = gameJSON.name
    gameBackend.currentTick = gameJSON.tick

    for (var solarSystemI = 0; solarSystemI < gameJSON.solarSystems.length; solarSystemI++) {
        var solarSystemJSON = gameJSON.solarSystems[solarSystemI]
        var solarSystemId = solarSystemJSON.id
        if (!gameBackend.solarSystemExists(solarSystemId))
            gameBackend.createSolarSystem(solarSystemId)
        var solarSystemCpp = gameBackend.getSolarSystem(solarSystemId)
        parseSolarSystem(solarSystemJSON, solarSystemCpp)
    }

    for (var playerI = 0; playerI < gameJSON.players.length; playerI++) {
        var playerJSON = gameJSON.players[playerI]
        var playerId = playerJSON.id
        if (!gameBackend.playerExists(playerId))
            gameBackend.createPlayer(playerId)
        var playerCpp = gameBackend.getPlayer(playerId)
        parsePlayer(playerJSON, playerCpp)
    }
}

function parsePlayer(playerJSON, playerCpp) {
    playerCpp.name = playerJSON.name
    playerCpp.credits = playerJSON.credits
    playerCpp.hue = playerJSON.hue
    playerCpp.color = playerJSON.color
    playerCpp.income = playerJSON.income
    playerCpp.planetsOwned = playerJSON.ownedPlanets

    var ufoIds = []
    for (var i = 0; i < playerJSON.ufos.length; i++) {
        ufoIds.push(playerJSON.ufos[i].id);
    }
    playerCpp.onlyKeepUfos(ufoIds);

    for (var ufoI = 0; ufoI < playerJSON.ufos.length; ufoI++) {
        var ufoJSON = playerJSON.ufos[ufoI]
        var ufoId = ufoJSON.id
        if (!playerCpp.ufoExists(ufoId))
            playerCpp.createUfo(ufoId)
        var ufoCpp = playerCpp.getUfo(ufoId)
        parseUfo(ufoJSON, ufoCpp)
    }
}

function parseUfo(ufoJSON, ufoCpp) {
    ufoCpp.inFight = ufoJSON.inFight
    ufoCpp.coord.x = ufoJSON.coord.x
    ufoCpp.coord.y = ufoJSON.coord.y
}

function parseSolarSystem(solarSystemJSON, solarSystemCpp) {
    solarSystemCpp.name = solarSystemJSON.name
    solarSystemCpp.coords.x = solarSystemJSON.coords.x
    solarSystemCpp.coords.y = solarSystemJSON.coords.y

    for (var i = 0; i < solarSystemJSON.planets.length; i++) {
        var planetJSON = solarSystemJSON.planets[i]
        var planetId = planetJSON.id
        if (!solarSystemCpp.planetExists(planetId))
            solarSystemCpp.createPlanet(planetId)
        var planetCpp = solarSystemCpp.getPlanet(planetId)
        parsePlanet(planetJSON, planetCpp)
    }
}

function parsePlanet(planetJSON, planetCpp) {
    planetCpp.name = planetJSON.name
    planetCpp.orbitDistance = planetJSON.orbitDistance
    planetCpp.orbitRotation = planetJSON.orbitRotation
    planetCpp.ownedBy = planetJSON.ownedBy
    planetCpp.color = planetJSON.color
}
