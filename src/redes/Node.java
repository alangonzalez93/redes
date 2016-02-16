
package redes;

public class Node {
    int reserved;
    int seats;
    
    public Node(){
        reserved = 0;
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
