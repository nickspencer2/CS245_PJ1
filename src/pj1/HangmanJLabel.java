/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import javax.swing.JLabel;

/**
 *
 * @author Blake
 */
public class HangmanJLabel extends JLabel {
    
    //Hidden Letter
    private String answerLetter;
    private boolean revealed;
    
    //Constructor that assigns the HiddenLetter
    public HangmanJLabel(String Letter) {
        answerLetter = Letter;
        revealed = false;
    }
    
    //Reveal the letter
    public void revealLetter() {
        this.setText(answerLetter);
        revealed = true;
    }
    
    //Return false if not revealed
    public boolean isFalse() {
        return revealed;
    }
    
    //return true if the Letter matches the answerLetter, else false
    public boolean isLetter(String Letter) {
        return Letter.equals(answerLetter);
    }
    
            
}
