
package redes;

import java.io.IOException;


public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        TCPServer tcp = new TCPServer();
        UDPServer udp = new UDPServer();
        tcp.start();
        udp.start();
    }
}
