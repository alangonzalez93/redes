
package redes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServer extends Thread {

    String clientSentence;
    String capitalizedSentenceTCP;
    ServerSocket welcomeSocket;

    public TCPServer() throws IOException {
        welcomeSocket = new ServerSocket(6789);
    }

    @Override
    public void run() {
        System.out.println("TCP SERVER ON");
        while (true) {
            Socket connectionSocket;
            try {
                connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();
                System.out.println("Received TCP: " + clientSentence);
                capitalizedSentenceTCP = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentenceTCP);
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
