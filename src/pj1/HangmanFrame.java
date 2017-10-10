/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Nick
 */
public class HangmanFrame extends JFrame {
    private javax.swing.JPanel cardPanel;
    private IntroPanel introPanel;
    private MenuPanel menuPanel;
    private Credit creditPanel;
    private HighScore highScorePanel;
    private Hangman hangmanPanel;
    
    private final boolean DEBUG = false;
    
    /**
     * Default constructor
     */
    public HangmanFrame(){
        initComponents();
    }
    
    /**
     * Set up the components in the frame
     */
    public void initComponents(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //Add the intro and main menu to the card layout to be able to switch between panels
        cardPanel = new javax.swing.JPanel(new java.awt.CardLayout());
        introPanel = new IntroPanel();
        menuPanel = new MenuPanel(cardPanel);
        creditPanel = new Credit(this);
        try {
            highScorePanel = new HighScore(this);
        } catch (IOException ex) {
            Logger.getLogger(HangmanFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HangmanFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        hangmanPanel = new Hangman(highScorePanel, this, cardPanel);
        cardPanel.add(hangmanPanel, "playButton");
        cardPanel.add(introPanel, "intro");
        cardPanel.add(creditPanel, "creditsButton");
        cardPanel.add(highScorePanel, "highscoresButton");
        cardPanel.add(menuPanel, "menu");
        this.add(cardPanel);
        addMenuPanel(hangmanPanel, "hangmanPanel", "playButton");
        addMenuPanel(highScorePanel, "highScorePanel", "highscoresButton");
        addMenuPanel(creditPanel, "creditPanel", "creditsButton");
        
        if(DEBUG){
            //Testing adding a new button
            javax.swing.JButton testButton = new javax.swing.JButton("Test");
            menuPanel.addButton("testButton", testButton);
            //To see what buttons we can link with screens
            System.out.println("Button names: " + Arrays.toString(menuPanel.getButtonNames()));
            //Make a panel for the "testButton" to link to
            JPanel testPanel = new JPanel();
            //Add a label to it to make sure we're viewing the correct screen
            testPanel.add(new JLabel("Test Screen!"));
            //Test the addMenuPanel method, which links a button on the menu panel to a new jpanel
            addMenuPanel(testPanel, "testPanel", "testButton");
            
        }
        pack();
        setLocationRelativeTo(null);//Center the frame
    }
    
    /**
     * Links a button to a screen, when it's pressed that screen will show
     * @param panel the panel you wish to link a menu button to
     * @param panelName the name to associate with the panel
     * @param buttonName the name of the button to link the panel to; use menuPanel.getButtonNames() to see the possible buttons
     */
    public void addMenuPanel(JPanel panel, String panelName, String buttonName){
        cardPanel.add(panel, panelName);
        menuPanel.setButtonScreen(panelName, buttonName);
    }
    
    /**
     * Show a panel
     * @param panelName the name of the panel to show
     */
    public void showPanel(String panelName){
        CardLayout card = (CardLayout)cardPanel.getLayout();
        card.show(cardPanel, panelName);
    }
    
    /**
     * Show a panel after a delay
     * @param panelName the name of the panel to show
     * @param seconds the amount of time to wait before switching to the screen in seconds
     */
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
    
    /**
     * The dimensions we prefer the frame to be
     * @return the dimensions
     */
    //public Dimension getPreferredSize() {
    //    return new Dimension(600, 400);
    //}
    
    //Set up look and feel of the panel and create a new HangmanFrame
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
