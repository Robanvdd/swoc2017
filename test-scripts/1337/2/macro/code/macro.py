"""
Example macro bot script.
"""

import sys
import json
import logging
import math

fh = logging.FileHandler('script.log')
fh.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(message)s')
fh.setFormatter(formatter)

logger = logging.getLogger('script')
logger.setLevel(logging.DEBUG)
logger.addHandler(fh)

def get_player(macro):
    for player in macro['players']:
        if player['name'] == "Arjan":
            return player

def get_ufos(player):
    return player['ufos']

def get_leader_ufo(ufos):
    return ufos[0]

def get_position(ufo):
    return ufo['coord']

def get_first_enemy_ufo(macro):
    for player in macro['players']:
        if player['name'] != "Arjan":
            if len(player['ufos']) > 0:
                return player['ufos'][0]

def command_move_to_coord(ufoIds, coord):
    x = math.floor(coord['x'])
    y = math.floor(coord['y'])
    command = {"Command" : "moveToCoord", "Ufos" : ufoIds, "Coord" : {"X" : x, "Y" : y}}
    logger.info('output ' + str(json.dumps(command)))
    print(json.dumps(command))

def command_conquer(planetId):
    command = {"Command" : "conquer", "PlanetId" : planetId}
    logger.info('output ' + str(json.dumps(command)))
    print(json.dumps(command))

def command_buy(credits, planetId):
    ufoCost = 100000
    command = {"Command" : "buy", "Amount" : math.floor(credits / ufoCost), "PlanetId" : planetId}
    logger.info('output ' + str(json.dumps(command)))
    print(json.dumps(command))

def distance(coord1, coord2):
    dx = coord1['x'] - coord2['x']
    dy = coord1['y'] - coord2['y']
    return math.sqrt(dx*dx + dy*dy)

def get_planet_coord(center, planet):
    radius = planet['orbitDistance']
    angle = planet['orbitRotation']
    x = center['x'] + radius * math.cos(angle)
    y = center['y'] + radius * math.sin(angle)
    return {"x" : x, "y" : y}

def get_nearest_planet_id(macro, coord):
    smallestDistance = 10000000000000000
    nearestPlanetId = {}
    for solarSystem in macro['solarSystems']:
        center = solarSystem['coords']
        for planet in solarSystem['planets']:
            dist = distance(get_planet_coord(center, planet), coord)
            if dist < smallestDistance:
                smallestDistance = dist
                nearestPlanetId = planet['id']
    return nearestPlanetId

def game_loop():
    # Wait for input from macro.
    macro_json = input()
    logger.info('input' + macro_json)
    macro = json.loads(macro_json)
    
    player = get_player(macro)
    ufos = get_ufos(player)
    leaderUfo = get_leader_ufo(ufos)
    leaderPosition = get_position(leaderUfo)
    targetEnemyPosition = get_position(get_first_enemy_ufo(macro))
    
    # Buy ufos, close to leader
    credits = player['credits']
    if credits >= 100000:
        nearestPlanetId = get_nearest_planet_id(macro, leaderPosition)
        command_buy(credits, nearestPlanetId)
    
    # Move all my ufos towards leader ufo
    otherUfoIds = []
    for ufo in ufos:
        if ufo['id'] != leaderUfo['id'] and not ufo['inFight']:
            otherUfoIds.append(ufo['id'])
    command_move_to_coord(otherUfoIds, leaderPosition)
    
    # Move leader ufo to an enemy
    if not leaderUfo['inFight']:
        command_move_to_coord([leaderUfo['id']], targetEnemyPosition)
    
    # Attack when close
    if distance(leaderPosition, targetEnemyPosition) < 256:
        nearestPlanetId = get_nearest_planet_id(macro, targetEnemyPosition)
        command_conquer(nearestPlanetId)

def main():
    while True:
        try:
            game_loop()
        except:
            logger.exception()

if __name__ == '__main__':
    main()
