
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
import jdk.internal.net.http.websocket.Transport;
public class ServersSideForm extends javax.swing.JFrame {

    ServerSocket ss;
    Socket s;
    DataOutputStream doutMain;
    DataInputStream dinMain;

     public ServersSideForm() {
        initComponents();
        this.getContentPane().setBackground(new Color( 233, 210, 180 ));
    }
     
     
   
    public class HandleServer extends Thread{
        
        public void run(){
            String str;
            try{            
                    ss=new ServerSocket(5001);
                    while(true){
                        s=ss.accept();
                        dinMain=new DataInputStream(s.getInputStream());
                        doutMain= new DataOutputStream(s.getOutputStream());
                        
                        String username = dinMain.readUTF();
                        String password = dinMain.readUTF();
                        
                        AccountManager accountManager = new AccountManager();
                        if (accountManager.checkCredentials(username, password)) {
                            String name = accountManager.getNameFromUsername(username);
                            doutMain.writeUTF(name);
                            textStatus.setText(textStatus.getText() + "\n<" + name + " Connected >");

                            new StartSending(name, doutMain, dinMain).start();
                            new StartReceiving(name, doutMain, dinMain).start();

                        } else {
                            doutMain.writeUTF("FAILURE");
                            s.close();
                        }
                    }
            }
            catch(Exception e){
                try{
                    JOptionPane.showMessageDialog(null, "Could Not Establish server");
                    ServerHome h=new ServerHome();
                    new ServersSideForm().setVisible(false);
                    h.setVisible(true);
                }catch(Exception e2){

                }
            }
        }
        
        public class StartSending extends Thread{
            public String name;
            public DataOutputStream dout;
            public DataInputStream din;
            public StartSending(String name,DataOutputStream dout,DataInputStream din){
                this.name=name;
                this.dout=dout;
                this.din=din;
            }
            public void run(){
                String str="";
                while(true){
                    str=msg_area.getText();
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

        public class StartReceiving extends Thread{
            public String name;
            public DataOutputStream dout;
            public DataInputStream din;
            public StartReceiving(String name,DataOutputStream dout,DataInputStream din){
                this.name=name;
                this.dout=dout;
                this.din=din;
            }
            public void run(){
                String str="";
                while(true){
                    try {
                        str=din.readUTF();
                        if(str.equals("Exit")){
                            textStatus.setText(textStatus.getText().trim()+"\n<"+name+" Disconnected>");
                            this.finalize();
                        }
                        else{
                            textStatus.setText(textStatus.getText().trim()+"\n"+name+" :- "+str);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ServersSideForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Throwable ex) {
                        JOptionPane.showMessageDialog(null, "Couldnt Stop the thread");
                    }

                }
            }
        }
    }
    
    public void go(){
        this.setVisible(true);
        textStatus.setText("<Server Started>");
        new HandleServer().start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_exit = new javax.swing.JButton();
        textStatus2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fontSlider = new javax.swing.JSlider();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        textStatus = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        msg_area.setColumns(20);
        msg_area.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        msg_area.setRows(5);
        msg_area.setBorder(new javax.swing.border.MatteBorder(null));
        msg_area.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        msg_area.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                msg_areaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                msg_areaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                msg_areaKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(msg_area);

        msg_exit.setBackground(new java.awt.Color(255, 102, 102));
        msg_exit.setText("Exit");
        msg_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msg_exitActionPerformed(evt);
            }
        });

        textStatus2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        textStatus2.setForeground(new java.awt.Color(51, 102, 0));
        textStatus2.setText("Connected");
        textStatus2.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Server Notepad");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
        textStatus.setFont(new java.awt.Font("MS Gothic", 0, 14)); // NOI18N
        textStatus.setRows(5);
        jScrollPane3.setViewportView(textStatus);

        jButton2.setText("Save as Text File");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Send Mail");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(fontSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textStatus2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fontSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(msg_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textStatus2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void msg_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msg_exitActionPerformed
        if(JOptionPane.showConfirmDialog(null,"All clients will be disconnected.Do you want to exit?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE)==0){
            System.exit(0); 
        }
                   
    }//GEN-LAST:event_msg_exitActionPerformed

    private void msg_areaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msg_areaKeyTyped

    }//GEN-LAST:event_msg_areaKeyTyped

    private void msg_areaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msg_areaKeyReleased

    }//GEN-LAST:event_msg_areaKeyReleased

    private void msg_areaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_msg_areaKeyPressed
       
    }//GEN-LAST:event_msg_areaKeyPressed

    private void fontSliderPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fontSliderPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_fontSliderPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int size=fontSlider.getValue();
        msg_area.setFont(new Font("Times New Roman",Font.PLAIN,size));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
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
                fout = new FileWriter(str+"/ank.txt");
                    try (PrintWriter p = new PrintWriter(fout)) {
                        String words[]=msg_area.getText().split("\n");
                        
                        for(String word : words){
                            p.println(word);
                        }   }
                fout.close();
                JOptionPane.showMessageDialog(null, "File Successfully written");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ServersSideForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServersSideForm.class.getName()).log(Level.SEVERE, null, ex);
            }
              }
            else {
              JOptionPane.showMessageDialog(null,"No Selection ");
              }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        // TODO add your handling code here:
//        
//        textStatus2.setForeground(Color.green);
//        textStatus2.setText("...Sending mail this may take few seconds");
//        String to=JOptionPane.showInputDialog("Enter Email Id : ");
//        String content=msg_area.getText();              //change accordingly 
//        //Get the session object  
//        Properties props = new Properties();  
//        props.put("mail.smtp.host", "smtp.gmail.com");  
//        props.put("mail.smtp.socketFactory.port", "465");  
//        props.put("mail.smtp.socketFactory.class",  
//                  "javax.net.ssl.SSLSocketFactory");  
//        props.put("mail.smtp.auth", "true");  
//        props.put("mail.smtp.port", "465");  
//
//         
//        Session session = Session.getDefaultInstance(props,  
//         new javax.mail.Authenticator() {  
//         protected javax.mail.PasswordAuthentication getPasswordAuthentication() {  
//         return new javax.mail.PasswordAuthentication("clientservermonitoringsystem@gmail.com","csmscsms");//change accordingly  
//         }  
//        });
//        
//        //compose message  
//        try {  
//            MimeMessage message = new MimeMessage(session);  
//            message.setFrom(new InternetAddress("clientservermonitoringsystem@gmail.com"));//change accordingly  
//            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
//            message.setSubject("Server Client Monitoring System mail");  
//            message.setText(content);  
//
//            //send message  
//            Transport.send(message);  
//            textStatus2.setText("...Mail Sent Successfully");
//            JOptionPane.showMessageDialog(null, "Mail sent successfully"); 
//            textStatus.setText(textStatus.getText().trim()+"\n<Mail Sent to "+to+">");
//
//        } catch (MessagingException e) {
//            textStatus2.setForeground(Color.red);
//            textStatus2.setText("...unsuccessful ");
//            JOptionPane.showMessageDialog(null, "Couldnt send mail, check for connections or enter valid email id");
//            throw new RuntimeException(e);
//        }  
// 
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(ServersSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServersSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServersSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServersSideForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServersSideForm().go();
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JSlider fontSlider;
    javax.swing.JButton jButton1;
    javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    javax.swing.JTextArea msg_area;
    javax.swing.JButton msg_exit;
    javax.swing.JTextArea textStatus;
    javax.swing.JLabel textStatus2;
    // End of variables declaration//GEN-END:variables
}
