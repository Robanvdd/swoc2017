# Example micro bot script.

import json
import sys
import traceback


def game_loop():
    # Wait for input from micro.
    micro_json = input()
    micro = json.loads(micro_json)
    print(micro, file=sys.stderr)

    print(micro_json, file=sys.stderr)
    print(micro, file=sys.stderr)

    # Bot logic
    commands = {
        "bots": [
            {
                "name": "bot1",
                "move": {
                    "direction": "1,1",
                    "speed": "10"
                },
                "shoot": {
                    "direction": "1,1"
                }
            },
            {
                "name": "bot2",
                "move": {
                    "direction": "1,1",
                    "speed": "10"
                },
                "shoot": {
                    "direction": "1,1"
                }
            }
        ]
    }

    # Send output to micro.
    commands_json = json.dumps(commands)
    print(commands_json)


def main():
    while True:
        try:
            game_loop()
        except BaseException:
            traceback.print_exc()


if __name__ == '__main__':
    main()
