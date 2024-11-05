package Server;

import Account.AccountManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.border.LineBorder;

public class ServerMonitoringUI extends JFrame {

    private JTextArea textAreaLog;
    private JButton btnStartServer;
    private JButton btnStopServer;
    private JLabel lblStatus;

    private ServerSocket serverSocket;
    private boolean isServerRunning = false;

    public ServerMonitoringUI() {
        setTitle("Server Monitoring");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
    }

    private void initializeUI() {
        textAreaLog = new JTextArea();
        textAreaLog.setEditable(false);
        //textAreaLog.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textAreaLog);

        btnStartServer = createStyledButton("Start Server");
        btnStopServer = createStyledButton("Stop Server");
        btnStopServer.setEnabled(false); // Chỉ bật khi server đã chạy

        lblStatus = new JLabel("Server Status: Stopped");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnStartServer);
        panelButtons.add(btnStopServer);

        add(lblStatus, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Sự kiện khi nhấn nút Start Server
        btnStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();  
            }
        });

        // Sự kiện khi nhấn nút Stop Server
        btnStopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 36));
        button.setBackground(new Color(0, 102, 102));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(10), BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 120, 120));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 102, 102));
            }
        });
        return button;
    }
    class RoundedBorder extends LineBorder {
        private int radius;

        public RoundedBorder(int radius) {
            super(new Color(0, 102, 102), 2, true);
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(lineColor);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(5001);
            isServerRunning = true;
            lblStatus.setText("Server Status: Running");
            textAreaLog.append("Server started on port 5001\n");

            btnStartServer.setEnabled(false);
            btnStopServer.setEnabled(true);

            // Tạo một luồng để xử lý các kết nối từ client
            new Thread(new ClientHandler()).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể start server tại cổng 5001", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void stopServer() {
        try {
            isServerRunning = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            lblStatus.setText("Server Status: Stopped");
            textAreaLog.append("Server stopped.\n");

            btnStartServer.setEnabled(true);
            btnStopServer.setEnabled(false);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể dừng lại server", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Lớp xử lý client
    private class ClientHandler implements Runnable {

        @Override
        public void run() {
            while (isServerRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    textAreaLog.append("Người dùng đã kết nối: " + clientSocket.getInetAddress() + "\n");

                    DataInputStream din = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());

                    // Nhận loại yêu cầu từ client ("SIGNUP" hoặc "LOGIN")
                    String requestType = din.readUTF();

                    if ("SIGNUP".equals(requestType)) {
                        handleSignup(din, dout);
                    } else if ("LOGIN".equals(requestType)) {
                        handleLogin(din, dout, clientSocket);
                    } else {
                        textAreaLog.append("Yêu cầu không hợp lệ từ client.\n");
                        dout.writeUTF("INVALID_REQUEST");
                        clientSocket.close();
                    }

                } catch (IOException e) {
                    if (isServerRunning) {
                        textAreaLog.append("Lỗi kết nối tới client.\n");
                        e.printStackTrace();
                    }
                }
            }
        }

        private void handleSignup(DataInputStream din, DataOutputStream dout) {
            try {
                // Nhận thông tin đăng ký từ client
                String username = din.readUTF();
                String password = din.readUTF();
                String name = din.readUTF();

                AccountManager accountManager = new AccountManager();
                if (accountManager.isUsernameTaken(username)) {
                    dout.writeUTF("USERNAME_TAKEN");
                    textAreaLog.append("Tên tài khoản đã tồn tại: " + username + "\n");
                } else {
                    accountManager.addAccount(username, password, name);
                    dout.writeUTF(name);
                    textAreaLog.append("Đăng ký thành công: " + username + "\n");
                }
            } catch (IOException e) {
                textAreaLog.append("Lỗi xử lý đăng ký.\n");
                e.printStackTrace();
            }
        }

        private void handleLogin(DataInputStream din, DataOutputStream dout, Socket clientSocket) {
            try {
                // Nhận thông tin đăng nhập từ client
                String username = din.readUTF();
                String password = din.readUTF();

                AccountManager accountManager = new AccountManager();
                if (accountManager.checkCredentials(username, password)) {
                    String name = accountManager.getNameFromUsername(username);
                    dout.writeUTF(name);
                    textAreaLog.append(name + " đã đăng nhập thành công.\n");

                    // Khởi động luồng nhận/gửi tin nhắn cho client
                    new StartReceiving(name, din).start();
                    new StartSending(name, dout).start();
                } else {
                    dout.writeUTF("FAILURE");
                    clientSocket.close();
                    textAreaLog.append("Đăng nhập thất bại cho tài khoản: " + username + "\n");
                }
            } catch (IOException e) {
                textAreaLog.append("Lỗi xử lý đăng nhập.\n");
                e.printStackTrace();
            }
        }
    }


    // Lớp gửi dữ liệu cho client
    private class StartSending extends Thread {
        private String name;
        private DataOutputStream dout;

        public StartSending(String name, DataOutputStream dout) {
            this.name = name;
            this.dout = dout;
        }

        public void run() {
            try {
                while (isServerRunning) {
                    dout.writeUTF("Server Message: Hello " + name);
                    Thread.sleep(1000); // Gửi dữ liệu mỗi giây
                }
            } catch (IOException | InterruptedException e) {
                textAreaLog.append("Connection lost with client: " + name + "\n");
            }
        }
    }

    // Lớp nhận dữ liệu từ client
    private class StartReceiving extends Thread {
        private String name;
        private DataInputStream din;

        public StartReceiving(String name, DataInputStream din) {
            this.name = name;
            this.din = din;
        }

        public void run() {
            try {
                String message;
                while (isServerRunning && (message = din.readUTF()) != null) {
                    textAreaLog.append(name + ": " + message + "\n");
                }
            } catch (IOException e) {
                textAreaLog.append("Client disconnected: " + name + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServerMonitoringUI serverUI = new ServerMonitoringUI();
            serverUI.setVisible(true);
        });
    }
}
