# PlayerPerks
A Minecraft Paper plugin that give players different perks

#### Perks/Roles
###### Farmer
- 50% chance of double drop on crops(wheat, beetroot, carrot, potato, nether wart).
- 25% chance that crops(mentioned above) placed get a growth boost and reach age 5 quicker.

###### Miner
- when one coal is consumed (right click) player gets double drop on everything broken with a pickaxe in the next 5 seconds
- permanent Haste II

###### Hunter
- ability to craft emeralds with lava bucket and lapis under it
- permanent double drops on mobs(enderman, zombie, skeleton, spider also cow and sheep)

###### Engineer
- ability to craft redstone from 9 granite blocks
- ability to craft slimeball with gold ingot, clay ball, emerald in any configuration
---
#### Setup
**/perks add <playername> <farmer|miner|hunter|engineer>**
this command adds a perk to a player

Perks can also be configured in the config.yml inside the plugin folder in the format:
```yml
<perk>:
- playerName

hunter:
- proPlayer123
- scoobySnack4life
...
```
