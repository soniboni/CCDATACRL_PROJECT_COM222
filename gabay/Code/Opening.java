/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gabay.Code;

import Abstractions.PageLoader;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author gelyn
 */
public class Opening extends javax.swing.JFrame {
    
    /**
     * Creates new form Opening
     */
    public Opening() {
        initComponents();
        setLocationRelativeTo(null);
        setBounds(0, 0, 430, 930);
        this.getContentPane().setBackground(Color.WHITE);
        animationButton.setBackground(Color.WHITE);
        startLabel.setVisible(false);
        
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the label visible after the specified delay (3 seconds)
                startLabel.setVisible(true);
            }
        });

        // Start the timer
        timer.setRepeats(false); // Set to false to run only once
        timer.start();


    }
    private void setIcon() {
        // Replace "path/to/your/icon.png" with the actual path to your icon file
        ImageIcon icon = new ImageIcon("D:\\Bonnie\\NU\\NU SY 2023-2024\\1st TERM\\DSA\\GABAY\\src\\Assets.Logo\\Logo.png");
        
        setIconImage(icon.getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        startLabel = new javax.swing.JLabel("", SwingConstants.CENTER);
        animationButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GABAY");
        setResizable(false);
        getContentPane().setLayout(null);

        startLabel.setFont(new java.awt.Font("Arial Nova", 0, 12)); // NOI18N
        startLabel.setForeground(new java.awt.Color(255, 102, 0));
        startLabel.setText("press anywhere to start booking");
        startLabel.setBounds(0, 800, 430, 20);
        getContentPane().add(startLabel);

        animationButton.setIcon(new ImageIcon("src/Anime/GABAY - Copy/src/Assets.Animation/OpeningTransparent.gif")); // NOI18N
        animationButton.setText("jButton1");
        animationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animationButtonActionPerformed(evt);
            }
        });
        animationButton.setBounds(0, -20, 430, 930);
        getContentPane().add(animationButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void animationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animationButtonActionPerformed
        this.dispose();
        PageLoader.getInstance();
    }//GEN-LAST:event_animationButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Opening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Opening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Opening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Opening.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Opening().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton animationButton;
    private javax.swing.JLabel startLabel;
    // End of variables declaration//GEN-END:variables
}