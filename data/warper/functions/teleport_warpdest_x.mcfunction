teleport @s 0 ~ ~
execute as @s[scores={warpdest_x=..-1}] at @s run function warper:teleport_warpdest_x_negative
execute as @s[scores={warpdest_x=1..}] at @s run function warper:teleport_warpdest_x_positive