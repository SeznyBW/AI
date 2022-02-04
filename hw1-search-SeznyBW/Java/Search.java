/* 
Sezny Watkins
Fall 2021, AI
Homework 1 - Search for a Restaraunt

Implements three different search functions - BFS, A*, UCS - for a given map
Then outputs the length, cost, which goal was reached, number of nodes expanded, and time spent searching
*/

import java.util.*;

public class Search {
    
    public static void main(String args[]){
        if (args.length==0){
            System.out.println("Please give a file name when calling this program");
        }
        else {
            //create the problem
            Map map = Map.readFromFile(args[0]);
            Problem prob = new Problem(map);
            //print what the map is
            System.out.println("Map : " + args[0]);
            System.out.println();
            //run BFS
            BFS(prob);
            //run A*
            A(prob);
            //run UCS
            UCS(prob);
        }
    }

    //run BFS on the problem
    public static void BFS(Problem prob) {
        //create variables for keeping track of the number of nodes, which goal state we found, and total time
        int nodes = 0;
        State goal=null;
        long time=0;
        //check the time we're starting at
        long start=System.currentTimeMillis();
        //create the frontier queue and explored set
        Queue<State> frontier = new LinkedList<State>();
        Set<State> explored = new HashSet<State>();
        //add the start node to the frontier
        State init = prob.startState();
        //I edited the state class to have a variable to keep track of the length and cost so far
        //I couldn't figure out how else to do it
        //So this updates those variables both to 0 for the start state
        init.leng=0;
        init.cost=0;
        frontier.add(init);
        //make a var for the state we're currently exploring
        State curr = frontier.poll();
        //and a var for the next states
        List<State> next;

        //while the frontier is not empty
        while (curr!=null){
            //increment the number of nodes we've explored
            nodes+=1;
            //put the current state in the explored set
            explored.add(curr);
            //get the children
            next=prob.actions(curr);
            //for each child
            for(State child : next){
                //update the length and cost for the child - length is +1 of the current, cost adds the cost of the action to the current
                child.leng=curr.leng+1;
                child.cost=curr.cost+prob.cost(curr, child);

                //stop if we've reached a goal, and set goal to the goal we've reached
                if(prob.goal(child)){
                    goal=child;
                    curr=null;
                    time=System.currentTimeMillis() - start;
                    break;
                }
                //else, add to frontier if not in frontier or explored
                else if(!explored.contains(child) && !frontier.contains(child)){
                    frontier.add(child);
                }
            }
            if (curr!=null){
                curr=frontier.poll();
            }
        }

        //check if we found a goal state
        if (goal==null){
            System.out.println("No goal state found");
        }

        else{
            //print out the info we've gathered
            System.out.println("BFS Path Length: " + goal.leng);
            System.out.println("BFS Path Cost: " + goal.cost);
            System.out.printf("BFS Goal Reached: (%f, %f)%n", goal.lat, goal.lon);
            System.out.println("Nodes Expanded with BFS: " + nodes);
            System.out.println("Time Spent with BFS (in milliseconds): " + time);
            System.out.println();
        }

    }


    //runs A* on the problem
    public static void A(Problem prob){
        //create variables for keeping track of the number of nodes, which goal state we found, and total time
        int nodes = 0;
        State goal=null;
        long time=0;
        //check the time we're starting at
        long start=System.currentTimeMillis();
        //create the frontier queue and explored set
        fnSort sort = new fnSort();
        PriorityQueue<State> frontier = new PriorityQueue<State>(sort);
        Set<State> explored = new HashSet<State>();
        //add the start node to the frontier
        State init = prob.startState();
        //I edited the state class to have a variable to keep track of the length and cost so far
        //I couldn't figure out how else to do it
        //So this updates those variables both to 0 for the start state
        init.leng=0;
        init.cost=0;
        frontier.add(init);
        //make a var for the state we're currently exploring
        State curr = frontier.poll();
        //and a var for the next states
        List<State> next;
        //and a var for when we're calculating the heuristic
        double min;
        double manhat;
        //and a var for a list of goal states
        List<State> goals =prob.goalStates();
        //and a var for an array version of the queue
        Object[] arr;
        State arrState;

        //while the frontier is not empty
        while (curr!=null){
            //if we're looking at the goal, exit the loop
            if (prob.goal(curr)){
                goal=curr;
                time=System.currentTimeMillis()-start;
                break;
            }
            //increment the number of nodes we've explored
            nodes+=1;
            //otherwise, add head to explored
            explored.add(curr);
            //and add its children to the frontier
            next=prob.actions(curr);
            for (State child : next){
                //update cost and length
                child.cost=curr.cost+prob.cost(curr, child);
                child.leng=curr.leng+1;
                //find the heuristic value, by finding the minimum manhattan distance to a goal
                min=-1;
                for(State goalie : goals){
                    manhat=Math.abs(goalie.lat - child.lat) + Math.abs(goalie.lon - child.lon);
                    if(min==-1){
                        min=manhat;
                    }
                    else if(manhat<min){
                        min=manhat;
                    }
                }
                child.heur=min;
                //and then get the fn value
                child.fn=child.cost+child.heur;
                //if child's not in explored or frontier, add to frontier
                if(!explored.contains(child) && !frontier.contains(child)){
                    frontier.add(child);
                }
                else if(frontier.contains(child)){
                    arr=frontier.toArray();
                    for(int i=0; i<arr.length; i++){
                        arrState=(State)arr[i]; 
                        if(child.equals(arrState)){
                            if(arrState.fn>child.fn){
                                frontier.remove(child);
                                frontier.add(child);
                            }
                            break;
                        }
                    }
                }
            }
            curr=frontier.poll();
        }

        //check if we found a goal state
        if (goal==null){
            System.out.println("No goal state found");
        }

        else{
            //print out the info we've gathered
            System.out.println("A* Path Length: " + goal.leng);
            System.out.println("A* Path Cost: " + goal.cost);
            System.out.printf("A* Goal Reached: (%f, %f)%n", goal.lat, goal.lon);
            System.out.println("Nodes Expanded with A*: " + nodes);
            System.out.println("Time Spent with A* (in milliseconds): " + time);
            System.out.println();
        }
    }

    //run the uniform cost search on a given problem
    public static void UCS(Problem prob){
        //create variables for keeping track of the number of nodes, which goal state we found, and total time
        int nodes = 0;
        State goal=null;
        long time=0;
        //check the time we're starting at
        long start=System.currentTimeMillis();
        //create the frontier queue and explored set
        costSort sort = new costSort();
        PriorityQueue<State> frontier = new PriorityQueue<State>(sort);
        Set<State> explored = new HashSet<State>();
        //add the start node to the frontier
        State init = prob.startState();
        //I edited the state class to have a variable to keep track of the length and cost so far
        //I couldn't figure out how else to do it
        //So this updates those variables both to 0 for the start state
        init.leng=0;
        init.cost=0;
        frontier.add(init);
        //make a var for the state we're currently exploring
        State curr = frontier.poll();
        //and a var for the next states
        List<State> next;

        //while the frontier is not empty:
        while(curr!=null){
            //if the head is the goal, we're done
            if (prob.goal(curr)){
                goal=curr;
                time=System.currentTimeMillis()-start;
                break;
            }
            //otherwise, we're exploring the node so increment the nodes explored
            nodes+=1;
            //add the current node to explored
            explored.add(curr);
            //get the list of children
            next=prob.actions(curr);
            //loop through the kids
            for(State child : next){
                //update it's cost and length
                child.cost=curr.cost+prob.cost(curr, child);
                child.leng=curr.leng+1;
                //if the child's state is not in explored or frontier, add it to the frontier
                if(!frontier.contains(child) && !explored.contains(child)){
                    frontier.add(child);
                }
                //otherwise, if it's already in the frontier at lower cost, replace it
                else if (frontier.contains(child)){
                    Iterator<State> iter = frontier.iterator();
                    while(iter.hasNext()){
                        State check = iter.next();
                        if(check.equals(child)){
                            if(check.cost>child.cost){
                                frontier.remove(child);
                                frontier.add(child);
                            }
                            break;
                        }
                    }
                }
            }
            //grab the new head of the frontier
            curr=frontier.poll();
        }

        //check if we found a goal state
        if (goal==null){
            System.out.println("No goal state found");
        }

        else{
            //print out the info we've gathered
            System.out.println("UCS Path Length: " + goal.leng);
            System.out.println("UCS Path Cost: " + goal.cost);
            System.out.printf("UCS Goal Reached: (%f, %f)%n", goal.lat, goal.lon);
            System.out.println("Nodes Expanded with UCS: " + nodes);
            System.out.println("Time Spent with UCS (in milliseconds): " + time);
            System.out.println();
        }
    }
}

class costSort implements Comparator<State>{
    public int compare(State x, State y){
        if (x.cost>y.cost){
            return 1;
        }
        else{
            return -1;
        }
    }
}

class fnSort implements Comparator<State>{
    public int compare(State x, State y){
        if (x.fn>y.fn){
            return 1;
        }
        else {
            return -1;
        }
    }
}