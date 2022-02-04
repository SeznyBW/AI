/* 
Sezny Watkins
Fall 2021, AI
Homework 2 - Sudoku (Constraint Satisfaction Problems)

Takes in a set of Sudoku puzzles, 
models each as a CSP,
outputs solutions to each puzzle.
It does so with Constraint Propogation and Backtracking Search.
*/

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class SudokuSolver {
    
    public static void main (String args[]){
        try{
            //a reader to read from the file
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            //the sudoku board for the given puzzle
            ArrayList<Cell> board;
            //while there's another puzzle, read it in and solve it
            while(reader.ready()){
                //read it in/set up the board
                board=readBoard(reader);
                //run constraint propogation
                board=AC3(board);
                //check if it's done
                for(int i=0; i<81; i++){
                    //if it's not, run the backtracking alg
                    if (board.get(i).domain.size()!=1){
                        board=Backtrack(board);
                        break;
                    }
                }
                //print out the completed board
                for (int i=0; i<81; i++){
       /*             if(i%9==0){
                        System.out.println();
                    }*/
                    if (board.get(i).domain.size()==1){
                        System.out.print(board.get(i).domain.get(0));
                    }
                    else{
                        System.out.print("error");
                    }
                }
                System.out.println();
           //     System.out.println();
            }
        }
        //if we can't read in the file, throw an exception
        catch(Exception e){
            System.out.println("Please give a valid file name");
        }
    }

    //reads in the sudoku board, prints it and stores it as a CSP
    public static ArrayList<Cell> readBoard (BufferedReader reader){
        //the board
        ArrayList<Cell> board = new ArrayList<Cell>();
        //the line we're reading in
        String line;
        //each cell's value
        String[] squares;

        //go through the nine rows of the board, print and make the Cells
        try{
            //read in the line
            line=reader.readLine();
            //get the line as individual characters
            squares=line.split("");
            //go through each cell in the row and make a Cell for it
            for (int j=0; j<81; j++){
                //print the value
/*                System.out.print(squares[j]);
                //if it's a new row next, print a new line
                if(j%9==8){
                    System.out.println();
                }*/
                //if it's a decimal, it's domain is 1-9
                if (squares[j].equals(".")){
                    board.add(new Cell());
                }
                //otherwise, it's domain is the number given
                else{
                    board.add(new Cell(Integer.parseInt(squares[j])));
                }
            }
        }
        catch(Exception e){
            System.out.println("End of document");
        }
        
        //now that we have all the cells made, make the list of constraints for each cell
        //the cell we're working on
        Cell working;
        //which row the cell is in
        int row;
        //which column the cell is in
        int col;
        //which square the cell is in (row)
        int boxr;
        //which square the cell is in (column)
        int boxc;
        //loop through each cell
        for (int i=0; i<81; i++){
            working=board.get(i);
            //get the row we're in
            row=i/9;
            //add every other cell in that row to the list of cells the current one has constraints with
            for (int j=row*9; j<(row+1)*9; j++){
                if (j!=i){
                    working.constraints.add(j);
                }
            }
            //get the column we're in
            col=i%9;
            //add ever other cell in that column to the list of cells the current one has constraints with
            for (int j=col; j<81; j=j+9){
                if (j!=i){
                    working.constraints.add(j);
                }
            }
            //get the box row we're in
            boxr=row/3;
            //and the column of the box we're in
            boxc=col/3;
            //the first cell in the box
            int j=((boxr*3)*9)+(boxc*3);
            //add each cell in the box to the list of constraints
            for(int k=0; k<9; k++){
                if (j!=i && !working.constraints.contains(j)){
                    working.constraints.add(j);
                }
                if (k%3==2){
                    j+=7;
                }
                else {
                    j+=1;
                }
            }
        }
        return board;
    }

    //run constraint propogation on the board
    public static ArrayList<Cell> AC3(ArrayList<Cell> board){
        //make a queue of all constraints
        Queue<Integer[]> arcs = new LinkedList<Integer[]>();
        //add all constraints to the queue
        for (int i=0; i<81; i++){
            for (int constraint : board.get(i).constraints){
                Integer[] pair={i, constraint};
                arcs.add(pair);
            }
        }

        //the constraint we're working on
        Integer[] currpair;
        //the cell we're updating
        Cell currcell;
        //while the queue is not empty, run constraint propogation
        while (arcs.peek()!=null){
            //get the next constraint
            currpair=arcs.poll();
            //get the cell we're updating the domain for
            currcell=board.get(currpair[0]);
            //run revise on it. 
            //if the domain changes:
            if (Revise(board, currcell, board.get(currpair[1]))){
                //add all of the cell's constraints to the queue
                //and update the cell
                for (int constraint : currcell.constraints){
                    Integer[] pair = {constraint, currpair[0]};
                    arcs.add(pair);
                }
                //if the domain is empty, return the board - it's not solvable so we're done
                if(currcell.domain.size()==0){
                    return board;
                }
            }
        }
        return board;
    }

    //given a constraint between two cells, see if the domain for the first changes
    public static Boolean Revise(ArrayList<Cell> board, Cell a, Cell b){
        //var to keep track of whether we've changed the domain or not
        Boolean changed=false;
        //a var to keep track of whether a constraint can be satisfied
        Boolean satisfied;
        for (int x=0; x<a.domain.size(); x++){
            //set satisfied to false until proven that it is true
            satisfied=true;
            for (int y : b.domain){
                //if the two values in the domain aren't equal then there's a way for it to be satisfied
                if(a.domain.get(x)!=y){
                    satisfied=false;
                    break;
                }
            }
            //if it's not satisfied, we need to remove it from the domain
            //and update revised to indicate that the domain has changed
            if (satisfied){
                a.domain.remove(x);
                //we've changed the domain, so set that to true
                changed=true;
            }
        }

        //return whether we've changed the domain
        return changed;
    }


    
    public static ArrayList<Cell> Backtrack(ArrayList<Cell> board){
        //check if it's complete
        //and which cell has the smallest domain
        Boolean complete=true;
        for (Cell x : board){
            if (x.domain.size()!=1){
                complete=false;
                break;
            }
        }
        //if so, return it
        if (complete){
            return board;
        }

        //otherwise, find the smallest domain that is not 1
        //this will be the one we try updating
        int min=10;
        int mini=0;
        int currdomsize;
        for (int i=0; i<81; i++){
            currdomsize=board.get(i).domain.size();
            if (currdomsize<min && currdomsize!=1){
                min=currdomsize;
                mini=i;
                if (currdomsize==2){
                    break;
                }
            }
        }

        //for the variable we've decided to set
        //loop through the possible values we can assign to it
        for (int value : board.get(mini).domain){
            //make a new problem with that value assigned to the variable
            ArrayList<Cell> newboard = copy(board);
            newboard.get(mini).domain.clear();
            newboard.get(mini).domain.add(value);
            //run constraint propogation on it
            newboard=AC3(newboard);
            //check if any of the domains are now empty
            Boolean empty=true;
            for (Cell check : newboard){
                if (check.domain.size()==0){
                    empty=false;
                    break;
                }
            }
            //if it's not empty, continue backtracking
            if (empty){
                Boolean success=true;
                ArrayList<Cell> result = Backtrack(newboard);
                //check if the backtracking gave an invalid answer
                for (Cell check : result){
                    if (check.domain.size()!=1){
                        success=false;
                        break;
                    }
                }
                //if not, we have our answer! return it;
                if(success){
                    return result;
                }
            }
        }
        return board;
    }

    public static ArrayList<Cell> copy(ArrayList<Cell> board){
        ArrayList<Cell> newboard = new ArrayList<Cell>();
        for (Cell curr : board){
            Cell newcell= new Cell();
            newcell.domain.clear();
            newcell.domain.addAll(curr.domain);
            newcell.constraints.addAll(curr.constraints);
            newboard.add(newcell);
        }
        return newboard;
    }
    
}
