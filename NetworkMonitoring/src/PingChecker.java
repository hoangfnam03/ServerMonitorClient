import java.io.IOException;
import java.net.InetAddress;

public class PingChecker {
    public static void checkPing(String ipAddress) {
        try {
            InetAddress inet = InetAddress.getByName(ipAddress);
            System.out.println("Sending Ping Request to " + ipAddress);
            if (inet.isReachable(5000)) {
                System.out.println(ipAddress + " is reachable.");
                Logger.log(ipAddress + " is reachable.");
            } else {
                System.out.println(ipAddress + " is not reachable.");
                Logger.log(ipAddress + " is not reachable.");
                //EmailAlert.sendAlert("Ping failed for " + ipAddress);
                System.out.println("Ping failed for" + ipAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
