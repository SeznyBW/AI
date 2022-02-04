[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6100961&assignment_repo_type=AssignmentRepo)
# hw1-search
HW1: Search for a Restaurant

Credit to OpenStreetMaps.com for the data used to generate the maps used in this assignment.

# Your Answers Go Here #
1. 
Map : mapO.dat                                                                                                                     
BFS Path Length: 63.0
BFS Path Cost: 0.08280000000000001
BFS Goal Reached: (41.290455, -82.218492)
Nodes Expanded with BFS: 1274
Time Spent with BFS (in milliseconds): 40

A* Path Length: 64.0
A* Path Cost: 0.08240000000000001
A* Goal Reached: (41.290455, -82.218492)
Nodes Expanded with A*: 285
Time Spent with A* (in milliseconds): 8

UCS Path Length: 64.0
UCS Path Cost: 0.08240000000000001
UCS Goal Reached: (41.290455, -82.218492)
Nodes Expanded with UCS: 982
Time Spent with UCS (in milliseconds): 16

Map : mapC.dat

BFS Path Length: 126.0
BFS Path Cost: 0.11800000000000004
BFS Goal Reached: (41.484322, -81.704265)
Nodes Expanded with BFS: 7838
Time Spent with BFS (in milliseconds): 92

A* Path Length: 131.0
A* Path Cost: 0.10949999999999999
A* Goal Reached: (41.483662, -81.730277)
Nodes Expanded with A*: 4458
Time Spent with A* (in milliseconds): 56

UCS Path Length: 131.0
UCS Path Cost: 0.10949999999999999
UCS Goal Reached: (41.483662, -81.730277)
Nodes Expanded with UCS: 10398
Time Spent with UCS (in milliseconds): 89

Map : mapL.dat

BFS Path Length: 90.0
BFS Path Cost: 0.10920000000000003
BFS Goal Reached: (40.756217, -96.652942)
Nodes Expanded with BFS: 39719
Time Spent with BFS (in milliseconds): 400

A* Path Length: 95.0
A* Path Cost: 0.10420000000000006
A* Goal Reached: (40.756217, -96.652942)
Nodes Expanded with A*: 10407
Time Spent with A* (in milliseconds): 127

UCS Path Length: 95.0
UCS Path Cost: 0.10420000000000006
UCS Goal Reached: (40.756217, -96.652942)
Nodes Expanded with UCS: 49636
Time Spent with UCS (in milliseconds): 595

Map : mapT.dat

BFS Path Length: 3.0
BFS Path Cost: 7.0
BFS Goal Reached: (0.000000, 0.000000)
Nodes Expanded with BFS: 5
Time Spent with BFS (in milliseconds): 0

A* Path Length: 4.0
A* Path Cost: 6.0
A* Goal Reached: (8.000000, 5.000000)
Nodes Expanded with A*: 4
Time Spent with A* (in milliseconds): 0

UCS Path Length: 4.0
UCS Path Cost: 6.0
UCS Goal Reached: (8.000000, 5.000000)
Nodes Expanded with UCS: 9
Time Spent with UCS (in milliseconds): 0

2. For the heuristic for A* I used the manhattan distance between the node and the (manhattan) nearest goal. A* and UCS always found the same goal with the same cost, but BFS sometimes found a different goal or a shorter path to the same goal but with a higher cost. A* was always fastest and expanded the fewest number of nodes. BFS and UCS varied on which one expanded the most nodes and which one took the longest amount of time (interestingly, the one which expanded the fewest nodes was not always the fastest in terms of time).

3. It's been a little while since I coded in Java so I did a lot of looking up the data types (especially PriorityQueues). I also was stumped for a long time and couldn't figure out what I was doing wrong - turns out it's just that I was trying to take a short cut on the comparators that required that I cast the costs/fn values as ints, and they were too small to do that accurately. It took me a long time to figure out what I was doing wrong. But actually following along with the algorithm itself and coding that was very simple.

4. Ugh, I forgot to keep track. Next time I'll do better on that. I'd say I spent maybe 5 hours of actually working on my code, but I spent more time than that just thinking about it, trying to work out what I might be doing wrong.

5. I affirm that I have adhered to the Honor Code on this assignment. -Sezny Watkins