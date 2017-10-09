/***************************************************************
* file: MenuPanel.java
* author: Nick Spencer
* class: CS 245
*
* assignment: Project v1.0
* date last modified: 10/7/2017
*
* purpose: Class for making a menu panel for project v1.0
*
****************************************************************/ 
package pj1;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nick
 */
public class MenuPanel extends JPanel {
    private javax.swing.BoxLayout layout;//Layout for the menu panel
    private javax.swing.JLabel titleLabel;//Label with string "Menu"
    private javax.swing.JPanel bottomPane;
    private javax.swing.JPanel cardPanel;
    
    private Map<String, javax.swing.JButton> buttons;//Map containing the buttons in the menu panel
    private javax.swing.JPanel buttonsPane;
    
    private final String[] INIT_BTN_NAMES = {"playButton", "highscoresButton", "creditsButton"};
    private final boolean DEBUG = false;
    
    /**
     * Constructor
     * @param cardPanel the cardPanel that switches between panels. Used so the buttons on this panel can switch to other panels.
     */
    public MenuPanel(javax.swing.JPanel cardPanel){
        super();//Use the default constructor for JPanel
        this.cardPanel = cardPanel;
        buttons = new HashMap<String, JButton>();
        //Setup the components for the panel
        setupTitleLabel();
        setupButtons();
        
        //Setup up the layout for the panel
        layout = new javax.swing.BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
        
        this.setSize(600, 380);
    }
    
    /**
     * @return The preferred size for the menu panel
     */
    public Dimension getPreferredSize() {
        return new Dimension(600, 380);
    }
    public Dimension getMinimumSize() {
        return new Dimension(600, 200);
    }
    public Dimension getMaximumSize() {
        return new Dimension(600, 380);
    }
    
    /**
     * 
     * @return An array of the names (Strings) for each button
     */
    public String[] getButtonNames(){
        return buttons.keySet().toArray(new String[1]);
    }
    
    /**
     * 
     * @param buttonName the name of the button to be returned
     * @return the button matching the name passed, null if it's not in the panel 
     */
    protected JButton getButton(String buttonName){
        return buttons.get(buttonName);
    }
    
    /**
     * 
     * @param evt the event that triggered this method
     * @param panelName the name of the panel to be shown when the event occurs
     */
    private void setScreen(java.awt.event.MouseEvent evt, String panelName){
        CardLayout card = (CardLayout)cardPanel.getLayout();
        card.show(cardPanel, panelName);
    }
    
    /**
     * 
     * @param buttonName the name of the button to link to the screen
     * @param screen the panel the button should lead to
     */
    protected void setButtonScreen(String panelName, String buttonName){
        JButton b = buttons.get(buttonName);
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                setScreen(evt, panelName);
            }
        });
    }

    /**
     * A private method for encapsulation which  sets the property for the "Menu" Label
     */
    private void setupTitleLabel() {
        javax.swing.JPanel titlePane = new javax.swing.JPanel();
        javax.swing.BoxLayout titlePaneLayout = new javax.swing.BoxLayout(titlePane, BoxLayout.LINE_AXIS);
        titlePane.setLayout(titlePaneLayout);
        titleLabel = new javax.swing.JLabel("Menu");
        titleLabel.setFont(new java.awt.Font("Charlemagne std", 0, 48));
        titlePane.add(Box.createHorizontalGlue());
        titlePane.add(titleLabel);
        titlePane.add(Box.createHorizontalGlue());
        this.add(titlePane);
        this.add(Box.createVerticalGlue());
    }
    
    /**
     * Add a button with associated name
     * @param buttonName the name to associate with the button
     * @param button the button to add
     */
    protected void addButton(String buttonName, JButton button){
        javax.swing.JPanel buttonPane = new javax.swing.JPanel();
        javax.swing.BoxLayout buttonLayout = new javax.swing.BoxLayout(buttonPane, BoxLayout.LINE_AXIS);
        buttonPane.setLayout(buttonLayout);
        buttonPane.add(Box.createHorizontalGlue());
        button.setFont(new java.awt.Font("Arial", 0, 15));
        buttonPane.add(button);
        if(DEBUG) buttonPane.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonsPane.add(buttonPane);
        buttons.put(buttonName, button);
        buttonsPane.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
    /**
     * A method to setup the icon for the menu panel
     */
    private void setupIcon(){
        if(DEBUG){
            System.out.println("Working Directory = " +
                  System.getProperty("user.dir"));
        }
        BufferedImage myPicture = null;
        try{
            myPicture = ImageIO.read(new File("Images/nbd_icon.jpg"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(100, 100, Image.SCALE_FAST)));
        bottomPane.add(picLabel);
    }

    /**
     * A private method for encapsulation which creates the buttons in a hashmap, sets their preferred sizes, and adds them to this panel
     */
    private void setupButtons() {
        //Create a pane for the bottom half of the main panel
        bottomPane = new javax.swing.JPanel();
        this.add(bottomPane);
        javax.swing.BoxLayout bottomPaneLayout = new javax.swing.BoxLayout(bottomPane, BoxLayout.LINE_AXIS);
        bottomPane.setLayout(bottomPaneLayout);
        setupIcon();
        //Add a filler to push the buttons pane to the right side
        bottomPane.add(Box.createHorizontalGlue());
        if(DEBUG) bottomPane.setBorder(BorderFactory.createLineBorder(Color.black));
        //Setup a separate panel for the buttons
        buttonsPane = new javax.swing.JPanel();
        bottomPane.add(buttonsPane);
        javax.swing.BoxLayout buttonsPaneLayout = new javax.swing.BoxLayout(buttonsPane, BoxLayout.PAGE_AXIS);
        buttonsPane.setLayout(buttonsPaneLayout);
        //Add a filler to push the buttons to the bottom, now the buttons are in the bottom right corner
        buttons.put("playButton", new javax.swing.JButton("Play"));
        buttons.put("highscoresButton", new javax.swing.JButton("Highscores"));
        buttons.put("creditsButton", new javax.swing.JButton("Credits"));
        for(String s : INIT_BTN_NAMES){
            javax.swing.JButton b = buttons.get(s);
            addButton(s, b);
        }
        if(DEBUG) buttonsPane.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
