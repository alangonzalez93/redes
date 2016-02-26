
package redes;

import java.util.PriorityQueue;

public class Node {
    
    public static final String REQUEST = "REQUEST";
    public static final String REPLY = "REPLY";
    public static final String RELEASE = "RELEASE";
    public static final int NPROCESSES = 2;
    public static final int pid = 1;
    
    static int reserved = 0;
    static int seats = 30;
    static int time = 0;
    static PriorityQueue<Message> q = new PriorityQueue<Message>();;
    
    /*public Node(){
        reserved = 0;
        q = new PriorityQueue<Message>();
        seats = 30;
    }*/
    
    public static int available(){
        return seats - reserved;
    }
    
    public static boolean reserve(int n){
        if(n <= available()){
            reserved =+ n;
            return true;
        }
        return false;
    }
    
    public static boolean cancel(int n){
        if(n <= reserved){
            reserved =- n;
            return true;
        }
        return false;
    }
    
    
}
