
package redes;

import java.util.PriorityQueue;

public class Node {
    int reserved;
    int seats;
    static int time = 0;
    PriorityQueue<Message> q;
    
    public Node(){
        reserved = 0;
        q = new PriorityQueue<Message>();
        seats = 30;
    }
    
    public int available(){
        return seats - reserved;
    }
    
    public boolean reserve(int n){
        if(n <= available()){
            reserved =+ n;
            return true;
        }
        return false;
    }
    
    public boolean cancel(int n){
        if(n <= reserved){
            reserved =- n;
            return true;
        }
        return false;
    }
    
    
}
