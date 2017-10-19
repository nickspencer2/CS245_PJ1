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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private HighScore highScorePanel;
    private HangmanFrame hangmanFrame;
    private JPanel cardPanel;
    
    private final boolean DEBUG = false;
    
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
    public ColorGamePanel(HangmanFrame hFrame, HighScore hsPanel, JPanel cPanel){
        super();
        round = 0;
        highScorePanel = hsPanel;
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
            Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
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
        BoxLayout timeLabelLayout = new BoxLayout(timeLabelPane, BoxLayout.LINE_AXIS);
        timeLabelPane.setLayout(timeLabelLayout);
        timeLabelPane.add(Box.createHorizontalGlue());
        timeLabelPane.add(timeLabel);
        layouts.put(timeLabelPane, timeLabelLayout);
        panels.put("timeLabelPane", timeLabelPane);
        labels.put("timeLabel", timeLabel);
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
        colorButtonAddAction(colorButtons, colorLabel);
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
    //Creates and adds a clock to the hangman panel. The clock updates every
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
     * Listener for this object
     * @param e the ActionEvent generated
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(DEBUG){
            if(e.getSource() == buttons.get("yellowButton")){
                System.out.println("Yellow button clicked!");
            }
        }
        //TODO listening code here
    }
    
    private void colorButtonAddAction(JButton[] colorButtons, JLabel colorLabel) {
        Random r = new Random();
        for(int i = 0; i < 5; i++) {
            Color buttonColor = COLORS[i];
            colorButtons[i].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    if(randColor.equals(buttonColor)) {
                        score += 100;
                        System.out.println(score);
                    }
                    round++;
                    if(round < 5) {
                        randColor = COLORS[r.nextInt(COLORS.length)];
                        colorLabel.setForeground(randColor);
                        colorLabel.setText(COLOR_NAMES[r.nextInt(COLOR_NAMES.length)]);
                    } else {
                        highScorePanel.isHighScore(score);
                        newGame(hangmanFrame, cardPanel, highScorePanel);
                        hangmanFrame.showPanel("highScorePanel");
                    }
                }
            });
        }
    }
    
    protected void newGame(HangmanFrame hangmanFrame, JPanel cardPanel, HighScore highScorePanel) {
        cardPanel.remove(this);
        ColorGamePanel colorGamePanel = new ColorGamePanel( hangmanFrame, highScorePanel, cardPanel);
        hangmanFrame.addMenuPanel(colorGamePanel, "colorGamePanel");
        
    }

    void setScore(int i) {
        score = i;
    }
    
}
