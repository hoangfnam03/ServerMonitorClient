package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class ServerMonitor {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(6000);
            System.out.println("Wating client...");

            while (true) {
                clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String logMessage;
                while ((logMessage = in.readLine()) != null) {
                    // Ghi nhận log từ client
                    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    System.out.println("[" + timestamp + "] Log nhận từ client: " + logMessage);
                    System.out.println("\n\n========================================================\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
