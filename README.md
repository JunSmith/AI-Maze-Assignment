# AI Maze Assignment
***By Jun Smith***
[Link](http://github.com/JunSmith/AI-Maze-Assignment)

## Instructions
In your favourite terminal, locate to the directory containing MazeGame.jar and type "java -jar "MazeGame.jar"". Requires java 8.

Reach the end tile without running out of stamina. Careful, each move and interaction with an enemy costs stamina, so plan carefully!

| Input | Action |
| --- | --- |
| Arrowkeys | Moves the player one tile |
| Z | View the map |
| X | Use item |

## Features
- Uses search algorithms
-- Random walk in enemy movement
-- Heuristic search (A*) in bombs. Both bomb types use the search to immediately kill an enemy. The blue "H" bomb searches further.
- Fuzzy logic when the player fights an enemy. The player suffers stamina usage based on current stamina and the condition of their weapon. The better the weapon condition and the lower the player stamina, the less stamina it takes to kill an enemy.
- Maze generation inspired by Prim's algorithm to generate the maze (in particular the usage of frontiers to determine valid space tiles).
