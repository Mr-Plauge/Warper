teleport @s ~ ~ 0
execute as @s[scores={warpdest_z=..-1}] at @s run function warper:teleport_warpdest_z_negative
execute as @s[scores={warpdest_z=1..}] at @s run function warper:teleport_warpdest_z_positive