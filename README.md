# FireworkJoin
Shoots fireworks above joining players
## Config
```yaml
# The Configuration File for the FireworkJoin Plugin
# Firework types: BALL, BALL_LARGE, BURST, CREEPER, STAR
delay: 1
fireworks:
  - type: STAR
    power: 0  # -1 will create an instantly exploding firework although this does not work in some versions of minecraft
    colors:
      - red: 255
        green: 0
        blue: 0
  - type: BALL
    power: 0
    colors:
      - red: 0
        green: 255
        blue: 0
```
## Permissions
- FireworkJoin:
  - Description: Shoots fireworks on join
  - Default: true