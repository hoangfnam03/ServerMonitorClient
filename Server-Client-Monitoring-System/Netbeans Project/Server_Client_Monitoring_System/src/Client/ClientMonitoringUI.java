package Client;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientMonitoringUI extends JFrame {

    private JLabel connectionStatusLabel;
    private JLabel userInfoLabel;
    private JTextArea notificationsArea;
    private JButton reconnectButton;
    private JButton logoutButton;
    private JButton exitButton;

    private Socket socket;
    private DataOutputStream dout;
    private DataInputStream din;

    public ClientMonitoringUI(String name, Socket socket) throws IOException {
        setTitle("Giám sát Client");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JLabel headerLabel = new JLabel("Giám sát Client", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(headerLabel, BorderLayout.NORTH);

        // Panel thông tin người dùng và kết nối
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        userInfoLabel = new JLabel("Người dùng: " + name);
        connectionStatusLabel = new JLabel("Trạng thái: Đã kết nối");

        infoPanel.add(userInfoLabel);
        infoPanel.add(connectionStatusLabel);
        infoPanel.add(new JLabel()); // Placeholder

        add(infoPanel, BorderLayout.NORTH);

        // Khu vực thông báo và cảnh báo
        notificationsArea = new JTextArea();
        notificationsArea.setEditable(false);
        notificationsArea.setBorder(BorderFactory.createTitledBorder("Thông báo và Cảnh báo"));
        add(new JScrollPane(notificationsArea), BorderLayout.CENTER);

        // Panel cho nút điều khiển
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = createStyledButton("Đăng xuất");
        exitButton = createStyledButton("Thoát");
        controlPanel.add(logoutButton);
        controlPanel.add(exitButton);

        add(controlPanel, BorderLayout.SOUTH);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Kết nối tới server 
        this.socket = socket;
        this.dout = new DataOutputStream(socket.getOutputStream());
        this.din = new DataInputStream(socket.getInputStream());
        
        new HandleClient(this).start();
    }

    private void connectToServer() {
        int retryCount = 0;
        final int maxRetries = 3; // Giới hạn số lần thử lại
        boolean connected = false;

        while (!connected && retryCount < maxRetries) {
            try {
                socket = new Socket("localhost", 5001);
                dout = new DataOutputStream(socket.getOutputStream());
                din = new DataInputStream(socket.getInputStream());

                connectionStatusLabel.setText("Trạng thái: Đã kết nối");
                appendNotification("Kết nối tới server thành công.");

                // Đợi phản hồi xác nhận từ server
                String serverResponse = din.readUTF();
                if ("FAILURE".equals(serverResponse)) {
                    appendWarning("Không nhận được xác nhận từ server.");
                    connectionStatusLabel.setText("Trạng thái: Mất kết nối");
                    socket.close(); // Đóng kết nối nếu không có xác nhận
                    retryCount++;
                    appendNotification("Thử kết nối lại... (Lần " + retryCount + ")");
                    Thread.sleep(2000); // Tạm dừng trước khi thử lại
                } else {
                    appendNotification("Xác nhận từ server: Kết nối thành công.");
                    connected = true;
                    new HandleClient(this).start();
                }
            } catch (Exception e) {
                connectionStatusLabel.setText("Trạng thái: Mất kết nối");
                appendWarning("Không thể kết nối tới server. Đang thử lại...");
                retryCount++;
                try {
                    Thread.sleep(2000); // Tạm dừng trước khi thử lại
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!connected) {
            appendWarning("Không thể kết nối tới server sau " + maxRetries + " lần thử.");
        }
    }



    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(93, 36));
        button.setBackground(new Color(0, 102, 102));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(10), BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 120, 120));
            }

            @Override
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

    // Hàm giả lập kết nối lại
    private void reconnect() {
        connectionStatusLabel.setText("Trạng thái: Đang kết nối lại...");
        connectToServer();
    }

    private void logout() {
        appendNotification("Đã đăng xuất.");
        userInfoLabel.setText("Người dùng: Chưa đăng nhập");

        // Đóng kết nối socket và luồng vào/ra
        try {
            if (dout != null) {
                dout.close();
            }
            if (din != null) {
                din.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            appendNotification("Đã ngắt kết nối với server.");
        } catch (IOException e) {
            appendWarning("Lỗi khi đóng kết nối: " + e.getMessage());
            e.printStackTrace();
        }

        // Đóng giao diện hiện tại
        this.dispose();

        // Hiển thị lại giao diện đăng nhập
        SwingUtilities.invokeLater(() -> {
            try {
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
                loginFrame.pack();
                loginFrame.setLocationRelativeTo(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    private void appendNotification(String message) {
        notificationsArea.append(message + "\n");
    }

    private void appendWarning(String message) {
        notificationsArea.append("CẢNH BÁO: " + message + "\n");
        notificationsArea.setForeground(Color.RED);
    }

    public interface User32 extends com.sun.jna.Library {
        User32 INSTANCE = (User32) Native.load("user32", User32.class);
        HWND GetForegroundWindow();
        int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
    }

    class HandleClient extends Thread {
        private final ClientMonitoringUI clientUI;

        public HandleClient(ClientMonitoringUI clientUI) {
            this.clientUI = clientUI;
        }

        public void run() {
            try {
                String previousWindowTitle = "";
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                while (true) {
                    String currentWindowTitle = getActiveWindowTitle();
                    if (!currentWindowTitle.equals(previousWindowTitle) && !currentWindowTitle.isEmpty()) {
                        previousWindowTitle = currentWindowTitle;
                        String currentTime = dtf.format(LocalDateTime.now());

                        // Kiểm tra và gửi dữ liệu
                        try {
                            dout.writeUTF("Time: " + currentTime + " | Window: " + currentWindowTitle);
                        } catch (IOException ex) {
                            clientUI.appendWarning("Mất kết nối tới server, thử kết nối lại...");
                            //reconnect();  đang làm kết nối lại khi mất kết nối tuy nhiên chưa xử lý tại server
                        }
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                clientUI.appendWarning("Lỗi không xác định: Mất kết nối tới server.");
                e.printStackTrace();
            }
        }

        private String getActiveWindowTitle() {
            byte[] windowText = new byte[512];
            HWND hwnd = User32.INSTANCE.GetForegroundWindow();
            User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
            return Native.toString(windowText);
        }
    }

    public static void main(String[] args) {
    String name = args.length > 0 ? args[0] : "Unknown User";

    // Khởi tạo socket
    final Socket socket;  // Đặt socket là final
    try {
        socket = new Socket("localhost", 5001); // hoặc địa chỉ và cổng của server bạn muốn kết nối
    } catch (IOException e) {
        e.printStackTrace();
        return; // Dừng chương trình nếu không thể kết nối
    }

    // Sử dụng socket trong invokeLater
    SwingUtilities.invokeLater(() -> {
        try {
            new ClientMonitoringUI(name, socket).setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(ClientMonitoringUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
}



}
