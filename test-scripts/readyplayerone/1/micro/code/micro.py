"""
Example micro bot script.

"""

import sys
import json
import logging


fh = logging.FileHandler('script.log')
fh.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(message)s')
fh.setFormatter(formatter)

logger = logging.getLogger('script')
logger.setLevel(logging.DEBUG)
logger.addHandler(fh)


def game_loop():
    # Wait for input from micro.
    micro_json = input()
    logger.info('input' + micro_json)
    micro = json.loads(micro_json)

    arenaWidth = micro['arena']['width']
    arenaHeight = micro['arena']['height']
    player = micro['player']
    
    bots = []
    for p in micro['players']:
        if p['name'] == player:
            bots = p['bots']

    commands = {
        'commands': []
    }

    for bot in bots:
        commands['commands'].append({
            'name': bot['name'],
            'move': {
                'direction': 0,
                'speed': 10
            },
            'shoot': {
                'direction': 180,
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
