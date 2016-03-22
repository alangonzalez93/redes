
package redes;


public class Terminal {
    

    
    static int reserved = 0;
    static final int seats = 30;
    static int time = 0;
    
    public static int available(){
        return seats - reserved;
    }
    
    public static boolean reserve(int n){
        if(n <= available()){
            System.out.println("Reservaste " + n);
            reserved += n;
            return true;
        }
        return false;
    }
    
    public static boolean cancel(int n){
        if(n <= reserved){
            System.out.println("Cancelaste " + n);
            reserved -= n;
            return true;
        }
        return false;
    }
    
    
}
