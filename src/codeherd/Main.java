/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package codeherd;

import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author BagusThanatos
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    JFileChooser jF ;
    File file=null;
    boolean saved = true;
    Client c=null;
    Server s=null;
    boolean input=false;
    public Main() {
        initComponents();
        jF = new JFileChooser();
        
       
        text.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (saved) saved= false;
                if (s!=null) {
                    s.sendData(text.getText());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (saved) saved= false;
                if (s!=null) {
                    s.sendData(text.getText());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuFileNew = new javax.swing.JMenuItem();
        menuFileOpen = new javax.swing.JMenuItem();
        menuFileSave = new javax.swing.JMenuItem();
        menuFileSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuFilePageSetup = new javax.swing.JMenuItem();
        menuFilePrint = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuFileExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        menuHost = new javax.swing.JMenuItem();
        menuRemote = new javax.swing.JMenuItem();
        menuDisconnect = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CodeHerd");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        text.setColumns(20);
        text.setRows(5);
        jScrollPane1.setViewportView(text);

        jMenu1.setText("File");

        menuFileNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuFileNew.setText("New");
        menuFileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileNewActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileNew);

        menuFileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuFileOpen.setText("Open");
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileOpenActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileOpen);

        menuFileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuFileSave.setText("Save");
        menuFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileSaveActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileSave);

        menuFileSaveAs.setText("Save As");
        menuFileSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileSaveAsActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileSaveAs);
        jMenu1.add(jSeparator1);

        menuFilePageSetup.setText("Page Setup");
        jMenu1.add(menuFilePageSetup);

        menuFilePrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuFilePrint.setText("Print");
        jMenu1.add(menuFilePrint);
        jMenu1.add(jSeparator3);

        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuFileExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Remote");

        menuHost.setText("Host");
        menuHost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHostActionPerformed(evt);
            }
        });
        jMenu3.add(menuHost);

        menuRemote.setText("Remote");
        menuRemote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRemoteActionPerformed(evt);
            }
        });
        jMenu3.add(menuRemote);

        menuDisconnect.setText("Disconnect/Stop Hosting");
        menuDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDisconnectActionPerformed(evt);
            }
        });
        jMenu3.add(menuDisconnect);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed
        // TODO add your handling code here:
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_menuFileExitActionPerformed

    private void menuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileOpenActionPerformed
        if (!saved){
            int v =JOptionPane.showConfirmDialog(this, "Do you want to save?");
            if(v==JOptionPane.OK_OPTION){
                menuFileSaveActionPerformed(evt);
            }
            else if (v==JOptionPane.CANCEL_OPTION) return;
        }
        int v = jF.showOpenDialog(this);
        if (v== JFileChooser.APPROVE_OPTION){
            this.file = jF.getSelectedFile();
            updateText();
            saved = true;
        }
        
    }//GEN-LAST:event_menuFileOpenActionPerformed

    private void menuFileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileSaveActionPerformed
        if (file!=null){
            try {
                BufferedWriter b = new BufferedWriter(new FileWriter(file));
                b.write(text.getText());
                b.flush();
                saved = true;
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else {
            int v = jF.showSaveDialog(this);
            if (v== JFileChooser.APPROVE_OPTION){
                file = jF.getSelectedFile();
                try {
                    BufferedWriter b = new BufferedWriter(new FileWriter(file));
                    b.write(text.getText());
                    b.flush();
                    
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                saved = true;
            }
            else return;
        }
    }//GEN-LAST:event_menuFileSaveActionPerformed

    private void menuFileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileNewActionPerformed
        if (!saved){
            int v =JOptionPane.showConfirmDialog(this, "Do you want to save?");
            if(v==JOptionPane.OK_OPTION){
                menuFileSaveActionPerformed(evt);
            }
            else if (v==JOptionPane.CANCEL_OPTION) return;
        }
        text.setText("");
        saved=true;
        file=null;
        
    }//GEN-LAST:event_menuFileNewActionPerformed

    private void menuFileSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileSaveAsActionPerformed
        menuFileSaveActionPerformed(evt);
    }//GEN-LAST:event_menuFileSaveAsActionPerformed

    private void menuHostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHostActionPerformed
        if (input) return;
        if (c!= null){
            JOptionPane.showMessageDialog(this, "Anda sedang terkoneksi ke dalam sebuah server.");
        }
        else if (s!=null){
            JOptionPane.showMessageDialog(this, "Anda sudah dalam kondisi hosting");
        }
        else {
            new InputHostName(this,s).setVisible(true);
            input=true;
            
        }
    }//GEN-LAST:event_menuHostActionPerformed

    private void menuRemoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRemoteActionPerformed
        if (input) return;
        if (c!= null){
            JOptionPane.showMessageDialog(this, "Anda sedang terkoneksi ke dalam sebuah server.");
        }
        else if (s!=null){
            JOptionPane.showMessageDialog(this, "Anda sudah dalam kondisi hosting");
        }
        else {
            new ConnToServer(this, c).setVisible(true);
            input=true;
            
        }
    }//GEN-LAST:event_menuRemoteActionPerformed

    private void menuDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDisconnectActionPerformed
        int val = JOptionPane.showConfirmDialog(this, "Warning!!\nJika anda mengklik YES, maka anda akan putus dari"
                + " Server atau Stop Hosting");
        if (val ==JOptionPane.YES_OPTION) {
            if (s!= null){
                s.sendData(Server.DISCONNECT);
                s.stopListening();
                
            }
            if (c!= null){
                c.disconnect();
                
            }
            s=null;
            c=null;
            this.setTitle("CodeHerd");
            text.setEditable(true);
        }
    }//GEN-LAST:event_menuDisconnectActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (c!= null){
            c.disconnect();
        }
        if (s!= null){
            s.sendData(Server.DISCONNECT);
        }
    }//GEN-LAST:event_formWindowClosing
    public void setInputFlag(boolean i){
        this.input=i;
    }
    public void setText(String s){
        this.text.setText(s);
    }
    public void setServer(Server s){
        this.s= s;
        input=false;
        if (s!=null) {
            this.c=null;
            s.listenToReqs();
            this.setTitle("CodeHerd Server: "+s.getName());
            text.setEditable(true);
        }
        else {
            this.setTitle("CodeHerd");
            text.setEditable(true);
        }
        
    }
    public void setClient (Client c){
        this.c=c;
        input =false;
        if (c!=null) {
            if (s!=null) {
                s.stopListening();
                this.s=null;
            }
            c.connect(this);
            this.setTitle("CodeHerd Client: Connected to "+c.getServerIP().toString());
            text.setEditable(false);
        }
        else {
            this.setTitle("CodeHerd");
            text.setEditable(true);
        }
    }
    private void updateText(){
        BufferedReader b= null;
        try {
            b = new BufferedReader(new FileReader(file));
            String n;
            String a="";
            n= b.readLine();
            while (n!=null){
               a+=n+"\n";
               n=b.readLine();
            }
            text.setText(a);
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                b.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem menuDisconnect;
    private javax.swing.JMenuItem menuFileExit;
    private javax.swing.JMenuItem menuFileNew;
    private javax.swing.JMenuItem menuFileOpen;
    private javax.swing.JMenuItem menuFilePageSetup;
    private javax.swing.JMenuItem menuFilePrint;
    private javax.swing.JMenuItem menuFileSave;
    private javax.swing.JMenuItem menuFileSaveAs;
    private javax.swing.JMenuItem menuHost;
    private javax.swing.JMenuItem menuRemote;
    private javax.swing.JTextArea text;
    // End of variables declaration//GEN-END:variables
}
