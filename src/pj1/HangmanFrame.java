/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Nick
 */
public class HangmanFrame extends JFrame {
    private javax.swing.JPanel cardPanel;
    private IntroPanel introPanel;
    private MenuPanel menuPanel;
    
    private final boolean DEBUG = false;
    
    public HangmanFrame(){
        initComponents();
    }
    
    public void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        cardPanel = new javax.swing.JPanel(new java.awt.CardLayout());
        introPanel = new IntroPanel();
        menuPanel = new MenuPanel();
        
        cardPanel.add(introPanel, "intro");
        cardPanel.add(menuPanel, "menu");
        //cardPanel.setSize(600, 400);
        
        this.add(cardPanel);
        
        if(DEBUG){
            javax.swing.JButton testButton = new javax.swing.JButton("Test");
            menuPanel.addButton("testButton", testButton);
        }
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public void showPanel(String panelName){
        CardLayout card = (CardLayout)cardPanel.getLayout();
        card.show(cardPanel, panelName);
    }
    
    public void showAfter(String panelName, int seconds){
        Timer timer = new Timer(seconds * 1000, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                showPanel(panelName);
            }
        });
        timer.setRepeats(false);
        timer.start();
        
        //DEBUG
        int h = 0;
        int w = 0;
        System.out.println("menuPanel size: " + menuPanel.getSize());
        for(Component c : menuPanel.getComponents()){
            System.out.println("\t" + c.toString());
            h += c.getHeight();
            w += c.getWidth();
        }
        System.out.println("Total height: " + h);
        System.out.println("Total width: " + w);
        System.out.println("Frame hw: " + this.getSize());
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }
    
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
            java.util.logging.Logger.getLogger(HangmanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HangmanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HangmanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HangmanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HangmanFrame mainFrame = new HangmanFrame();
                mainFrame.setVisible(true);
                mainFrame.showPanel("intro");
                mainFrame.showAfter("menu", 3);
            }
        });
    }
}
