/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nick
 */
public class ColorGamePanel extends JPanel {
    private Map<String, JPanel> panels;
    private Map<JPanel, BoxLayout> layouts;
    private Map<String, JButton> buttons;
    private Map<String, JLabel> labels;
    
    private ImageIcon[] colorButtonImages;
    
    private final String[] colorButtonsNames = {"yellowButton", "greenButton", "purpleButton", "redButton", "blueButton"};
    private final Color[] colors = {Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED, Color.BLUE};
    private final String[] colorNames = {"YELLOW", "GREEN", "PURPLE", "RED", "BLUE"};
    private final String[] colorButtonImageNames = {"Images\\yellowbutton.jpg", "Images\\greenbutton.jpg", "Images\\purplebutton.jpg", "Images\\redbutton.jpg", "Images\\bluebutton.jpg"};
    
    public ColorGamePanel(HangmanFrame hangmanFrame, JPanel cardPanel){
        super();
        panels = new HashMap<>();
        layouts = new HashMap<>();
        buttons = new HashMap<>();
        labels = new HashMap<>();
        initButtonImagesArray();
        setupPanels();
    }
    
    private void initButtonImagesArray(){
        colorButtonImages = new ImageIcon[5];
        for(int i = 0; i < colorButtonImages.length; i++){
            colorButtonImages[i] = new ImageIcon(colorButtonImageNames[i]);
        }
    }
    
    private void addRandomColorButtons(JPanel colorButtonsPane, JButton[] colorButtons){
        List<JButton> randomButtons = new ArrayList<>();
        for(int i = 0; i < colorButtons.length; i++){
            randomButtons.add(colorButtons[i]);
        }
        Collections.shuffle(randomButtons);
        for(int i = 0; i < randomButtons.size(); i++){
            colorButtonsPane.add(Box.createHorizontalGlue());
            colorButtonsPane.add(randomButtons.get(i));
            colorButtonsPane.add(Box.createHorizontalGlue());
        }
    }
    
    private void setupPanels(){
        Random r = new Random();
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(mainLayout);
        //Setup the panel for the label that says the current time
        JPanel timeLabelPane = new JPanel();
        JLabel timeLabel = new JLabel("TIMEHERE");
        BoxLayout timeLabelLayout = new BoxLayout(timeLabelPane, BoxLayout.LINE_AXIS);
        timeLabelPane.setLayout(timeLabelLayout);
        timeLabelPane.add(Box.createHorizontalGlue());
        timeLabelPane.add(timeLabel);
        layouts.put(timeLabelPane, timeLabelLayout);
        panels.put("colorLabelPane", timeLabelPane);
        labels.put("colorLabel", timeLabel);
        //Setup the panel for the label that says the current color
        JPanel colorLabelPane = new JPanel();
        JLabel colorLabel = new JLabel(colorNames[r.nextInt(colorNames.length)]);
        colorLabel.setFont(new Font("Arial Black", 0, 25));
        colorLabel.setForeground(colors[r.nextInt(colors.length)]);
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
        BoxLayout colorButtonsPaneLayout = new BoxLayout(colorButtonsPane, BoxLayout.LINE_AXIS);
        colorButtonsPane.setLayout(colorButtonsPaneLayout);
        addRandomColorButtons(colorButtonsPane, colorButtons);
        layouts.put(colorButtonsPane, colorButtonsPaneLayout);
        panels.put("colorButtonsPane", colorButtonsPane);
        for(int i = 0; i < colorButtons.length; i++){
            buttons.put(colorButtonsNames[i], colorButtons[i]);
        }
        //Add the colorLabelPane and colorButtonsPane to this panel
        this.add(timeLabelPane);
        this.add(colorLabelPane);
        this.add(Box.createVerticalGlue());
        this.add(colorButtonsPane);
    }
    
    
}
