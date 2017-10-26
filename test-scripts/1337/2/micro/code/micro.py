"""
Example micro bot script.
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

def distance(x1, y1, x2, y2):
    dx = x2-x1
    dy = y2-y1
    return math.sqrt(dx*dx + dy*dy)

def get_my_ufos(micro):
    ufos = []
    for p in micro['players']:
        if p['id'] == micro['playerId']:
            ufos = p['ufos']
    return ufos

def get_enemy_ufos(micro):
    enemyUfos = []
    for p in micro['players']:
        if p['id'] != micro['playerId']:
            enemyUfos += p['ufos']
    return enemyUfos

def get_corner(arenaWidth, arenaHeight, index):
    if index == 0:
        return [20, 20]
    if index == 1:
        return [arenaWidth - 20, 20]
    if index == 2:
        return [arenaWidth - 20, arenaHeight - 20]
    if index == 3:
        return [20, arenaHeight - 20]

def get_target_enemy(otherUfos):
    targetX = 0
    targetY = 0
    if otherUfos != []:
        for bot in otherUfos:
            if bot['hitpoints'] > 0:
                targetBot = bot
                targetX = targetBot['position']['x']
                targetY = targetBot['position']['y']
                break
    return [targetX, targetY]

def append_move_to_command(commands, id, x, y, speed):
    commands['commands'].append({
        'id': id,
        'moveTo': {
            'x': x,
            'y': y,
            'speed': speed
        }
    })

def append_shoot_at_command(commands, id, x, y):
    commands['commands'].append({
        'id': id,
        'shootAt': {
            'x': x,
            'y': y
        }
    })

time = 0
targetLocation = [20, 20]
targetCornerIndex = 0

def game_loop():
    # Wait for input from micro.
    micro_json = input()
    logger.info('input' + micro_json)
    micro = json.loads(micro_json)
    
    arenaWidth = micro['arena']['width']
    arenaHeight = micro['arena']['height']
    player = micro['playerId']
    
    global time
    time = time + 0.1
    
    ufos = get_my_ufos(micro)
    otherUfos = get_enemy_ufos(micro)
    targetEnemy = get_target_enemy(otherUfos)
    
    commands = {
        'commands': []
    }
    
    global targetLocation
    if ufos != []:
        ufox = ufos[0]['position']['x']
        ufoy = ufos[0]['position']['y']
        
        dis = distance(ufox, ufoy, targetLocation[0], targetLocation[1])
        if (dis < 10):
            logger.info('distance: ' + str(dis))
            global targetCornerIndex
            targetCornerIndex += 1
            targetLocation = get_corner(arenaWidth, arenaHeight, targetCornerIndex % 4)
        
        append_shoot_at_command(commands, ufos[0]['id'], targetEnemy[0], targetEnemy[1])
        append_move_to_command(commands, ufos[0]['id'], targetLocation[0], targetLocation[1], 10)
    
    if len(ufos) > 1:
        for i in range(1, len(ufos)):
            ufo0x = ufos[0]['position']['x']
            ufo0y = ufos[0]['position']['y']
            
            append_move_to_command(commands, ufos[i]['id'], ufo0x, ufo0y, 10)
            append_shoot_at_command(commands, ufos[i]['id'], targetEnemy[0], targetEnemy[1])
    
    # Send output to micro.
    commands_json = json.dumps(commands)
    logger.info('output ' + commands_json)
    print(commands_json)

def main():
    while True:
        try:
            game_loop()
        except:
            logger.exception()

if __name__ == '__main__':
    main()
