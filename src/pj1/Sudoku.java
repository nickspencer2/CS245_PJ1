/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pj1;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 *
 * @author nicks
 */
public class Sudoku extends JPanel implements PropertyChangeListener{
    private BoxLayout layout;
    private Map<String, JPanel> panels;
    private Map<JPanel, LayoutManager> layouts;
    private Map<String, JButton> buttons;
    private Map<String, JLabel> labels;
    private Map<String, JFormattedTextField> formattedTextFields;
    private HangmanFrame hangmanFrame;
    
    private final int borderWidth = 2;
    
    private final boolean DEBUG = true;
    
    public Sudoku(HangmanFrame hmFrame){
        super();
        hangmanFrame = hmFrame;
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
        initGameLayout();
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
                JFormattedTextField formattedTextField = new JFormattedTextField("");
                formattedTextField.setHorizontalAlignment(JTextField.CENTER);
                formattedTextField.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 26));
                formattedTextField.setText("");
                formattedTextField.setValue("");
                formattedTextField.setColumns(1);
                formattedTextField.addPropertyChangeListener("value", this);
                formattedTextField.addFocusListener(new FocusListener(){
                    @Override
                    public void focusGained(FocusEvent e) {}
                    @Override
                    public void focusLost(FocusEvent e) {
                        JFormattedTextField source = (JFormattedTextField) e.getSource();
                        int intValue = 0;
                        try{
                            intValue = Integer.parseInt(source.getText());
                        }
                        catch(NumberFormatException ex){
                            source.setValue("");
                            source.setText("");
                        }
                        if(DEBUG){
                            System.out.println("intValue2 = " + intValue);
                        }
                        if(!(intValue >= 1 && intValue <= 9)){
                            if(DEBUG){
                                System.out.println("setting text to ''");
                            }
                            source.setValue("");
                            source.setText("");
                        }
                        else{
                            source.setValue(intValue);
                            source.setText(source.getValue().toString());
                        }
                    }
                    
                });
                subGamePane.add(formattedTextField);
                formattedTextField.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
                formattedTextFields.put("subGamePane" + i + "formattedTextField" + j, formattedTextField);
            }
        }
        if(DEBUG){
            for(String s : formattedTextFields.keySet()){
                System.out.println(s);
            }
        }
    }

    /**
     * listener for the formatted text fields
     * @param e the event that triggered this method
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        JFormattedTextField source = (JFormattedTextField) e.getSource();
        int intValue = 0;
        try{
            intValue = Integer.parseInt(source.getText());
        }
        catch(NumberFormatException ex){
            source.setValue("");
            source.setText("");
        }
        if(DEBUG){
            System.out.println("intValue = " + intValue);
        }
        if(!(intValue >= 1 && intValue <= 9)){
            source.setValue("");
            source.setText("");
            if(intValue != 0) {
                JOptionPane.showMessageDialog(hangmanFrame , "This is not a good value!!!");
            }
        }
        else{
            source.setValue(intValue);
            source.setText(source.getValue().toString());
        }
        /*if(DEBUG){
            System.out.println("sg2ftf2 value = " + getFormattedTextFieldValue("subGamePane2formattedTextField2"));
        }*/
    }
    
    public int getFormattedTextFieldValue(String formattedTextFieldName){
        int value;
        try{
            value = Integer.parseInt(formattedTextFields.get(formattedTextFieldName).getValue().toString());
        }
        catch(NumberFormatException nfe){
            return 0;
        }
        return value;
    }

    private void initGameLayout() {
        
        formattedTextFields.get("subGamePane0formattedTextField0").setText("8");
        formattedTextFields.get("subGamePane0formattedTextField7").setText("1");
        formattedTextFields.get("subGamePane1formattedTextField0").setText("4");
        formattedTextFields.get("subGamePane1formattedTextField2").setText("6");
        formattedTextFields.get("subGamePane2formattedTextField3").setText("7");
        formattedTextFields.get("subGamePane2formattedTextField3").setText("4");
        formattedTextFields.get("subGamePane2formattedTextField6").setText("6");
        formattedTextFields.get("subGamePane2formattedTextField7").setText("5");
        formattedTextFields.get("subGamePane3formattedTextField0").setText("5");
        formattedTextFields.get("subGamePane3formattedTextField2").setText("9");
        formattedTextFields.get("subGamePane3formattedTextField7").setText("4");
        formattedTextFields.get("subGamePane3formattedTextField8").setText("8");
        formattedTextFields.get("subGamePane4formattedTextField1").setText("3");
        formattedTextFields.get("subGamePane4formattedTextField4").setText("7");
        formattedTextFields.get("subGamePane4formattedTextField7").setText("2");
        formattedTextFields.get("subGamePane5formattedTextField0").setText("7");
        formattedTextFields.get("subGamePane5formattedTextField1").setText("8");
        formattedTextFields.get("subGamePane5formattedTextField6").setText("1");
        formattedTextFields.get("subGamePane5formattedTextField8").setText("3");
        formattedTextFields.get("subGamePane6formattedTextField1").setText("5");
        formattedTextFields.get("subGamePane6formattedTextField2").setText("2");
        formattedTextFields.get("subGamePane6formattedTextField5").setText("1");
        formattedTextFields.get("subGamePane6formattedTextField6").setText("3");
        formattedTextFields.get("subGamePane7formattedTextField6").setText("9");
        formattedTextFields.get("subGamePane7formattedTextField8").setText("2");
        formattedTextFields.get("subGamePane8formattedTextField1").setText("9");
        formattedTextFields.get("subGamePane8formattedTextField8").setText("5");
        
        formattedTextFields.get("subGamePane0formattedTextField0").setEnabled(false);
        formattedTextFields.get("subGamePane0formattedTextField7").setEnabled(false);
        formattedTextFields.get("subGamePane1formattedTextField0").setEnabled(false);
        formattedTextFields.get("subGamePane1formattedTextField2").setEnabled(false);
        formattedTextFields.get("subGamePane2formattedTextField3").setEnabled(false);
        formattedTextFields.get("subGamePane2formattedTextField3").setEnabled(false);
        formattedTextFields.get("subGamePane2formattedTextField6").setEnabled(false);
        formattedTextFields.get("subGamePane2formattedTextField7").setEnabled(false);
        formattedTextFields.get("subGamePane3formattedTextField0").setEnabled(false);
        formattedTextFields.get("subGamePane3formattedTextField2").setEnabled(false);
        formattedTextFields.get("subGamePane3formattedTextField7").setEnabled(false);
        formattedTextFields.get("subGamePane3formattedTextField8").setEnabled(false);
        formattedTextFields.get("subGamePane4formattedTextField1").setEnabled(false);
        formattedTextFields.get("subGamePane4formattedTextField4").setEnabled(false);
        formattedTextFields.get("subGamePane4formattedTextField7").setEnabled(false);
        formattedTextFields.get("subGamePane5formattedTextField0").setEnabled(false);
        formattedTextFields.get("subGamePane5formattedTextField1").setEnabled(false);
        formattedTextFields.get("subGamePane5formattedTextField6").setEnabled(false);
        formattedTextFields.get("subGamePane5formattedTextField8").setEnabled(false);
        formattedTextFields.get("subGamePane6formattedTextField1").setEnabled(false);
        formattedTextFields.get("subGamePane6formattedTextField2").setEnabled(false);
        formattedTextFields.get("subGamePane6formattedTextField5").setEnabled(false);
        formattedTextFields.get("subGamePane6formattedTextField6").setEnabled(false);
        formattedTextFields.get("subGamePane7formattedTextField6").setEnabled(false);
        formattedTextFields.get("subGamePane7formattedTextField8").setEnabled(false);
        formattedTextFields.get("subGamePane8formattedTextField1").setEnabled(false);
        formattedTextFields.get("subGamePane8formattedTextField8").setEnabled(false);
        
    }
}
