"""
Example micro bot script.

Input from micro game:
"arena" {
    //TODO
},
players: [

]


Output to micro game:

"bots": [
    {
        "name": "bot_name"
        "move": {
            "direction": "0-360",
            "speed": "0-10"
        },
        "shoot": {
            "direction": "0-360"
        }
    }
]
"""

import json
import sys
import traceback


angle = 0

def game_loop():
    # Wait for input from micro.
    micro_json = input()
    micro = json.loads(micro_json)
    print(micro, file=sys.stderr)

    print(micro_json, file=sys.stderr)
    print(micro, file=sys.stderr)

    # Bot logic
    global angle
    angle = (angle + 1) % 360

    commands = {
        "bots": [
            {
                "name": "bot0",
            },
            {
                "name": "bot1",
                "move": {
                    "direction": angle,
                    "speed": "5"
                }
            },
            {
                "name": "bot2",
                "shoot": {
                    "direction": angle
                }
            },
            {
                "name": "bot1",
                "move": {
                    "direction": angle,
                    "speed": "5"
                },
                "shoot": {
                    "direction": angle
                }
            },
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
