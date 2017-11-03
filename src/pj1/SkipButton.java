/***************************************************************
* file: SkipButton.java
* author: B. Gilmartin
* class: CS 245 – Graphical User Interface
*
* assignment: program 1
* date last modified: 10/9/2017
*
* purpose: Is the Skip Button in the game part of the UI. If pressed
* the score will be 0 and newGame will be called.
*
****************************************************************/ 
package pj1;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Blake
 */
public class SkipButton extends JButton {
    
    //method: SkipButton (Constructor)
    //purpose: Create a skip button that can call newGame
    public SkipButton(ColorGamePanel colorGamePanel, HangmanFrame hangmanFrame, JPanel cardPanel, Hangman game) {

        //Sets text of the Button to be the character
        this.setText("Skip");

        //Sets size of the button
        this.setSize(70, 30);

        //sets location of the button
        this.setLocation(500, 70);
        
        this.setToolTipText("Skip to next game. You will recieve a score of 0 on this game");

        //Add ActionListener to Button
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Enter action to go to different Panel here!!!!
                //colorGamePanel.setScore(0);
                game.newGameSkip(hangmanFrame, cardPanel);
                hangmanFrame.showPanel("colorGamePanel");
            }
        });
    }    
}
