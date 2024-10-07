package GUI;

import network.PingChecker;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import network.PortScanner;

public class MainAppGUI {
    public static void main(String[] args) {
        // Tạo frame chính
        JFrame frame = new JFrame("Network Monitoring Tool");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Tạo nhãn và các nút
        JLabel titleLabel = new JLabel("=== Network Monitoring Tool ===");
        titleLabel.setBounds(100, 20, 250, 30);
        frame.add(titleLabel);

        JButton pingButton = new JButton("Ping check");
        pingButton.setBounds(100, 80, 200, 30);
        frame.add(pingButton);

        JButton portScanButton = new JButton("Port scan");
        portScanButton.setBounds(100, 130, 200, 30);
        frame.add(portScanButton);

        // Tạo Text Area cho output
        JTextArea outputArea = new JTextArea();
        outputArea.setBounds(50, 180, 300, 70);
        outputArea.setEditable(false);
        frame.add(outputArea);

        // Sự kiện khi người dùng bấm nút Ping
        pingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = JOptionPane.showInputDialog("Enter IP or domain: ");
                if (ip != null && !ip.isEmpty()) {
                    // Gọi hàm checkPing (giả sử hàm này tồn tại)
                    outputArea.setText("Pinging " + ip + "...\n");
                    PingChecker.checkPing(ip);  // Thực hiện hàm ping
                } else {
                    outputArea.setText("Invalid IP or domain!");
                }
            }
        });

        // Sự kiện khi người dùng bấm nút Port Scan
        portScanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String host = JOptionPane.showInputDialog("Enter host: ");
                if (host != null && !host.isEmpty()) {
                    String range = JOptionPane.showInputDialog("Enter port range (start-end): ");
                    if (range != null && range.contains("-")) {
                        String[] ports = range.split("-");
                        int startPort = Integer.parseInt(ports[0]);
                        int endPort = Integer.parseInt(ports[1]);

                        outputArea.setText("Scanning ports from " + startPort + " to " + endPort + "...\n");
                        PortScanner.scanPorts(host, startPort, endPort);  // Thực hiện scan ports
                    } else {
                        outputArea.setText("Invalid port range!");
                    }
                } else {
                    outputArea.setText("Invalid host!");
                }
            }
        });

        // Hiển thị giao diện
        frame.setVisible(true);
    }
}
