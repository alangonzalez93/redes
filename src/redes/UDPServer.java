
package redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer extends Thread {

    int replyCount = 0;
    DatagramSocket serverSocket;
    byte[] receiveData;
    byte[] sendData;

    public UDPServer() throws SocketException {
        serverSocket = new DatagramSocket(9876);
        receiveData = new byte[1024];
        sendData = new byte[1024];
    }

    @Override
    public void run() {
        System.out.println("UDP SERVER ON");
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println("Received UDP "+ sentence);
                String[] s = sentence.split("-");
                switch(s[0]){
                    case Node.REQUEST:
                        Message msg = new Message(Integer.parseInt(s[1]),s[2],Integer.parseInt(s[3])); // crea el mensaje nuevo con lo que le llego
                        System.out.println("Received UDP: " + msg.toString());
                        Node.q.add(msg);
                    break;
                    case Node.REPLY:
                        replyCount++;
                        if(replyCount >= Node.NPROCESSES){
                            replyCount =0;
                            if(!Node.q.isEmpty() && Node.q.peek().pid == Node.pid){
                                Message  m = Node.q.remove();
                                switch(m.msg){
                                    case "available":
                                        System.out.println(Node.available());
                                    break;
                                    case "reserve":
                                        Node.reserve(m.parameter);
                                    break;
                                    case "cancel":
                                }
                            }
                        }
                    break;
                    case Node.RELEASE:
                       
                        //System.out.println("Received UDP: " + msg.toString());
                        
                    break;
                
                }

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            } catch (IOException ex) {
                Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    private void release(){
    
    }
    
    private void reply(){
    
    }
}
