package Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketManager {
    private static Socket socket;
    private static DataOutputStream dout;
    private static DataInputStream din;

    public static void initializeConnection(String host, int port) throws IOException {
        socket = new Socket(host, port);
        dout = new DataOutputStream(socket.getOutputStream());
        din = new DataInputStream(socket.getInputStream());
    }

    public static DataOutputStream getOutputStream() {
        return dout;
    }

    public static DataInputStream getInputStream() {
        return din;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void closeConnection() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}
