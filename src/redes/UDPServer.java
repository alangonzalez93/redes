
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
                        System.out.println("request: " + msg.toString());
                        Node.q.add(msg);
                        reply();
                    break;
                    case Node.REPLY:
                        replyCount++;
                        if(replyCount >= Node.NPROCESSES){                            
                            checkAndExecute();
                        }
                         System.out.println("reply: " );
                    break;
                    case Node.RELEASE:
                        Node.q.remove();
                        checkAndExecute();
                         System.out.println("release: ");
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
    
    private void checkAndExecute(){
        if(!Node.q.isEmpty() && Node.q.peek().getPid() == Node.pid){
            Message  m = Node.q.remove();
            switch(m.getMsg()){
                case "available":
                    System.out.println(Node.available());
                break;
                case "reserve":
                    Node.reserve(m.getParameter());
                break;
                case "cancel":
                    Node.cancel(m.getParameter());
                break;
            }
        }
    }
    
    private void release() throws IOException {
        Node.time++;
        int time_stamp = Node.time;
       // Message message = new Message(time_stamp,command,1);
        DatagramSocket clientSocket = new DatagramSocket();       
        InetAddress IPAddress = InetAddress.getByName("192.168.0.25");
        String sentence = Node.RELEASE + "-";
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);       
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);       
        clientSocket.receive(receivePacket);       
        String modifiedSentence = new String(receivePacket.getData());      
        //System.out.println("FROM SERVER:" + modifiedSentence);       
        clientSocket.close();
    }
    
    private void reply() throws IOException {
        Node.time++;
        int time_stamp = Node.time;
       // Message message = new Message(time_stamp,command,1);
        DatagramSocket clientSocket = new DatagramSocket();       
        InetAddress IPAddress = InetAddress.getByName("192.168.0.25");
        String sentence = Node.REPLY + "-";
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);       
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);       
        clientSocket.receive(receivePacket);       
        String modifiedSentence = new String(receivePacket.getData());      
        //System.out.println("FROM SERVER:" + modifiedSentence);       
        clientSocket.close();
    }
}
