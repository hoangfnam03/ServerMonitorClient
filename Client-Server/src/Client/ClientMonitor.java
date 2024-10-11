package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMonitor {
    public static void main(String[] args) {
        Socket socket = null;  // Định nghĩa socket bên ngoài để đóng sau
        try {
            // Kết nối tới server giám sát
            socket = new Socket("localhost", 6000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Vòng lặp để liên tục theo dõi các kết nối mạng và tiến trình
            while (true) {
                // 1. Sử dụng lệnh netstat để theo dõi các kết nối mạng hiện tại
                Process process = Runtime.getRuntime().exec("netstat -an");
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder connections = new StringBuilder();

                while ((line = input.readLine()) != null) {
                    // Tìm các kết nối đến cổng 80 (HTTP) hoặc cổng 443 (HTTPS) của các trang web
                    if (line.contains(":80") || line.contains(":443")) {
                        connections.append(line).append("\n");
                    }
                }
                input.close();

                // Nếu có kết nối mạng đến các trang web, gửi thông tin về server
                if (connections.length() > 0) {
                    out.println("Kết nối mạng đến trang web:\n" + connections.toString());
                }

                // 2. Theo dõi các tiến trình đang chạy (Windows tasklist)
                process = Runtime.getRuntime().exec("tasklist");
                input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder processList = new StringBuilder();

                while ((line = input.readLine()) != null) {
                    processList.append(line).append("\n");
                }
                input.close();

                // Gửi danh sách các tiến trình đang chạy lên server
                out.println("Các tiến trình đang chạy:\n" + processList.toString());

                // Tạm dừng 10 giây trước khi lấy lại kết nối
                Thread.sleep(10000); // 10 giây
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng socket ngay cả khi có lỗi hoặc vòng lặp kết thúc
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();  // Đóng kết nối socket sau khi hoàn thành công việc
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
