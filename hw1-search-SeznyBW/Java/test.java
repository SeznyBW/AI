import java.util.*;

public class test {
    public static void main(String args[]){
        PriorityQueue<State> frontier = new PriorityQueue<State>((x, y) -> (int)x.fn-(int)y.fn);
        State one = new State(10,10);
        one.fn=100;
        frontier.add(one);
        State two= new State(100, 100);
        two.fn=10;
        frontier.add(two);
        State three=new State(100, 100);
        three.fn=100;
        frontier.add(three);
        State four=new State(50,50);
        four.fn=50;
        frontier.add(four);
        Iterator<State> iter = frontier.iterator();
        System.out.println(two.equals(iter.next()));
        System.out.println(four.equals(iter.next()));
        System.out.println(two.equals(iter.next()));
        System.out.println(one.equals(iter.next()));
        
        Object[] arr =frontier.toArray();
        for(int i=0; i<arr.length; i++){
            State arrState=(State)arr[i]; 
            if(one.equals(arrState)){
                System.out.println("Found " + i);
            }
        }

        two.fn=3;
        frontier.remove(two);
        frontier.add(two);
        System.out.println(frontier.poll().fn);
        System.out.println(frontier.poll().fn);
        System.out.println(frontier.poll().fn);
        System.out.println(frontier.poll().fn);

    }
}