package Client;


import Client.Login;
import Account.AccountManager;
public class LoginAndSignUp {


    public static void main(String[] args) {
        
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null); 
    }
    
}