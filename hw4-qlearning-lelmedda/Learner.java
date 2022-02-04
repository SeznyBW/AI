/* 

Sezny Watkins and Loubna El Meddah El Idrissi
Fall 2021, AI
Homework 4 - Stochastic Robot Navigation (Q-Learning)

Uses Q-Learning to enable a 
robot to learn how to navigate from its starting location in the bottom left corner to the goal 
location in the top right corner
runs 100 episodes

*/

import java.util.*;
import java.util.Random;
import java.io.*;

public class Learner{

    public static void main (String args[]){
      //  try{
            //get the alpha and epsilon values
            double alpha = Double.parseDouble(args[0]);
            double epsilon = Double.parseDouble(args[1]);

            //make a new grid
            Grid g = new Grid();
            //initialize all Q and n values to 0
            double[][][]Q= new double[15][6][4];
            int[][][]n= new int[15][6][4];
            for (int x=0; x<15; x++){
                for (int y=0; y<6; y++){
                    for (int z=0; z<4; z++){
                        Q[x][y][z]=0.0;
                        n[x][y][z]=0;
                    }
                }
            }

            //run 100 episodes, printing the cumulative rewards
            // Add for loop after testing
            for (int i=0; i<100; i++){
                System.out.println(episode(g, alpha, epsilon, Q, n));
            }
     //   }
        //if we weren't given the two arguments, complain
       /* catch(IndexOutOfBoundsException e){
            System.out.println("Not all arguments present. Please try again with java Learner <alphaValue> <epsilonValue>");
        }*/
    }

    //a single episode, from start to an absorbing state
    //returns the cumulative reward for the episode
    public static double episode(Grid g, double alpha, double epsilon, double[][][] Q, int[][][] n){
        //keep track of the cummulative reward
        double cumr = 0.0;
        //get the start state 
        State state = g.generateStartState();
        //until we are in an end state, choose an action and recalculate the Q value
        while (state!=g.ABSORBING_STATE){
            String action = getAction(state, epsilon, Q);
            //get the next state and the reward
            State nextstate = g.generateNextState(state, action);
            double reward = g.generateReward(state, action);
            //update the Q value
            updateq(g, Q, alpha, state, action, nextstate, reward, n);
            //update cumr
            cumr=cumr+reward;
            //update state to the next state
            state=nextstate;
        }
        return cumr;
    }

    //update the Q value after picking an action
    public static void updateq(Grid g, double[][][] Q, double alpha, State currstate, String action, State newstate, double reward, int[][][] n){
        //find the max Q value for th new state
        double max=0.0;
        if (newstate.x!=-1) {
            max = Q[newstate.x][newstate.y][0];
            for (int i=1; i<4; i++) {
                if (Q[newstate.x][newstate.y][i]>max){
                    max = Q[newstate.x][newstate.y][i];
                }
            }
        }
        //if requested, use the 1/n for alpha. otherwise use the given alpha value
        double a;
        if (alpha!=0){
            a=alpha; 
        }
        else{
            n[currstate.x][currstate.y][g.actions.indexOf(action)]+=1;
            a=1/n[currstate.x][currstate.y][g.actions.indexOf(action)];
        }
        //update the Q value
        Q[currstate.x][currstate.y][g.actions.indexOf(action)]=((1-a)*Q[currstate.x][currstate.y][g.actions.indexOf(action)]) + (a * (reward + (.99*max)));
    }

    public static String getAction(State state, double epsilon, double[][][] Q){
        // takes in epsilon number, current state, and updated Q-Table
      
         // Creating an object of List with the 4 possible actions
        List<String> actions = new ArrayList<>(4);
        String action;
 
         // Adding elements to object of List class
        actions.add("up");
        actions.add("down");
        actions.add("left");
        actions.add("right"); 

        Random random = new Random();
        double randnum = random.nextDouble();

        if (randnum<epsilon){
            // With probability ($\epsilon$) 
            // choose random action (exploration)
            int rand = random.nextInt(4);
            action = actions.get(rand);
        }
        else{
            // With probability (1-epsilon)
            // choose best action (exploitation)
            double max = Q[state.x][state.y][0];
            action=actions.get(0);
            for (int i=1; i<4; i++) {
                if (Q[state.x][state.y][i]>max){
                    max = Q[state.x][state.y][i];
                    action = actions.get(i);
                }
            }
        }
        return action;
    }
}
