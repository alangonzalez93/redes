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
    public static final int pid = 2;
    public static ArrayList<String> ips = new ArrayList();
    public static ArrayList<Integer> pids = new ArrayList();
    
    static PriorityQueue<Message> q = new PriorityQueue<Message>(10, new Comparator<Message>() {
        public int compare(Message m1, Message m2) {
            return (m1.getTime() > m2.getTime()) ? 1
                    : (m1.getTime() < m2.getTime()) ? -1
                            : (m1.getPid() > m2.getPid()) ? 1 : -1;
        }
    });

    private static void loadConfig() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("config.txt"));
        try {
            String line = br.readLine();
            while (line != null) {
                String[] sLine = line.split("-");
                ips.add(sLine[0]);
                pids.add(Integer.parseInt(sLine[1]));
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    public static void main(String[] args) throws IOException {
        loadConfig();
        for (String s : ips){
            System.out.println(s);
        }
        for (Integer i : pids){
            System.out.println(i.toString());
        }
        TCPServer tcp = new TCPServer();
        UDPServer udp = new UDPServer();
        tcp.start();
        udp.start();
    }
}
