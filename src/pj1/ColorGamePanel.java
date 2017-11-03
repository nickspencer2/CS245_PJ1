/***************************************************************
* file: ColorGamePanel.java
* author: Nick Spencer and Blake Gilmartin
* class: CS 245 - GUIs
*
* assignment: Project v1.1
* date last modified: 10/19/2017
*
* purpose: this class sets up an object for the panel to display the Color Game
*
****************************************************************/ 
package pj1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author Nick
 */
public class ColorGamePanel extends JPanel implements ActionListener {
    private Map<String, JPanel> panels;
    private Map<JPanel, LayoutManager> layouts;
    private Map<String, JButton> buttons;
    private Map<String, JLabel> labels;
    
    private ImageIcon[] colorButtonImages;
    
    private Color randColor;
    private int score = 0;
    private int round;
    private int colorGameDebugNum;
    private HangmanFrame hangmanFrame;
    private Hangman hangmanPanel;
    private JPanel cardPanel;
    
    private final boolean DEBUG = false;
    private final boolean DEBUG2 = true;
    
    private final String[] COLOR_BUTTONS_NAMES = {"yellowButton", "greenButton", "purpleButton", "redButton", "blueButton"};
    private final Color[] COLORS = {Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED, Color.BLUE};
    private final String[] COLOR_NAMES = {"YELLOW", "GREEN", "PURPLE", "RED", "BLUE"};
    private final String[] COLOR_BUTTONS_IMAGE_NAMES = {"Images\\yellowbutton.jpg", "Images\\greenbutton.jpg", "Images\\purplebutton.jpg", "Images\\redbutton.jpg", "Images\\bluebutton.jpg"};
    
    /**
     * Constructor
     * @param hFrame the main frame
     * @param hsPanel the panel for displaying high scores
     * @param cPanel the Panel which manages the screens (panels)
     */
    public ColorGamePanel(HangmanFrame hFrame, JPanel cPanel, int colorGameDebugNum){
        super();
        Dimension d = new Dimension(584, 362);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
        round = 0;
        this.colorGameDebugNum = colorGameDebugNum;
        hangmanFrame = hFrame;
        cardPanel = cPanel;
        
        panels = new HashMap<>();
        layouts = new HashMap<>();
        buttons = new HashMap<>();
        labels = new HashMap<>();
        initButtonImagesArray();
        setupPanels();
        addButtonListeners();
    }
    
    /**
     * Helper method to fill in the array of images for the color buttons
     */
    private void initButtonImagesArray(){
        colorButtonImages = new ImageIcon[5];
        //colorJButton = new ColorJButton[5];
        for(int i = 0; i < colorButtonImages.length; i++){
            ImageIcon imageIcon = new ImageIcon(COLOR_BUTTONS_IMAGE_NAMES[i]); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it 
            Image newimg = image.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            imageIcon = new ImageIcon(newimg);  // transform it back
            colorButtonImages[i] = imageIcon;
        }
    }
    
    /**
     * Add color buttons in random locations
     * @param colorButtonsPane the pane which is to contain the color buttons
     * @param colorButtons the buttons to randomly place
     */
    private void addRandomColorButtons(JPanel colorButtonsPane, JButton[] colorButtons){
        Random r = new Random();
        List<JButton> randomButtons = new ArrayList<>();
        for(int i = 0; i < colorButtons.length; i++){
            randomButtons.add(colorButtons[i]);
        }
        LinkedList<int[]> colorButtonCoords = new LinkedList<>();
        int i = 0;
        while(i < 5){
            int[] coords = {r.nextInt(5), r.nextInt(5)};
            boolean colorButtonCoordsContains = false;
            if(DEBUG){
                System.out.println("Checking if " + coords[0] + ", " + coords[1] + " is in colorButtonCoords.");
            }
            for(int j = 0; j < colorButtonCoords.size(); j++){
                if((colorButtonCoords.get(j))[0] == coords[0] && (colorButtonCoords.get(j))[1] == coords[1])
                    colorButtonCoordsContains = true;
            }
            if(!colorButtonCoordsContains){
                colorButtonCoords.add(coords);
                i++;
            }
        }
        if(DEBUG){
        System.out.println("colorButtonCoords: ");
            for(int j = 0; j < colorButtonCoords.size(); j++){
                System.out.println((colorButtonCoords.get(j))[0] + ", " + (colorButtonCoords.get(j))[1]);
            }
        }
        Collections.shuffle(randomButtons);
        int k = 0;
        for(int m = 0; m < 5; m++){
            for(int j = 0; j < 5; j++){
                int[] coords = {m, j};
                boolean colorButtonCoordsContains = false;
                for(int n = 0; n < colorButtonCoords.size(); n++){
                    if((colorButtonCoords.get(n))[0] == coords[0] && (colorButtonCoords.get(n))[1] == coords[1])
                        colorButtonCoordsContains = true;
                }
                if(colorButtonCoordsContains){
                    if(DEBUG){
                        System.out.println("\tAdding colorButton at " + m + ", " + j);
                    }
                    colorButtonsPane.add(randomButtons.get(k++));
                }
                else{
                    final JLabel label = new JLabel("");
                    if(DEBUG){
                        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                    colorButtonsPane.add(label);
                }
            }
        }
    }
    
    /**
     * Helper method to setup the layout of all the panels, 
     * along with their labels and buttons
     */
    private void setupPanels(){
        Random r = new Random();
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(mainLayout);
        //Setup the panel for the label that says the current time
        JPanel timeLabelPane = new JPanel();
        JLabel timeLabel = createClock(new JLabel("TIMEHERE"));
        JLabel scoreLabel = new JLabel("Score: " + score);
        BoxLayout timeLabelLayout = new BoxLayout(timeLabelPane, BoxLayout.LINE_AXIS);
        timeLabelPane.setLayout(timeLabelLayout);
        timeLabelPane.add(scoreLabel);
        timeLabelPane.add(Box.createHorizontalGlue());
        timeLabelPane.add(timeLabel);
        layouts.put(timeLabelPane, timeLabelLayout);
        panels.put("timeLabelPane", timeLabelPane);
        labels.put("timeLabel", timeLabel);
        labels.put("scoreLabel", scoreLabel);
        //Setup the panel for the label that says the current color
        JPanel colorLabelPane = new JPanel();
        randColor = COLORS[r.nextInt(COLORS.length)];
        JLabel colorLabel = new JLabel(COLOR_NAMES[r.nextInt(COLOR_NAMES.length)]);
        colorLabel.setFont(new Font("Arial Black", 0, 25));
        colorLabel.setForeground(randColor);
        BoxLayout colorLabelLayout = new BoxLayout(colorLabelPane, BoxLayout.LINE_AXIS);
        colorLabelPane.setLayout(colorLabelLayout);
        colorLabelPane.add(Box.createHorizontalGlue());
        colorLabelPane.add(colorLabel);
        colorLabelPane.add(Box.createHorizontalGlue());
        layouts.put(colorLabelPane, colorLabelLayout);
        panels.put("colorLabelPane", colorLabelPane);
        labels.put("colorLabel", colorLabel);
        //Setup the panel for the buttons
        JPanel colorButtonsPane = new JPanel();
        JButton yellowButton = new JButton(colorButtonImages[0]);
        JButton greenButton = new JButton(colorButtonImages[1]);
        JButton purpleButton = new JButton(colorButtonImages[2]);
        JButton redButton = new JButton(colorButtonImages[3]);
        JButton blueButton = new JButton(colorButtonImages[4]);
        JButton[] colorButtons = {yellowButton, greenButton, purpleButton, redButton, blueButton};
        //colorButtonAddAction(colorButtons, colorLabel);
        GridLayout colorButtonsPaneLayout = new GridLayout(5, 5);
        colorButtonsPane.setLayout(colorButtonsPaneLayout);
        addRandomColorButtons(colorButtonsPane, colorButtons);
        layouts.put(colorButtonsPane, colorButtonsPaneLayout);
        panels.put("colorButtonsPane", colorButtonsPane);
        for(int i = 0; i < colorButtons.length; i++){
            buttons.put(COLOR_BUTTONS_NAMES[i], colorButtons[i]);
        }
        //Add the colorLabelPane and colorButtonsPane to this panel
        this.add(timeLabelPane);
        this.add(colorLabelPane);
        this.add(Box.createVerticalGlue());
        this.add(colorButtonsPane);
    }
    
    /**
     * Adds this as a listener to the button with matching name 
     * (requires the button to be in the buttons field [map])
     * @param buttonName the name of the button to add a listener to
     */
    private void addButtonListener(String buttonName){
        buttons.get(buttonName).addActionListener(this);
    }
    
    /**
     * Adds a listener for all the color buttons whose names are in the COLOR_BUTTONS_NAMES field
     */
    private void addButtonListeners(){
        for(String s : COLOR_BUTTONS_NAMES){
            addButtonListener(s);
        }
    }
    
    //method: createClock
    //Creates and adds a clock to the panel. The clock updates every
    //second.
    private JLabel createClock(JLabel clock) {
        
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MMMMMMMMMMMM dd, yyyy  HH:mm:ss");
        
        clock.setHorizontalAlignment(JLabel.CENTER);
        clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 16));
        Timer time = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                Date currTime = calendar.getTime();
                clock.setText(dateTimeFormat.format(currTime));
                //clock.setText(DateFormat.getTimeInstance().format(new Date()));
                
            }
        });
        time.setRepeats(true);
        time.setCoalesce(true);
        time.setInitialDelay(0);
        time.start();
        
        clock.setLocation(350, 0);
        clock.setSize(250, 40);
        return clock;
    }

    /**
     * Returns whether or not the button is in the buttons map
     * @param button the button
     * @param buttons the button map
     * @return true if the button is in the buttons map, false otherwise
     */
    public boolean in(Object button, Map<String, JButton> buttons){
        for(JButton jb : buttons.values()){
            if(jb.equals(button)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the entry in the button map matching the button
     * @param button the button to find
     * @return the entry in the button map matching the button
     */
    public Entry<String, JButton> findButtonEntry(Object button){
        for(Entry<String, JButton> jb : buttons.entrySet()){
            if(jb.getValue().equals(button)){
                return jb;
            }
        }
        return null;
    }
    
    /**
     * Returns the color associated with the button
     * @param buttonName the name of the button
     * @return the color of the button
     */
    public Color findButtonColor(String buttonName){
        for(int i = 0; i < COLOR_BUTTONS_NAMES.length; i++){
            if(COLOR_BUTTONS_NAMES[i].equals(buttonName)){
                return COLORS[i];
            }
        }
        return null;
    }
    
    /**
     * Rearranges the color buttons
     */
    public void shuffleGamePanel(){
        JPanel colorButtonsPane = panels.get("colorButtonsPane");
        Component[] cBPComponents = colorButtonsPane.getComponents();
        List<Component> cBPComponentsList = Arrays.asList(cBPComponents);
        Collections.shuffle(cBPComponentsList);
        Component[] cBPNewComponents = cBPComponentsList.toArray(new Component[0]);
        //Reset panel
        for(Component c : cBPComponents){
            colorButtonsPane.remove(c);
        }
        //Add new random buttons
        for(Component c : cBPNewComponents){
            colorButtonsPane.add(c);
        }
    }
    
    /**
     * Listener for this object
     * @param e the ActionEvent generated
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Entry<String, JButton> buttonEntry = findButtonEntry((Component)e.getSource());
        if(DEBUG2){
            System.out.println("e.getSource = " + e.getSource());
            System.out.println("Action = " + e.paramString());
        }
        if(buttonEntry == null) return;
        String buttonName = buttonEntry.getKey();
        Color buttonColor = findButtonColor(buttonName);
        JLabel colorLabel = labels.get("colorLabel");
        Random r = new Random();
        if(DEBUG2){
            System.out.println("Button's color = " + buttonColor);
            System.out.println("Color label's color = " + randColor);
        }
        if( randColor.equals(buttonColor) ) {
            score += 100;
            labels.get("scoreLabel").setText("Score: " + score);
            System.out.println("(In ColorGamePanel.actionPerformed) Got one right! Score is now = " + score);
        }
        if(DEBUG2){
            System.out.println("-----------------------");
        }
        round++;
        if(round < 5) {
            randColor = COLORS[r.nextInt(COLORS.length)];
            colorLabel.setForeground(randColor);
            colorLabel.setText(COLOR_NAMES[r.nextInt(COLOR_NAMES.length)]);
        } else {
            hangmanFrame.getSudoku().setScoreSudoku(score);
            newGame(hangmanFrame, cardPanel);
            hangmanFrame.showPanel("sudokuPanel");
        }
        if( in(e.getSource(), buttons) ){
            shuffleGamePanel();
        }
        //TODO listening code here
    }
    
    /**
     * method: newGame
     * function: Remove game from the cardPanel. Create new color game and 
     * add it to the cardPanel.
     * @param hangmanFrame
     * @param cardPanel
     * @param highScorePanel 
     */
    protected void newGame(HangmanFrame hangmanFrame, JPanel cardPanel) {
        cardPanel.remove(this);
        ColorGamePanel colorGamePanel = new ColorGamePanel(hangmanFrame, cardPanel, colorGameDebugNum + 1);
        hangmanFrame.setColorGamePanel(colorGamePanel);
        hangmanFrame.addMenuPanel(colorGamePanel, "colorGamePanel");
    }

    /**
     * Set score to i
     * @param i 
     */
    void setScore(int i) {
        score = i;
        if(DEBUG2){
            System.out.println("CGP " + colorGameDebugNum + "Score set to " + i);
        }
        labels.get("scoreLabel").setText("Score = " + score);
    }
    
}
