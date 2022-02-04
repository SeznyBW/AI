/* 
Sezny Watkins
Fall 2021, AI
Homework 3 - Robotic Wildfire Suppression (Markov Decision Processes)

Class for the transition
Keeps track of it's start state, end state, action, probability, and reward
*/


import java.util.*;

public class Transition{
    //the start state
    public State start;
    //the end state
    public State end;
    //the action
    public int action;
    //the probablity
    public double prob;
    //and the reward
    public int reward=0;


    //constructor, initializes the id and label
    public Transition(State start, int action, State end, double prob){
        this.start=start;
        this.end=end;
        this.action=action;
        this.prob=prob;
    }
}