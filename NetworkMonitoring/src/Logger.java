import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String FILE_PATH = "network_log.csv";

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.append(message).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
