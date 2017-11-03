/***************************************************************
* file: Hangman.java
* author: B. Gilmartin
* class: CS 245 – Graphical User Interface
*
* assignment: program 1
* date last modified: 10/9/2017
*
* purpose: Panel that will be displayed on the main frame of the
* hangman game actually being played. 
*
****************************************************************/ 
package pj1;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.time.Instant.now;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author Blake
 */
public class Hangman extends javax.swing.JPanel {

    /**
     * Creates new form Hangman
     */
    
    private int score;
    private int limbCounter;
    private int hangmanDebugNum;
    
    public Hangman(HangmanFrame hangmanFrame, JPanel cardPanel, int hangmanDebugNum) {
        //set default score
        score = 100;
        
        this.hangmanDebugNum = hangmanDebugNum;
        
        //set default limb
        limbCounter = 0;
        
        //Gets word for game
        String word = getRandWord();
        System.out.println(word);
        
        //Word Length
        int wordLength = word.length();
        
        //Create panel layout
        this.setSize(600, 400);
        this.setLayout(null);
        
        //Display the current score
        JLabel currScore = new JLabel();
        currScore.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 20));
        currScore.setText("Score: " + score);
        currScore.setLocation(400, 100);
        currScore.setSize(200, 50);
        this.add(currScore);
        
        //Create the correct number of Labels for hangman
        HangmanJLabel[] labels = new HangmanJLabel[wordLength];
        
        //Create the mystery word of the hangman game
        createMysteryWord(labels, this, word);
        
        //Create a AlphaJButton Array of 26 buttons
        AlphaJButton[] Alpha = new AlphaJButton[26];
        
        //Create Alphabet Buttons
        createAlphaJButtons(Alpha, wordLength, labels, hangmanFrame.getColorGamePanel(), 
                hangmanFrame, cardPanel, currScore);
        
        this.add(new SkipButton(hangmanFrame.getColorGamePanel(), hangmanFrame, cardPanel, this));
        
        //Create and add clock
        JLabel clock = new JLabel();
        createClock(clock);
        
    }
    //method: paintComponent
    //purpose: Takes care of the graphics (The hangman and the stage as well as
    //the Hangman title.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.repaint();
        
        //Paint the Hangman Stage
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(185, 215, 415, 215);
        g2.drawLine(250, 215, 250, 80);
        g2.drawLine(250, 80, 335, 80);
        g2.drawLine(335, 80, 335, 100);
        BufferedImage image = null;
        
        //Display the title
        try {
            image = ImageIO.read(new File("Images\\Title.png"));
        } catch (IOException ex) {
            Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(image, 0, 0, this);
        
        //Display the correct body image
        BufferedImage body = null;
        try {
            body = ImageIO.read(new File("Images\\Body" + limbCounter + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(body, 292, 100, this);
        
    }
    
    
    //method: subtractScore
    //purpose: Subtract score by 10, update the score counter on the GUI
    //check if the game is over(score is no longer greater than 40).
    private boolean subtractScore(JLabel currScore) {
        score = score - 10;
        currScore.setText("Score: " + score);
        return score > 40;
    }
    
    //method: allRevealed
    //purpose: if all letters are revealed return true else return false.
    private boolean allRevealed(HangmanJLabel[] labels) {
        for(int i = 0; i < labels.length; i++) {
            if(!labels[i].isFalse()) {
                return false;
            }
        }
        return true;
    }
    
    //method: getRandWord
    //purpose: Get a random word from the words provided. 
    private String getRandWord() {
        String[] words = {"ABSTRACT", "CEMETERY", "NURSE", "PHARMACY", "CLIMBING"};
        Random rand = new Random();
        return words[rand.nextInt(5)];
    }
    
    //method: newGame
    //purpose: create a new instance of Hangman when the previous game ends and
    //remove the old Hangman from the cardPanel and replace it with the new one
    protected void newGame(HangmanFrame hangmanFrame, JPanel cardPanel) {
        cardPanel.remove(this);
        Hangman hangmanPanel = new Hangman(hangmanFrame, cardPanel, hangmanDebugNum + 1);
        hangmanFrame.setHangman(hangmanPanel);
        hangmanFrame.getColorGamePanel().setScore(score);
        hangmanFrame.addMenuPanel(hangmanPanel, "hangmanPanel", "playButton");
    }
    
    //Same as other instance except set score to zero
    protected void newGameSkip(HangmanFrame hangmanFrame, JPanel cardPanel){
        cardPanel.remove(this);
        score = 0;
        Hangman hangmanPanel = new Hangman(hangmanFrame, cardPanel, hangmanDebugNum + 1);
        hangmanFrame.setHangman(hangmanPanel);
        hangmanFrame.getColorGamePanel().setScore(score);
        hangmanFrame.addMenuPanel(hangmanPanel, "hangmanPanel", "playButton");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    //method: createMysteryWord
    //purpose: create the " _ _ _ _ _ " for the game panel
    private void createMysteryWord(HangmanJLabel[] labels, Hangman aThis, String word) {
        //Word Length
        int wordLength = word.length();
        
        //calculate label size
        int LSize = 400/wordLength;
        
        //Starting location
        int xLocation = 100;
        
        //Create a "_" that is centered for every letter of the word
        for(int i = 0; i < wordLength; i++) {
            
            //Create a HangmanJLabel that has a character in form of a string
            labels[i] = new HangmanJLabel(Character.toString(word.charAt(i)));
            
            //Set size of the JLabel
            labels[i].setSize(LSize, 30);
            
            //Set Location of JLabel on the JPanel
            labels[i].setLocation(xLocation, 240);
            
            //Set default text to _
            labels[i].setText("_");
            
            //Set to Bigger and Bolded Font
            labels[i].setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 20));
            
            //Cetners the JLabel to make look more neat and centered
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            
            //Space the size of the JLabel
            xLocation += LSize;
            
            //Add the JLabel to the JFrame
            this.add(labels[i]);
        }
    }

    //method: createAlphaJButtons
    //purpose: Create, format, and add the buttons for each letter
    //of the alphabet
    private void createAlphaJButtons(AlphaJButton[] Alpha, int wordLength,
            HangmanJLabel[] labels, ColorGamePanel colorGamePanel, 
            HangmanFrame hangmanFrame, JPanel cardPanel,JLabel currScore) {
        //Integer ascii of A, used to make alphabet
        int alphabet = 65;
        
        //Default button size
        int ButtonSize = 40;
        
        //Space between buttons
        int space = 2;
        
        //Currently contains starting position, but will change to space buttons
        int spacer = 27;
        
        //Create first 13 buttons, put in correct location, assign action
        for(int i = 0; i < 13; i++) {
            
            //Creates AlphaJButton with an alphabet character
            Alpha[i] = new AlphaJButton(Character.toString((char)alphabet));
            
            //Sets text of the Button to be the character
            Alpha[i].setText(Character.toString((char)alphabet));
            
            //Iterates the alphabet integer
            alphabet++;
            
            //Sets size of the button
            Alpha[i].setSize(ButtonSize, 30);
            
            //sets location of the button
            Alpha[i].setLocation(spacer, 300);
            
            //iterates spacer that will put the next button in the right place
            spacer = spacer + space + ButtonSize;
            
            //Add ActionListener to Button
            Alpha[i].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    
                    //change if letter button pressed is in in the word
                    boolean hit = false;
                    
                    //Disable Button after being pressed
                    AlphaJButton Button = (AlphaJButton)evt.getSource();
                    Button.setEnabled(false);
                    
                    //Check if the Letter of the button pressed is contained in
                    //letters displayed
                    for(int i = 0; i < wordLength; i++) {
                        if(labels[i].isLetter(Button.getLetter())) {
                            labels[i].revealLetter();
                            hit = true;
                        }
                    }
                    
                    //If all letters revealed go to other panel
                    if(allRevealed(labels)) {
                        hangmanFrame.getColorGamePanel().setScore(score);
                        newGame(hangmanFrame, cardPanel);
                        hangmanFrame.showPanel("colorGamePanel");
                    }
                    
                    //if not hit then subtract score and check if game over
                    if(!hit) {
                        if(!subtractScore(currScore)) {
                            hangmanFrame.getColorGamePanel().setScore(score);
                            newGame(hangmanFrame, cardPanel);
                            hangmanFrame.showPanel("colorGamePanel");
                        }
                        limbCounter++;
                    }
                }
            });
            Alpha[i].setToolTipText("Enter this letter");
            
            //add Button to panel
            this.add(Alpha[i]);
        }
        
        spacer = 27;
        
         //Create last 13 buttons, put in correct location, assign action
        for(int i = 0; i < 13; i++) {
            
            //Creates AlphaJButton with an alphabet character
            Alpha[i] = new AlphaJButton(Character.toString((char)alphabet));
            
            //Sets text of the Button to be the character
            Alpha[i].setText(Character.toString((char)alphabet));
            
            //Iterates the alphabet integer
            alphabet++;
            
            //Sets size of the button
            Alpha[i].setSize(ButtonSize, 30);
            
            //sets location of the button
            Alpha[i].setLocation(spacer, 340);
            
            //iterates spacer that will put the next button in the right place
            spacer = spacer + space + ButtonSize;
            
            //Add ActionListener to Button
            Alpha[i].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    
                    //change if letter button pressed is in in the word
                    boolean hit = false;
                    
                    //Disable Button after being pressed
                    AlphaJButton Button = (AlphaJButton)evt.getSource();
                    Button.setEnabled(false);
                    
                    //Check if the Letter of the button pressed is contained in
                    //letters displayed
                    for(int i = 0; i < wordLength; i++) {
                        if(labels[i].isLetter(Button.getLetter())) {
                            labels[i].revealLetter();
                            hit = true;
                        }
                    }
                     
                    //If all letters revealed go to other panel
                    if(allRevealed(labels)) {
                        hangmanFrame.getColorGamePanel().setScore(score);
                        newGame(hangmanFrame, cardPanel);
                        hangmanFrame.showPanel("colorGamePanel");
                    }
                    
                    //if not hit then subtract score and check if game over
                    if(!hit) {
                        if(!subtractScore(currScore)) {
                            hangmanFrame.getColorGamePanel().setScore(score);
                            newGame(hangmanFrame, cardPanel);
                            hangmanFrame.showPanel("colorGamePanel");
                        }
                        limbCounter++;
                    }
                }
            });
            Alpha[i].setToolTipText("Enter this letter");
            
            //add Button to panel
            this.add(Alpha[i]);
        }
    }

    //method: createClock
    //Creates and adds a clock to the hangman panel. The clock updates every
    //second.
    private void createClock(JLabel clock) {
        
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
        this.add(clock);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
