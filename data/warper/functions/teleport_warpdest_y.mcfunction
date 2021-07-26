teleport @s ~ 0 ~
execute as @s[scores={warpdest_y=..-1}] at @s run function warper:teleport_warpdest_y_negative
execute as @s[scores={warpdest_y=1..}] at @s run function warper:teleport_warpdest_y_positive