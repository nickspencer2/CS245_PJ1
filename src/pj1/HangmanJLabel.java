/***************************************************************
* file: HangmanJLable.java
* author: B. Gilmartin
* class: CS 245 – Graphical User Interface
*
* assignment: program 1
* date last modified: 10/9/2017
*
* purpose: JLabel that has a hidden letter that will be revealed
* when the user presses the correct button.
*
****************************************************************/ 
package pj1;

import javax.swing.JLabel;

/**
 *
 * @author Blake
 */
public class HangmanJLabel extends JLabel {
    
    //Hidden Letter
    private String answerLetter;
    
    //is this letter revealed
    private boolean revealed;
    
    //method: HangmanJLabel (Constructor)
    //purpose: Constructor that assigns the HiddenLetter
    public HangmanJLabel(String Letter) {
        answerLetter = Letter;
        revealed = false;
    }
    
    //method: revealLetter
    //purpose: Reveal the letter and set revealed to true.
    public void revealLetter() {
        this.setText(answerLetter);
        revealed = true;
    }
    
    //method: isFalse
    //purpose: return revealed (true if HangmanJLabel is revealed).
    public boolean isFalse() {
        return revealed;
    }
    
    //method: isLetter
    //return if the hidden letter is equal to the Letter entered
    public boolean isLetter(String Letter) {
        return Letter.equals(answerLetter);
    }
    
            
}
