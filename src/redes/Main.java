
package redes;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Main {
 
    public static final String REQUEST = "REQUEST";
    public static final String REPLY = "REPLY";
    public static final String RELEASE = "RELEASE";
    public static final int NPROCESSES = 1;
    public static final int pid = 1;
     static PriorityQueue<Message> q = new PriorityQueue<Message>(10, new Comparator<Message>(){
        public int compare(Message m1, Message m2) {
            return (m1.getTime() > m2.getTime()) ? 1 :
                    (m1.getTime() < m2.getTime()) ? -1 :
                    (m1.getPid() > m2.getPid()) ? 1 : -1;
        }
    });
    
    
    public static void main(String[] args) throws IOException {
        TCPServer tcp = new TCPServer();
        UDPServer udp = new UDPServer();
        tcp.start();
        udp.start();
    }
}
