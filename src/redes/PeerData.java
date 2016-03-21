package redes;

public class PeerData {
    
    private String ip;
    private final int pid;
    private final int udpPort;
    private final int tcpPort;

    public PeerData(String ip, int pid, int udpPort, int tcpPort) {
        this.ip = ip;
        this.pid = pid;
        this.udpPort = udpPort;
        this.tcpPort = tcpPort;
    }

    public PeerData(int pid, int udpPort, int tcpPort) {
        this.pid = pid;
        this.udpPort = udpPort;
        this.tcpPort = tcpPort;
    }

    public String getIp() {
        return ip;
    }

    public int getPid() {
        return pid;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    @Override
    public String toString() {
        return "PeerData{" + "ip=" + ip + ", pid=" + pid + ", udpPort=" + udpPort + ", tcpPort=" + tcpPort + '}';
    }
    
    
    
}
