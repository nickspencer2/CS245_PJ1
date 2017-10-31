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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Map<String, Integer> answers;
    private List<String> deducted;
    private HangmanFrame hangmanFrame;
    private JPanel cardPanel;
    private HighScore highscorePanel;
    private int score;
    private int notAgain;
    
    private final int borderWidth = 2;
    
    private final boolean DEBUG = true;
    
    public Sudoku(HangmanFrame hmFrame, JPanel cPanel, HighScore hsPanel){
        super();
        score = 540;
        notAgain = 0;
        hangmanFrame = hmFrame;
        cardPanel = cPanel;
        highscorePanel = hsPanel;
        layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
        setup();
        createAnswers();
    }
    
    /**
     * Helper method to setup the components of this panel
     */
    public void setup(){
        deducted = new ArrayList<>();
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
        
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Object[] keys = formattedTextFields.keySet().toArray();
                boolean complete = true;
                String key;
                for(int i = 0; i < keys.length; i++) {
                    key = keys[i].toString();
                    if(answers.containsKey(key)) {
                        if(!formattedTextFields.get(key).getText().toString().equals("")) {
                            if(answers.get(key) != Integer.parseInt(formattedTextFields.get(key).getText())) {
                                if(!deducted.contains(key)) {
                                    System.out.println(key);
                                    score -= 10;
                                    deducted.add(key);
                                }
                                complete = false;
                            }
                        }
                        if(formattedTextFields.get(key).getText().toString().equals("")) {
                            complete = false;
                        }
                    }
                }
                System.out.println("Score: " + score);
                if(complete) {
                    newGame(hangmanFrame, cardPanel, highscorePanel);
                    hangmanFrame.showPanel("highScorePanel");
                }
            }
        });
        
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //Enter action to go to different Panel here!!!!
                //colorGamePanel.setScore(0);
                newGame(hangmanFrame, cardPanel, highscorePanel);
                hangmanFrame.showPanel("highScorePanel");
            }
        });
        
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
                        String isNull = "";
                        try{
                            intValue = Integer.parseInt(source.getText());
                            isNull = source.getText();
                        }
                        catch(NumberFormatException ex){
                            isNull = source.getText();
                            source.setValue("");
                            source.setText("");
                        }
                        if(DEBUG){
                            System.out.println("intValue2 = " + intValue);
                        }
                        if(!(intValue >= 1 && intValue <= 9)){
                            if(!isNull.equals("")) {
                                JOptionPane.showMessageDialog(hangmanFrame , "This is not a good value!!!");
                            }
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
        String isNull = "";
        try{
            intValue = Integer.parseInt(source.getText());
            isNull = source.getText();
        }
        catch(NumberFormatException ex){
            isNull = source.getText();
            source.setValue("");
            source.setText("");
        }
        if(DEBUG){
            System.out.println("intValue = " + intValue);
        }
        if(!(intValue >= 1 && intValue <= 9)){
            source.setValue("");
            source.setText("");
            if(!isNull.equals("")) {
                JOptionPane.showMessageDialog(hangmanFrame , "This is not a good value!!!11331");
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
        formattedTextFields.get("subGamePane2formattedTextField2").setText("7");
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
        formattedTextFields.get("subGamePane2formattedTextField2").setEnabled(false);
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

    private void createAnswers() {
        answers = new HashMap<>();
        
        answers.put("subGamePane0formattedTextField1", 3);
        answers.put("subGamePane0formattedTextField2", 5);
        answers.put("subGamePane0formattedTextField3", 2);
        answers.put("subGamePane0formattedTextField4", 9);
        answers.put("subGamePane0formattedTextField5", 6);
        answers.put("subGamePane0formattedTextField6", 4);
        answers.put("subGamePane0formattedTextField8", 7);
        
        answers.put("subGamePane1formattedTextField1", 1);
        answers.put("subGamePane1formattedTextField3", 8);
        answers.put("subGamePane1formattedTextField4", 5);
        answers.put("subGamePane1formattedTextField5", 7);
        answers.put("subGamePane1formattedTextField6", 2);
        answers.put("subGamePane1formattedTextField7", 9);
        answers.put("subGamePane1formattedTextField8", 3);
        
        answers.put("subGamePane2formattedTextField0", 9);
        answers.put("subGamePane2formattedTextField1", 2);
        answers.put("subGamePane2formattedTextField4", 3);
        answers.put("subGamePane2formattedTextField5", 1);
        answers.put("subGamePane2formattedTextField8", 8);
        
        answers.put("subGamePane3formattedTextField1", 6);
        answers.put("subGamePane3formattedTextField3", 1);
        answers.put("subGamePane3formattedTextField4", 2);
        answers.put("subGamePane3formattedTextField5", 3);
        answers.put("subGamePane3formattedTextField6", 7);
        
        answers.put("subGamePane4formattedTextField0", 1);
        answers.put("subGamePane4formattedTextField2", 4);
        answers.put("subGamePane4formattedTextField3", 6);
        answers.put("subGamePane4formattedTextField5", 8);
        answers.put("subGamePane4formattedTextField6", 5);
        answers.put("subGamePane4formattedTextField8", 9);
        
        answers.put("subGamePane5formattedTextField2", 2);
        answers.put("subGamePane5formattedTextField3", 5);
        answers.put("subGamePane5formattedTextField4", 4);
        answers.put("subGamePane5formattedTextField5", 9);
        answers.put("subGamePane5formattedTextField7", 6);
        
        answers.put("subGamePane6formattedTextField0", 6);
        answers.put("subGamePane6formattedTextField3", 9);
        answers.put("subGamePane6formattedTextField4", 8);
        answers.put("subGamePane6formattedTextField7", 7);
        answers.put("subGamePane6formattedTextField8", 4);
        
        answers.put("subGamePane7formattedTextField0", 7);
        answers.put("subGamePane7formattedTextField1", 8);
        answers.put("subGamePane7formattedTextField2", 1);
        answers.put("subGamePane7formattedTextField3", 3);
        answers.put("subGamePane7formattedTextField4", 4);
        answers.put("subGamePane7formattedTextField5", 5);
        answers.put("subGamePane7formattedTextField7", 6);
        
        answers.put("subGamePane8formattedTextField0", 3);
        answers.put("subGamePane8formattedTextField2", 4);
        answers.put("subGamePane8formattedTextField3", 2);
        answers.put("subGamePane8formattedTextField4", 7);
        answers.put("subGamePane8formattedTextField5", 6);
        answers.put("subGamePane8formattedTextField6", 8);
        answers.put("subGamePane8formattedTextField7", 1);
        
        Object[] answersKeys = answers.keySet().toArray();
        String answersKey;
        for(int i = 0; i < answersKeys.length; i++) {
            answersKey = answersKeys[i].toString();
            
        }
    }
    
    protected void newGame(HangmanFrame hangmanFrame, JPanel cardPanel, HighScore hsPanel) {
        cardPanel.remove(this);
        Sudoku sudokuPanel = new Sudoku(hangmanFrame, cardPanel, hsPanel);
        hangmanFrame.addMenuPanel(sudokuPanel, "sudokuPanel");    
    }
    
    public void setScoreSudoku(int prevScore) {
        score += prevScore;
    }
    
}
