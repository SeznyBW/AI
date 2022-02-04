[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6624429&assignment_repo_type=AssignmentRepo)
# hw4-qlearning
HW4: Stochastic Robot Navigation (Q-Learning)
Sezny Watkins and Loubna El Meddah El Idrissi

1. We used a=0, .1, .25, .5, and .75. We found that a=0 (or 1/n(s,a)) was by far the worst, and didn't seem to learn at all. We rather think we must have programmed that option wrong. We had to make a second line graph just with the other alpha values to be able to see them clearly. It looks like, between those for, .1 did the worst. It took the longest to learn and had the most dips/low scores after it started getting mostly decent scores. It looks like .25 learned the fastest, but .5 did the best once it had learned enough. That is, .5 had the fewest dips/low scores once it reached a certain point.

2. We used epsilon=.01, .05, .1, .25, and .5. The two values we chose (.25 and .5) were way too high and resulted in a lot of dips/low scores after it had learned better. There were too many overlapping lines to really make sense of the other three, so again we made another line graph this time excluding the two worst (.25 and .5). From this graph, it looks like epsilon=.01 allowed it to learn slightly faster. But really they all seemed to learn at roughly the same rate. The big difference is that the higher the epsilon value (largely the .1), the more dips/low scores it got after learning how to do better.

3. We would suggest a medium/medium-low learning value, probably somewhere between .25-.5. We would also add that the lower the learning value, the faster it learns but also the worse it does after it learns. We would also advise against using 1/n(s,a) - unless we programmed that wrong. We would also advise using a very low epsilon value. We would explain that it's going to learn at roughly the same rate regardless of the epsilon value, but if it keeps exploring a lot after it has learned then it will result in a lot of lower scores.

4. The most frustrating part for us was the 1/n(s,a) value for alpha. For some reason it doesn't seem to be working right (unless it's supposed to behave very poorly, which is not the impression we got in class), and we couldn't figure out why. Also, the first tests we ran were with that alpha value so we thought the whole program wasn't working and spent a lot of time staring at it trying to figure out why before we realized it worked for other alpha values. But the rest of the lab went very smoothly and was quite enjoyable.

5. We spent about 8 hours on the lab.

6. We adhered to the honor code on this assignment - Sezny Watkins and Loubna El Meddah El Idrissi
