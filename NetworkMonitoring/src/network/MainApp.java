package network;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Network Monitoring Tool ===");
        System.out.println("1. Ping check");
        System.out.println("2. Port scan");
        System.out.println("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        
        switch (choice) {
            case 1:
                System.out.println("Enter IP or domain: ");
                String ip = scanner.nextLine();
                PingChecker.checkPing(ip);
                break;
            case 2:
                System.out.println("Enter host: ");
                String host = scanner.nextLine();
                System.out.println("Enter port range (start-end): ");
                String range = scanner.nextLine();
                String[] ports = range.split("-");
                int startPort = Integer.parseInt(ports[0]);
                int endPort = Integer.parseInt(ports[1]);
                PortScanner.scanPorts(host, startPort, endPort);
                break;
            default:
                System.out.println("Invalid option!");
        }
        
        scanner.close();
    }
}
