/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nicks
 */
public class Sudoku extends JPanel {
    private BoxLayout layout;
    private Map<String, JPanel> panels;
    private Map<JPanel, LayoutManager> layouts;
    private Map<String, JButton> buttons;
    private Map<String, JLabel> labels;
    private Map<String, JFormattedTextField> formattedTextFields;
    
    private final int borderWidth = 2;
    
    public Sudoku(){
        super();
        layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
        setup();
    }
    
    /**
     * Helper method to setup the components of this panel
     */
    public void setup(){
        setupPanels();
        setupLayouts();
        setupButtons();
        setupLabels();
        setupFormattedTextFields();
    }
    
    /**
     * Helper method to setup the panels, buttons, labels, etc. in this panel
     */
    private void setupPanels(){
        panels = new HashMap<>();
        JPanel topLabelsPane = new JPanel();//The pane holding the "Sudoku" label and the date and time
        JPanel bottomPane = new JPanel();//The pane holding the "Submit" button, the game panel, and the "Quit" button
        JPanel submitPane = new JPanel();//The pane holding the "Submit" button
        JPanel gamePane   = new JPanel();//The pane holding the game
        JPanel[] gameSubPanes = new JPanel[9];
        for(int i = 0; i < gameSubPanes.length; i++){
            gameSubPanes[i] = new JPanel();
            panels.put("gameSubPane" + i, gameSubPanes[i]);
            gamePane.add(gameSubPanes[i]);
        }
        JPanel quitPane   = new JPanel();//The pane holding the "Quit" button
        panels.put("topLabelsPane", topLabelsPane);
        panels.put("bottomPane", bottomPane);
        panels.put("submitPane", submitPane);
        panels.put("gamePane", gamePane);
        panels.put("quitPane", quitPane);
        this.add(topLabelsPane);
        this.add(bottomPane);
        bottomPane.add(submitPane);
        bottomPane.add(gamePane);
        bottomPane.add(quitPane);
    }
    
    /**
     * Helper method to setup the layouts for all panels in the panels map
     */
    private void setupLayouts(){
        layouts = new HashMap<>();
        for(Entry<String, JPanel>  j : panels.entrySet()){
            String key = j.getKey();
            JPanel value = j.getValue();
            if(key.equals("topLabelsPane") || key.equals("bottomPane")){
                BoxLayout jLayout = new BoxLayout(value, BoxLayout.LINE_AXIS);
                value.setLayout(jLayout);
                //value.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                layouts.put(value, jLayout);
            }
            else if(key.equals("submitPane") || key.equals("quitPane")){
                BoxLayout jLayout = new BoxLayout(value, BoxLayout.PAGE_AXIS);
                value.setLayout(jLayout);
                //value.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                layouts.put(value, jLayout);
            }
            else if(key.equals("gamePane")){
                GridLayout jLayout = new GridLayout(3, 3);
                value.setLayout(jLayout);
                value.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                layouts.put(value, jLayout);
            }
            else if(key.contains("gameSubPane")){//TODO gamesubpanes
                GridLayout jLayout = new GridLayout(3, 3);
                value.setLayout(jLayout);
                value.setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth, borderWidth, borderWidth, Color.BLACK));
                layouts.put(value, jLayout);
            }
            else{//default layout for other panels in this object
                BoxLayout jLayout = new BoxLayout(value, BoxLayout.PAGE_AXIS);
                value.setLayout(jLayout);
                //value.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                layouts.put(value, jLayout);
            }
        }
    }

    /**
     * Helper method to setup the buttons ("submit" and "quit")
     */
    private void setupButtons(){
        buttons = new HashMap<>();
        JButton submitButton = new JButton("Submit");
        JButton quitButton = new JButton("Quit");
        buttons.put("submitButton", submitButton);
        buttons.put("quitButton", quitButton);
        //add the buttons to the panels
        JPanel submitPane = panels.get("submitPane");
        submitPane.add(Box.createVerticalGlue());
        submitPane.add(submitButton);
        JPanel quitPane = panels.get("quitPane");
        quitPane.add(Box.createVerticalGlue());
        quitPane.add(quitButton);
    }
    
    /**
     * Helper method to setup the labels ("sudoku" and the date/time)
     */
    private void setupLabels(){
        labels = new HashMap<>();
        JPanel topLabelsPane = panels.get("topLabelsPane");
        JLabel sudokuLabel = new JLabel("Sudoku");
        JLabel timeLabel = new JLabel("TIMEHERE");
        labels.put("sudokuLabel", sudokuLabel);
        labels.put("timeLabel", timeLabel);
        topLabelsPane.add(sudokuLabel);
        topLabelsPane.add(Box.createHorizontalGlue());
        topLabelsPane.add(timeLabel);
    }
    
    /**
     * Helper method to setup the formatted text fields for the game
     */
    private void setupFormattedTextFields(){
        formattedTextFields = new HashMap<>();
        for(int i = 0; i < 9; i++){
            JPanel subGamePane = panels.get("gameSubPane" + i);
            for(int j = 0; j < 9; j++){
                JFormattedTextField formattedTextField = new JFormattedTextField();
                subGamePane.add(formattedTextField);
                formattedTextField.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
                formattedTextFields.put("subGamePane" + i + "formattedTextField" + j, formattedTextField);
            }
        }
    }
}
