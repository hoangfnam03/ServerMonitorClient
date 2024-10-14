package client ;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.List;

public class ClientMonitor {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Địa chỉ của server
        int port = 6789; // Cổng của server
        
        try (Socket socket = new Socket(serverAddress, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            // Giám sát các tiến trình (process)
            monitorProcesses(writer);
            
            // Giám sát hoạt động file trong một thư mục cụ thể
            monitorFileChanges(writer, Paths.get("C:/path/to/monitor")); // Thay đổi đường dẫn theo nhu cầu
            
        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
        }
    }

    // Phương thức giám sát các tiến trình (process)
    public static void monitorProcesses(PrintWriter writer) {
        List<ProcessHandle> processes = ProcessHandle.allProcesses().toList();

        for (ProcessHandle process : processes) {
            process.info().command().ifPresent(command -> {
                writer.println("Running process: " + command);
            });
        }
    }

    // Phương thức giám sát sự thay đổi file trong thư mục cụ thể
    public static void monitorFileChanges(PrintWriter writer, Path pathToMonitor) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            pathToMonitor.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;

            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path fileName = (Path) event.context();

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        writer.println("File created: " + fileName);
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        writer.println("File deleted: " + fileName);
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        writer.println("File modified: " + fileName);
                    }
                }
                key.reset(); // Reset key để tiếp tục theo dõi
            }
        } catch (IOException | InterruptedException e) {
            writer.println("Error in file monitoring: " + e.getMessage());
        }
    }
}
