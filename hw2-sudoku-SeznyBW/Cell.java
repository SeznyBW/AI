/* 
Sezny Watkins
Fall 2021, AI
Homework 2 - Sudoku (Constraint Satisfaction Problems)

Class for each cell in the Sudoku puzzle
Has the domain and a list of constraints tied to the cell
*/

import java.util.*;

public class Cell{
    //the domain of the cell
    public ArrayList<Integer> domain=new ArrayList<Integer>();
    //the cells that it has constraints with / can't be the same as
    public ArrayList<Integer> constraints=new ArrayList<Integer>();

    //constructor for if the number is not known
    //sets the domain to 1-9
    public Cell(){
        for (int i=1; i<10; i++){
            this.domain.add(i);
        }
    }

    //constructor for if the number for the cell is known
    //sets the domain to that number
    public Cell(int num){
        this.domain.add(num);
    }
}