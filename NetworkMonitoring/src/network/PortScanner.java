package network;
import java.io.IOException;
import java.net.Socket;

public class PortScanner {
    public static void scanPorts(String host, int startPort, int endPort) {
        for (int port = startPort; port <= endPort; port++) {
            try (Socket socket = new Socket(host, port)) {
                System.out.println("Port " + port + " is open.");
                Logger.log("Port " + port + " on " + host + " is open.");
            } catch (IOException e) {
                System.out.println("Port " + port + " is closed.");
                Logger.log("Port " + port + " on " + host + " is closed.");
            }
        }
    }
}
