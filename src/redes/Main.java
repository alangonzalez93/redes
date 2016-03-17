package redes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {

    public static final String REQUEST = "REQUEST";
    public static final String REPLY = "REPLY";
    public static final String RELEASE = "RELEASE";
    public static final int NPROCESSES = 1;
    public static int pid;
    public static int udpPort;
    public static int tcpPort;
    //public static ArrayList<String> ips =  new ArrayList(); // ip's de los demas peers
    //public static ArrayList<Integer> pids = new ArrayList(); // pid's de los demas peers
    public static ArrayList<PeerData> peerData = new ArrayList();
    public static int parameter = -1; // parametro opcional de las consultas
    public static String command = "";
    
    //Cola de prioridades, donde se encolan consultas, es la estructura que usa el algoritmo distribuido de Lamport para la exclusion mutua
    static SyncQueue<Message> q = new SyncQueue<Message>(10, new Comparator<Message>() {
        public int compare(Message m1, Message m2) {
            return (m1.getTime() > m2.getTime()) ? 1
                    : (m1.getTime() < m2.getTime()) ? -1
                            : (m1.getPid() > m2.getPid()) ? 1 : -1;
        }
    });

    /*Metodo que lee el archivo de configuracion y carga los arreglos ips y pids*/
    private static void loadConfig() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("config.txt"));
        try {
            String line = br.readLine();
            String[] fstLine = line.split("-");//fstLine[0]=pid, fstLine[1]=udpPort, fstLine[2]=tcpPort
            peerData.add(new PeerData(Integer.parseInt(fstLine[0]), Integer.parseInt(fstLine[1]), Integer.parseInt(fstLine[2])));
            pid = peerData.get(0).getPid();
            udpPort = peerData.get(0).getUdpPort();
            tcpPort = peerData.get(0).getTcpPort();
            line = br.readLine();
            while (line != null) {
                String[] sLine = line.split("-");//sLine[0]=ip, sLine[1]=pid, sLine[2]=udpPort, sLine[3]=tcpPort
                peerData.add(new PeerData(sLine[0],Integer.parseInt(fstLine[1]), Integer.parseInt(fstLine[2]), Integer.parseInt(fstLine[3])));
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    /*Metodo principal, inicia los hilos de ejecucion de tcp para el cliente y udp para los peers*/
    public static void main(String[] args) throws IOException {
        loadConfig();
        for (PeerData pd : peerData){
            System.out.println(pd.toString());
        }
        
        TCPServer tcp = new TCPServer();
        UDPServer udp = new UDPServer();
        tcp.start();
        udp.start();
    }
}
