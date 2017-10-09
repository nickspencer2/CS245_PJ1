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
public class SkipButton extends JButton {
    
    public SkipButton() {

        //Sets text of the Button to be the character
        this.setText("Skip");

        //Sets size of the button
        this.setSize(70, 30);

        //sets location of the button
        this.setLocation(500, 70);

        //Add ActionListener to Button
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Enter action to go to different Panel here!!!!
            }
        });
    }    
}
