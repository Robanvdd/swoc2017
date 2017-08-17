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
        parsePlayer(playerJSON)
    }

    for (var fightI = 0; fightI < gameJSON.fights.length; fightI++) {
        var fightJSON = gameJSON.fights[fightI]
        parseFight(fightJSON)
    }
}

function parseFight(fightJSON) {
    print(fightJSON.id + " "
        + fightJSON.player1Id + " "
        + fightJSON.player2Id + " "
        + fightJSON.planetId + " "
        + fightJSON.player1UfoIds + " "
        + fightJSON.player2UfoIds)
}

function parsePlayer(playerJSON) {
    print(playerJSON.id + " "
        + playerJSON.name + " "
        + playerJSON.credits)

    for (var ufoI = 0; ufoI < playerJSON.ufos.length; ufoI++) {
        var ufoJSON = playerJSON.ufos[ufoI]
        parseUfo(ufoJSON)
    }
}

function parseUfo(ufoJSON) {
    print(ufoJSON.id + " "
        + ufoJSON.type + " "
        + ufoJSON.inFight + " "
        + ufoJSON.coord.x + "," + ufoJSON.coord.y)
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
}
