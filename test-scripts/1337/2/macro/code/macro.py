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
    command = {"Command" : "moveToCoord", "Ufos" : otherUfoIds, "Coord" : leaderPosition}
    logger.info('output' + command)
    json.dumps(command)

def command_conquer(planetId):
    command = {"Command" : "conquer", "PlanetId" : planetId}
    logger.info('output' + command)
    json.dumps(command)

def distance(coord1, coord2):
    dx = coord1['X'] - coord2['X']
    dy = coord1['Y'] - coord2['Y']
    return math.sqrt(dx*dx + dy*dy)

def get_planet_coord(center, planet):
    radius = planet['orbitDistance']
    angle = planet['orbitRotation']
    x = center['x'] + radius * math.cos(angle)
    y = center['y'] + radius * math.sin(angle)
    return {"x" : x, "y" : y}

def get_nearest_planet_id(macro, coord):
    smallestDistance = sys.maxint
    nearestPlanetId = {}
    for solarSystem in macro['solarSystems']:
        center = solarSystem['coords']
        for planet in solarSystem:
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
    
    # Move all my ufos towards leader ufo
    otherUfoIds = []
    for ufo in ufos:
        if ufo['id'] != leaderUfo['id']:
            otherUfoIds.append(ufo['id'])
    command_move_to_coord(otherUfoIds, leaderPosition)
    
    # Move leader ufo to an enemy
    command_move_to_coord([leaderUfo['id']], targetEnemyPosition)
    
    # Attack when close
    if distance(leaderPosition, targetEnemyPosition) < 100:
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
