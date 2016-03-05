
package redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer extends Thread {

    static int replyCount = 0;
    DatagramSocket serverSocket;
    byte[] receiveData;
    byte[] sendData;
    private CheckAndExecute checkAndExecute;

    public UDPServer() throws SocketException {
        serverSocket = new DatagramSocket(9876);
        receiveData = new byte[1024];
        sendData = new byte[1024];
        checkAndExecute = new CheckAndExecute();
    }

    @Override
    public void run() {
        System.out.println("UDP SERVER ON");
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
               // System.out.println("Received UDP "+ sentence);
                String[] s = sentence.split("-");
                switch(s[0]){
                    case Main.REQUEST:    
                        Message msg = new Message(Integer.parseInt(s[1]),s[2],Integer.parseInt(s[3])); // crea el mensaje nuevo con lo que le llego
                        System.out.println("arreglo " + s.length);
                        if(s.length == 6){
                            msg.setParameter(Integer.parseInt(s[4]));
                        }
                        Node.time = Math.max(Node.time, msg.getTime()) + 1;
                        System.out.println("me llego request: " + msg.toString());                
                        synchronized (this) {Main.q.add(msg);}
                        reply(msg.getPid());
                    break;
                    case Main.REPLY:
                        replyCount++;
                        if(replyCount >= Main.ips.size()){  
                            System.out.println("Me llegaron todos los replys");
                            checkAndExecute.start();
                        }
                        
                    break;
                    case Main.RELEASE:
                        System.out.println("me llego release");
                        Message  m;
                        synchronized (this) {m= Main.q.remove();}   
                        exec(m); // elimina el tope y lo ejecuta para igualar su estado al de los demas.
                        //checkAndExecute(); //se fija si es su turno y ejecuta
                      
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
    
    public static void exec(Message m){        
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
    
    /*private void checkAndExecute() throws IOException{
        
    }*/
    
    public static void release() throws IOException {
        replyCount = 0;
        broadcast(null,Main.RELEASE);
    }
    
    private void reply(Integer dst) throws IOException {
      int i;
      for (i = 0; !Main.pids.get(i).equals(dst);i++){};
      send(Main.ips.get(i));  
    }
    
    private void send(String ip) throws IOException{
        DatagramSocket clientSocket = new DatagramSocket();            
        InetAddress IPAddress = InetAddress.getByName(ip);
        String sentence = Main.REPLY+ "-";
        byte[] sendData = new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);             
        clientSocket.close();  
    }
    public static void broadcast(Message message, String type) throws IOException{
        for (int i = 0; i < Main.ips.size(); i++) {
            DatagramSocket clientSocket = new DatagramSocket();            
            InetAddress IPAddress = InetAddress.getByName(Main.ips.get(i));
            String sentence = "";
            if(message != null)
                sentence = type+ "-" + message.toString();
            else
                sentence = type+ "-";
            byte[] sendData = new byte[1024];
           // byte[] receiveData = new byte[1024];
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);       
           // DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);       
            //clientSocket.receive(receivePacket);       
            //String modifiedSentence = new String(receivePacket.getData());      
           // System.out.println("FROM SERVER:" + modifiedSentence);       
            clientSocket.close();  
        }
    }
}
