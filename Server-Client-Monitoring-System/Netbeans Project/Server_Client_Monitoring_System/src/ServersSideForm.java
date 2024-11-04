import Account.AccountManager;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;

public class ServersSideForm extends javax.swing.JFrame {
    
    ServerSocket ss;
    Socket s;
    DataOutputStream doutMain;
    DataInputStream dinMain;

    public ServersSideForm() {
        initComponents();
        this.getContentPane().setBackground(new Color(233, 210, 180));
    }

    public class HandleServer extends Thread {
        public void run() {
            try {            
                ss = new ServerSocket(5001);
                while (true) {
                    s = ss.accept();
                    dinMain = new DataInputStream(s.getInputStream());
                    doutMain = new DataOutputStream(s.getOutputStream());

                    String username = dinMain.readUTF();
                    String password = dinMain.readUTF();
                    System.out.println(username);
                    System.out.println(password);

                    AccountManager accountManager = new AccountManager();
                    if (accountManager.checkCredentials(username, password)) {
                        String name = accountManager.getNameFromUsername(username);
                        doutMain.writeUTF("SUCCESS");
                        textStatus.setText(textStatus.getText() + "\n<" + name + " Connected >");

                        new StartSending(name, doutMain, dinMain).start();
                        new StartReceiving(name, doutMain, dinMain).start();
                    } else {
                        doutMain.writeUTF("FAILURE");
                        s.close();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could Not Establish server");
                ServerHome h = new ServerHome();
                new ServersSideForm().setVisible(false);
                h.setVisible(true);
            }
        }

        public class StartSending extends Thread {
            public String name;
            public DataOutputStream dout;
            public DataInputStream din;
            public StartSending(String name, DataOutputStream dout, DataInputStream din) {
                this.name = name;
                this.dout = dout;
                this.din = din;
            }
            public void run() {
                String str;
                while (true) {
                    str = msg_area.getText();
                    try {
                        dout.writeUTF(str);
                    } catch (IOException ex) {
                        Logger.getLogger(ServersSideForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServersSideForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        public class StartReceiving extends Thread {
            public String name;
            public DataOutputStream dout;
            public DataInputStream din;
            public StartReceiving(String name, DataOutputStream dout, DataInputStream din) {
                this.name = name;
                this.dout = dout;
                this.din = din;
            }
            public void run() {
                String str;
                while (true) {
                    try {
                        str = din.readUTF();
                        System.out.println(str);
                        if (str.equals("Exit")) {
                            textStatus.setText(textStatus.getText().trim() + "\n<" + name + " Disconnected>");
                            break;
                        } else {
                            textStatus.setText(textStatus.getText().trim() + "\n" + name + " :- " + str);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServersSideForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void go() {
        this.setVisible(true);
        textStatus.setText("<Server Started>");
        new HandleServer().start();
    }

    private void msg_areaKeyPressed(java.awt.event.KeyEvent evt) {
        // Code xử lý sự kiện nhấn phím trong msg_area
    }

    private void msg_areaKeyReleased(java.awt.event.KeyEvent evt) {
        // Code xử lý sự kiện nhả phím trong msg_area
    }

    private void msg_areaKeyTyped(java.awt.event.KeyEvent evt) {
        // Code xử lý sự kiện gõ phím trong msg_area
    }

    private void msg_exitActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            s.close();
            ss.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(ServersSideForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fontSliderPropertyChange(java.beans.PropertyChangeEvent evt) {
        int fontSize = fontSlider.getValue();
        msg_area.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int fontSize = fontSlider.getValue();
        msg_area.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(msg_area.getText());
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // Code để gửi mail (yêu cầu bổ sung thêm các chi tiết về thư mục mail nếu có)
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        // Mã tạo GUI cho JFrame
    }
    // </editor-fold>                        
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JSlider fontSlider;
    private javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_exit;
    private javax.swing.JTextArea textStatus;
    private javax.swing.JLabel textStatus2;
    // End of variables declaration                   
}
