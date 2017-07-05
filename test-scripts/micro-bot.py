# Example micro bot script.

import json
import sys


def game_loop():
    # Wait for input from micro.
    micro_json = raw_input()
    micro = json.loads(micro_json)

    # Bot logic
    # commands = {
    #     'bots': [
    #         { 'id': 'bot1' ,'move': '1,1'},
    #         { 'id': 'bot2', 'move': '2,2'}
    #     ]
    # }
    commands = micro

    # Send output to micro.
    commands_json = json.dumps(commands).encode('ascii', 'replace')
    print(commands_json)


def main():
    while True:
        game_loop()


if __name__ == '__main__':
    main()
