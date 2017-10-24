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

def get_my_bots(micro):
    bots = []
    for p in micro['players']:
        if p['id'] == micro['playerId']:
            bots = p['bots']
    return bots

def get_enemy_bots(micro):
    enemyBots = []
    for p in micro['players']:
        if p['id'] != micro['playerId']:
            enemyBots += p['bots']
    return enemyBots

def get_corner(arenaWidth, arenaHeight, index):
    if index == 0:
        return [20, 20]
    if index == 1:
        return [arenaWidth - 20, 20]
    if index == 2:
        return [arenaWidth - 20, arenaHeight - 20]
    if index == 3:
        return [20, arenaHeight - 20]

def get_target_enemy(otherBots):
    targetX = 0
    targetY = 0
    if otherBots != []:
        for bot in otherBots:
            if bot['hitpoints'] > 0:
                targetBot = bot
                targetX = targetBot['position']['x']
                targetY = targetBot['position']['y']
                break
    return [targetX, targetY]

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
    
    bots = get_my_bots(micro)
    
    otherBots = get_enemy_bots(micro)
    
    targetEnemy = get_target_enemy(otherBots)
    
    commands = {
        'commands': []
    }
    
    global targetLocation
    if bots != []:
        botx = bots[0]['position']['x']
        boty = bots[0]['position']['y']
        
        logger.info('botx: ' + str(botx) + ', boty: ' + str(boty))
        logger.info('targetLocation[0]: ' + str(targetLocation[0]) + ', targetLocation[1]: ' + str(targetLocation[1]))
        
        dis = distance(botx, boty, targetLocation[0], targetLocation[1])
        if (dis < 10):
            logger.info('distance: ' + str(dis))
            global targetCornerIndex
            targetCornerIndex += 1
            logger.info('targetCornerIndex: ' + str(targetCornerIndex))
            targetLocation = get_corner(arenaWidth, arenaHeight, targetCornerIndex % 4)
        
        commands['commands'].append({
            'id': bots[0]['id'],
            'moveTo': {
                'x': targetLocation[0],
                'y': targetLocation[1],
                'speed': 10
            },
            'shootAt': {
                'x': targetEnemy[0],
                'y': targetEnemy[1]
            }
        })
    
    for i in range(1, len(bots)):
        bot0x = bots[0]['position']['x']
        bot0y = bots[0]['position']['y']
        
        commands['commands'].append({
            'id': bots[i]['id'],
            'moveTo': {
                'x': bot0x,
                'y': bot0y,
                'speed': 10
            },
            'shootAt': {
                'x': targetEnemy[0],
                'y': targetEnemy[1]
            }
        })
    
    
    # Send output to micro.
    commands_json = json.dumps(commands)
    logger.info('output' + commands_json)
    print(commands_json)

def main():
    while True:
        try:
            game_loop()
        except:
            logger.exception()

if __name__ == '__main__':
    main()
