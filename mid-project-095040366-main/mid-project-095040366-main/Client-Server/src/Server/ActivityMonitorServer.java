package Server ;
import java.io.*;
import java.net.*;

public class ActivityMonitorServer {
    public static void main(String[] args) {
        int port = 6789; // Cổng để server lắng nghe
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            
            while (true) {
                // Lắng nghe kết nối từ client
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Tạo luồng đọc từ client
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                // Đọc và hiển thị thông tin về hoạt động của client
                String clientActivity;
                while ((clientActivity = reader.readLine()) != null) {
                    System.out.println("Client activity: " + clientActivity);
                }

                socket.close();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
