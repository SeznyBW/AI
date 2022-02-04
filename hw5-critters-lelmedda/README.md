[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6631239&assignment_repo_type=AssignmentRepo)
# hw5-critters
HW5: Critters! 2.0 Party Edition
Sezny Watkins and Loubna El Meddah El Idrissi

1.
swatkins1: This critter performs the best out of the swatkins critters. By having a class variable that keeps track of the location of every critter in the swatkins1 class, each swatkins1 critter finds the closest (manhattan distance) swatkins1 critter besides itself and moves towards it. If interacting with another swatkins1 critter then it alternates between healing and partying (so it keeps track of its last move, and parties if last time it healed). I did this because if it just heals every time the other critter might not need to be healed and no karma is added. But sometimes healing is helpful since it adds more karma and keeps the other swatkins1 critters alive longer. If its health is above 25 it parties with all other critters. If its health is 25 then it fights to try and stay alive. It keeps track of other critters' fighting moves and does the move that will beat the one the other critter uses most often.

swatkins2: This one uses q-learning. I kept a class variable for the q table so that all the critters could use it. I kept track of the state as what enemy it was facing, and how much health it had. The reward was the change in karma value. But obviously the lower the health the lower the expected future utility since it will die sooner, so it should also be protecting its health. This critter didn't do too well, I think because it dies off before it has a chance to learn.

swatkins3: This is just a copycat. Through a class variable, it keeps track of the most recent move used against one of the swatkins3 critters and does that move next time it has an interaction. This does not work well given that most of the critters in its environment all do different things. But if there's a predominant move (i.e. if most of the other critters party) then it should do somewhat decently.

swatkins4: This critter keeps track of each other critters moves through a class variable. If facing another swatkins4 it heals. Otherwise, if the other critter generally parties or heals, this critter parties. Otherwise, it does whatever move beats the move the other critter does most often.


2. I enjoyed coming up with my own ideas, though I had trouble coming up with 4 that were all somewhat unique. I especially enjoyed implementing my first critter. It was difficult having to use python, because I haven't really used it since 150 (like, 5 years ago), and I couldn't just copy and paste my q-learning code since that was in java. But it wasn't too bad switching to python. 

3. I spent about 6 hours on this lab.

4. I affirm that I have adhered to the Honor Code on this assignment - Sezny Watkins