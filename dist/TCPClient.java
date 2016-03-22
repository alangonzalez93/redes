import java.io.*;
import java.net.*;
class TCPClient {  
  public static void main(String argv[]) throws Exception  {   
  		send("reserve 4");
  		send("reserve 2");
	} 

	private static void send(String data)throws Exception  {
		String sentence;
  	    String modifiedSentence;  
   		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in)); 
     	Socket clientSocket = new Socket("localhost", 6789); 
       	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = inFromUser.readLine();   
        outToServer.writeBytes(data); 
        //modifiedSentence = inFromServer.readLine(); 
        //System.out.println("FROM SERVER: " + modifiedSentence); 
        //clientSocket.close();  
	}
} 