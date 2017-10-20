/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
            if(key.equals("topLabelsPane") || key.equals("bottomPane")){
                BoxLayout jLayout = new BoxLayout(j.getValue(), BoxLayout.LINE_AXIS);
                j.getValue().setLayout(jLayout);
                layouts.put(j.getValue(), jLayout);
            }
            else if(key.equals("submitPane") || key.equals("quitPane")){
                BoxLayout jLayout = new BoxLayout(j.getValue(), BoxLayout.PAGE_AXIS);
                j.getValue().setLayout(jLayout);
                layouts.put(j.getValue(), jLayout);
            }
            else if(key.equals("gamePane")){
                GridLayout jLayout = new GridLayout(3, 3);
                j.getValue().setLayout(jLayout);
                layouts.put(j.getValue(), jLayout);
            }
            else if(key.contains("gameSubPane")){//TODO gamesubpanes
                GridLayout jLayout = new GridLayout(3, 3);
                j.getValue().setLayout(jLayout);
                layouts.put(j.getValue(), jLayout);
            }
            else{//default layout for other panels in this object
                BoxLayout jLayout = new BoxLayout(j.getValue(), BoxLayout.PAGE_AXIS);
                j.getValue().setLayout(jLayout);
                layouts.put(j.getValue(), jLayout);
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
}
