
package redes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer extends Thread {

    String clientSentence;
    String capitalizedSentenceTCP;
    ServerSocket welcomeSocket;
    DataOutputStream outToClient;
    
    public TCPServer() throws IOException {
        welcomeSocket = new ServerSocket(6789);
    }
    
    private void request(String data)throws IOException{
        String[] arrayData = data.split(" ");
        String command = arrayData[0];
        String parameter = "";        
        Node.time++;
        int time_stamp = Node.time;
        Message message = new Message(time_stamp,command,Main.pid); //agregarle el parametro al mensaje
        if(arrayData.length > 1){
            parameter = arrayData[1];
            message.setParameter(Integer.parseInt(parameter));
        }
        Main.q.add(message);
        UDPServer.broadcast(message, Main.REQUEST);
    }
    
    @Override
    public void run() {
        System.out.println("TCP SERVER ON");
 
            Socket connectionSocket;
            try {
                connectionSocket = welcomeSocket.accept();
                while (!connectionSocket.isClosed()) {                                      
                    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    clientSentence = inFromClient.readLine();
                    request(clientSentence);
                    //System.out.println("Received TCP: " + clientSentence);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
}
