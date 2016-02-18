
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
    Node node;

    public TCPServer() throws IOException {
        welcomeSocket = new ServerSocket(6789);
        node = new Node();
    }

    private void accion(String data) throws IOException{
        String[] arrayData = data.split(" ");
        String command = arrayData[0];
        Integer parameter;
        switch (command){
            case "available":
               outToClient.writeBytes(String.valueOf(node.available()+"\n"));
               broadcast();
               break;
            case "reserve":
                parameter = Integer.valueOf(arrayData[1]);
                node.reserve(parameter);
                outToClient.writeBytes("reserva ok \n");
                broadcast();
                break;
            case "cancel":
                parameter = Integer.valueOf(arrayData[1]);
                node.cancel(parameter);
                outToClient.writeBytes("cancel ok \n");
                broadcast();
                break;
            default:
                outToClient.writeBytes("incorrect command \n");
                break;
        }
    }
    
    private void broadcast()throws IOException{
        DatagramSocket clientSocket = new DatagramSocket();       
        InetAddress IPAddress = InetAddress.getByName("192.168.0.25");
        String sentence = "Hola hermanitos mios";
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);       
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);       
        clientSocket.receive(receivePacket);       
        String modifiedSentence = new String(receivePacket.getData());      
        System.out.println("FROM SERVER:" + modifiedSentence);       
        clientSocket.close();
    }
    
    @Override
    public void run() {
        System.out.println("TCP SERVER ON");
        Socket connectionSocket;
            try {
                while (true) {
                connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                accion(clientSentence);
                //System.out.println("Received TCP: " + clientSentence);
                
                }
                //
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
}
