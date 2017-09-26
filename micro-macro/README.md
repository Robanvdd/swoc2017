# Micro-Macro

> The micro-macro-onomicon


## Micro


### Game Mechanics

Players control bots using 2 commands: `move` and `shoot`.


#### Moving

The `move` command moves the bot in the specified `direction` at the specified `speed`.
The movement is based on the [polar coordinate system], `direction` being the *angle* and `speed` being the *radius*.

**Parameters:**

- `direction`: Value between **0** and **360**.
- `speed`: Value between **0** and **10**.


#### Shooting

The `shoot` command shoots a lazer from the origin of the bot in the specified `direction`.
Unlike moving, shooting has a fixed projectile speed.
The movement is based on the [polar coordinate system], `direction` being the *angle* and the fixed projectile speed being the *radius*.
Shooting also has a *cooldown* of **0.5** seconds meaning that you can only shoot twice per second.
Each shot does **2** points of damage. There is no friendly fire.

**Parameters:**

- `direction`: Value between: **0** and **360**.


#### Hitpoints

A bot starts with **20** `hitpoints` and is destroyed when it's `hitpoints` reaches `0`.


#### Position

All bots have a `position` "**x,y**". This represents the bot's current position on the arena.
The arena has a specified `width` and `height`. The top left corner of the arena represents "**0,0**" and the bottom right corner of the arena represents "**width,height**".
Your bot can only move within the bounds of the arena. There is no collision between bots.


#### The Laz0r Fence

The arena is surrounded by a lazor fence. If any bot touches this fence, they will immediatly be destroyed (**0** `hitpoints`).
In order to keep the games short, the arena will start shrinking after a fixed amount of time and will keep shrinking until the battle is over.


#### Winning Condition

Last man standing: if all your bots are destroyed you lose.


### Scripting


#### Game Loop

The game loop of any basic micro bot is as follows:

1. Read `JSON` formatted game state (input) from micro using `stdin`
2. Bot logic (where the magic happens)
3. Write `JSON` formatted commands (output) to micro using `stdout`

*Note: The input and output are in [JSON] format*

Python example:

```python

while True:
    input = input()
    # Bot logic
    print(output)

```


##### Reading input from Micro

The input received from micro has the following format:

```json
{
    "arena": {
        "height": "integer",
        "width": "integer"
    },
    "player": "string",
    "players": [
        {
            "name": "string",
            "bots": [
                {
                    "name": "string",
                    "hitpoints": "float",
                    "position": "float,float"
                }
            ]
        }
    ],
    "projectiles": [
        {
            "position": "float,float",
            "direction": "float",
        },
    ]
}
```

Input example:

```json
{
    "arena": {
        "height": "1000",
        "width": "1000"
    },
    "player": "player0",
    "players": [
        {
            "name": "player0",
            "bots": [
                {
                    "name": "bot0",
                    "hitpoints": "20",
                    "position": "25,25"
                },
                {
                    "name": "bot1",
                    "hitpoints": "10",
                    "position": "25,50"
                }
            ]
        },
        {
            "name": "player1",
            "bots": [
                {
                    "name": "bot0",
                    "hitpoints": "15",
                    "position": "75,75"
                },
                {
                    "name": "bot1",
                    "hitpoints": "15",
                    "position": "50,75"
                }
            ]
        }
    ],
    "projectiles": [
        {
            "position": "23,34",
            "direction": "32",
        },
        {
            "position":"15,17",
            "direction": "32"
        }
    ]
}
```


##### Writing output to Micro

The output sent to micro has the following format:

```json
{
    "commands": [
        {
            "name": "string",
            "move": {
                "direction": "float",
                "speed": "float"
            },
            "shoot": {
                "direction": "float"
            }
        },
    ]
}
```

Output example:

```json
{
    "commands": [
        {
            "name": "bot0",
        },
        {
            "name": "bot1",
            "move": {
                "direction": "0",
                "speed": "10"
            }
        },
        {
            "name": "bot2",
            "shoot": {
                "direction": "90"
            }
        },
        {
            "name": "bot3",
            "move": {
                "direction": "180",
                "speed": "10"
            },
            "shoot": {
                "direction": "270"
            }
        },
    ]
}
```


[polar coordinate system]:https://en.wikipedia.org/wiki/Polar_coordinate_system
[JSON]: https://en.wikipedia.org/wiki/JSON
