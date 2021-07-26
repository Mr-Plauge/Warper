execute as @s[scores={warper_dim=0,warpdest_x=29999000..}] at @s run scoreboard players set @s warpdest_x 29999000
execute as @s[scores={warper_dim=1,warpdest_x=3749875..}] at @s run scoreboard players set @s warpdest_x 3749875
execute as @s[scores={warper_dim=2,warpdest_x=29999000..}] at @s run scoreboard players set @s warpdest_x 29999000
execute as @s[scores={warper_dim=0,warpdest_z=29999000..}] at @s run scoreboard players set @s warpdest_z 29999000
execute as @s[scores={warper_dim=1,warpdest_z=3749875..}] at @s run scoreboard players set @s warpdest_z 3749875
execute as @s[scores={warper_dim=2,warpdest_z=29999000..}] at @s run scoreboard players set @s warpdest_z 29999000
execute as @s[scores={warper_dim=0,warpdest_x=..-29999000}] at @s run scoreboard players set @s warpdest_x -29999000
execute as @s[scores={warper_dim=1,warpdest_x=..-3749875}] at @s run scoreboard players set @s warpdest_x -3749875
execute as @s[scores={warper_dim=2,warpdest_x=..-29999000}] at @s run scoreboard players set @s warpdest_x -29999000
execute as @s[scores={warper_dim=0,warpdest_z=..-29999000}] at @s run scoreboard players set @s warpdest_z -29999000
execute as @s[scores={warper_dim=1,warpdest_z=..-3749875}] at @s run scoreboard players set @s warpdest_z -3749875
execute as @s[scores={warper_dim=2,warpdest_z=..-29999000}] at @s run scoreboard players set @s warpdest_z -29999000
execute as @s[scores={warpdest_dim=1,warpdest_y=120..}] at @s run scoreboard players set @s warpdest_y 120
execute as @s at @s run function warper:teleport_warpdest_x
execute as @s at @s run function warper:teleport_warpdest_y
execute as @s at @s run function warper:teleport_warpdest_z
execute as @s[scores={warpdest_dim=0}] at @s in minecraft:overworld run teleport ~ ~ ~
execute as @s[scores={warpdest_dim=1}] at @s in minecraft:the_nether run teleport ~ ~ ~
execute as @s[scores={warpdest_dim=2}] at @s in minecraft:the_end run teleport ~ ~ ~