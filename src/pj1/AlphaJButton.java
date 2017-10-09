/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import javax.swing.JButton;

/**
 *
 * @author Blake
 */
public class AlphaJButton extends JButton {
    
    //Letter of the alphabet
    private String Letter;
    
    //Assigns the letter of the alphabet
    public AlphaJButton(String alpha) {
        Letter = alpha;
    }
    
    //return Letter
    public String getLetter() {
        return Letter;
    }
    
}
