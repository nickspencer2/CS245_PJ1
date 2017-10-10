/***************************************************************
* file: AlphaJButton.java
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

import javax.swing.JButton;

/**
 *
 * @author Blake
 */
public class AlphaJButton extends JButton {
    
    //Letter of the alphabet
    private String Letter;
    
    //method: AlphaJButton (Constructor)
    //purpose: Assigns the letter of the alphabet
    public AlphaJButton(String alpha) {
        Letter = alpha;
    }
    
    //method: getLetter
    //purpose: return Letter
    public String getLetter() {
        return Letter;
    }
    
}
