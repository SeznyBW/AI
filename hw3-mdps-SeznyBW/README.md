[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=6410869&assignment_repo_type=AssignmentRepo)
# hw3-mdps
HW3: Robotic Wildfire Suppression (Markov Decision Processes)
Sezny Watkins

1. Based off the experiment with gamma values ranging from .1-.9, the higher the gamma value the better the average utility of the agent. Specifically, it had negative utilities for .1 and .3. Then .5 and .7 were in the 20's, and at .9 it jumped to over 40. I think this is because it is weighting more heavily potential future utility, rather than focusing only on the reward it gets from one specific action. This implies that it is important to focus more on long term utility. 

2. I implemented the MDP by creating two classes - one for each state, and one for each transition. The state kept track of it's ID and label and, more importantly, a list of transitions seperated by which action it applies to, a list of Q values for each action, and the V value. Then the transition kept track of it's start state, end state, action and, more importantly, probability and reward. Then the MDP problem was summed up as just a list of states. I did consider doing it the way you suggested in class, with a list to keep track of states and V values, and arrays to keep track of the transition function and Q values. But I'd already started doing it with classes and it honestly seemed simpler to me to keep track of one list rather than four, and to have all the information I'd need in just a couple places.

3. I enjoyed this lab, though I did not enjoy trying to find the energy-time to do it in (I'm sorry it is so late). Once I finished writing and debugging the code to read in an mdp file and make the problem, the rest seemed to go really easily. I enjoyed the experiment and getting to see the practical effects of changing the gamma value. I did not enjoy trying to figure out how to get the simulator to run. It wouldn't run when I ssh'd into my cs.oberlin account, and I had to try updating java a couple of times. But oh well, maybe it will be useful in the future?

4. I spent maybe 8-10 hours on this assignment? One of these days I'll remember to keep track, I swear.

5. I affirm that I have adhered to the Honor Code on this assignment - Sezny Watkins