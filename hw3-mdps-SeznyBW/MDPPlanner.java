/* 
Sezny Watkins
Fall 2021, AI
Homework 3 - Robotic Wildfire Suppression (Markov Decision Processes)

Makes a policy based on a markov decision problem:
Parses an MDP file, 
performs the value iteration algorithm,
and saves the policy to a file
*/

import java.util.*;
import java.io.*;

public class MDPPlanner{

    public static void main (String args[]){
        try{
            //read in the mdp file and parse it
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            State[] states=parse(reader);
            //do value iteration
            Double gamma = Double.parseDouble(args[1]);
            iterate(states, gamma);
            //write the plan to the given file
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[2]));
            plan(states, writer);
            writer.close();
        }
        //if we couldn't find the mdp input file, print an error message
        catch(FileNotFoundException e){
            System.out.println("input MDP file not found. Please try again with a valid file name");
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Not all arguments present. Please try again with java MDPPlanner <mdpFilename> <gammaValue> <policyFilename>");
        }
        catch(IOException e){
            System.out.println("IO Exception. Please try again.");
        }
    }

    //parse the inputted mdp file, outputting a list of states
    public static State[] parse(BufferedReader reader){
        try{
            //store all the information on states
            ArrayList<String> statesString=new ArrayList<String>();
            String line = reader.readLine();
            line=reader.readLine();
            while (!line.equals("")){
                statesString.add(line);
                line=reader.readLine();
            }
            //see how many actions there are
            int a=0;
            line=reader.readLine();
            line=reader.readLine();
            while(!line.equals("")){
                a+=1;
                line=reader.readLine();
            }

            //go back through and make all the states
            State[] states=new State[statesString.size()];
            String[] partsS = new String[2];
            while(statesString.size()!=0){
                partsS=statesString.remove(0).split(",");
                states[Integer.parseInt(partsS[0])]=new State(Integer.parseInt(partsS[0]), partsS[1], a);
            }

            //add all the transitions
            line=reader.readLine();
            line=reader.readLine();
            String[] partsT=new String[4];
            while(!line.equals("")){
                partsT=line.split(",");
                states[Integer.parseInt(partsT[0])].transitions.get(Integer.parseInt(partsT[1])).add(new Transition(states[Integer.parseInt(partsT[0])], Integer.parseInt(partsT[1]), states[Integer.parseInt(partsT[2])], Double.parseDouble(partsT[3])));
                line=reader.readLine();
            }

            //add the reward values to each transition
            line=reader.readLine();
            String[] partsR=new String[4];
            while (reader.ready()){
                line=reader.readLine();
                partsR=line.split(",");
                ArrayList<Transition> looking = states[Integer.parseInt(partsR[0])].transitions.get(Integer.parseInt(partsR[1]));
                for(Transition check : looking){
                    if (states[Integer.parseInt(partsR[2])].equals(check.end)){
                        check.reward=Integer.parseInt(partsR[3]);
                        break;
                    }
                }
            }
            //return list of states
            return states;
        }
        catch(IOException e){
            System.out.println("Trouble reading in file. Please try again");
            System.exit(1);
        }

        //it should never get here, but it won't run without it ugh
        State[] states = new State[0];
        return states;
    }

    //update Q and V values
    public static void iterate (State[] states, Double gamma){
        //a value to tell us when to stop iterating
        Double change=1.0;
        //keep iterating until the largest change in V is less than .1
        while (change>=.1){
            //initialize change to -1 so it will be updated the first time
            change=-1.0;

            //update all Q values
            for (int i=0; i<states.length; i++){
                State currS=states[i];
                //for each action, find the Q(s,a) value;
                for (int j=0; j<currS.transitions.size();j++){
                    Double q=0.0;
                    //for each transition, add to the bellman equation
                    for (int k=0; k<currS.transitions.get(j).size(); k++){
                        Transition currT = currS.transitions.get(j).get(k);
                        q=q+(currT.prob*(currT.reward+(gamma*currT.end.V)));
                    }
                    //update the Q(s, a) value
                    currS.Q[j]=q;
                }
            }

            //update all V values
            for (int i=0; i<states.length; i++){
                State currS=states[i];
                //val to keep track of the greatest Q val:
                Double v = currS.Q[0];
                //check all the other Q vals to see if it's greater
                for (int j=1; j<currS.Q.length; j++){
                    //if the Q value is greater than our current v, update v to that value
                    if (currS.Q[j]>v){
                        v=currS.Q[j];
                    }
                }
                //check if this is the greatest change to a v value that we've made
                if (Math.abs(currS.V-v)>change){
                    //if so, update the change value to the new difference
                    change=Math.abs(currS.V-v);
                }
                //update the V value
                currS.V=v;
            }
        }

    }

    //write the plan to a given file
    public static void plan (State[] states, BufferedWriter writer){
       try{
        //loop through the states and give the plan for each state
            for (int i=0; i<states.length; i++){
                State curr = states[i];
                //find the action with the best q value for that state
                double bestQ= curr.Q[0];
                int bestAction=0;
                for (int j=1; j<curr.Q.length; j++){
                    if(curr.Q[j]>bestQ){
                        bestQ=curr.Q[j];
                        bestAction=j;
                    }
                }
                //print the action for the given state
                String s= Integer.toString(i).concat(",").concat(Integer.toString(bestAction));
                writer.write(s);
                writer.newLine();
            }
        }
        catch(IOException e){
            System.out.println("IOException in plan");
        }
    }


}