
package redes;


public class Node {
    

    
    static int reserved = 0;
    static int seats = 30;
    static int time = 0;
   
    
    /*public Node(){
        reserved = 0;
        q = new PriorityQueue<Message>();
        seats = 30;
    }*/
    
    public static int available(){
        System.out.println("ejecute el available");
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
