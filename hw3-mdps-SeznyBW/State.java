/* 
Sezny Watkins
Fall 2021, AI
Homework 3 - Robotic Wildfire Suppression (Markov Decision Processes)

Class for each state in the problem
Keeps track of it's unique identifier, label (for debugging), and a list of transition going from that state
*/


import java.util.*;

public class State{
    //the unique identifier
    public int id;
    //the label for debugging
    public String label;
    //a list of transitions with this state as the start state, for each possible action
    public ArrayList<ArrayList<Transition>> transitions=new ArrayList<ArrayList<Transition>>();
    public Double[] Q;
    public Double V=0.0;
 
    //constructor, initializes the id and label
    public State(int id, String label, int actions){
        this.id=id;
        this.label=label;
        Q=new Double[actions];
        for (int a=0; a<actions; a++){
            Q[a]=0.0;
        }
        for (int i=0; i<actions; i++){
            ArrayList<Transition> t = new ArrayList<Transition>();
            transitions.add(t);
        }
    }
}