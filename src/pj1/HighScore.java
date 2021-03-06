/***************************************************************
* file: HighScore.java
* author: Daniel Chow
* class: CS 245 – Graphical User Interface
*
* assignment: Swing Project v1.0
* date last modified: 10/17/2017
*
* purpose: This Class is a panel for the HighScore page. It will 
* handle storing the Highscores in a Serialized file. It will handle
* the name input for new High Scores. It will display the top 5 high
* scores as well as the name of the high score holders. 
* 
*
****************************************************************/ 
package pj1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;



public class HighScore extends javax.swing.JPanel implements Serializable{

    private String name;
    File f;
    private List<User> scores;
    private Boolean bHS;
    private int index;
    private int score;
    private final int SIZE = 5;
    HangmanFrame hf;
    public HighScore(HangmanFrame hf) throws FileNotFoundException, IOException, ClassNotFoundException {
        name = null;
        bHS = false;
        index = 0;
        score = 0; 
        this.hf = hf;
        f = new File("highscore.ser");
        
        scores = new ArrayList<>();
        
        if(f.exists()&&!f.isDirectory()){
           FileInputStream fis = new FileInputStream(f);
           ObjectInputStream ois = new ObjectInputStream(fis);
           scores = (ArrayList) ois.readObject();
                     
            
        }
        else{
            try (FileOutputStream fos = new FileOutputStream("highscore.ser"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                reset();
                oos.writeObject(scores);
                oos.flush();
            }
        }
       
        initComponents();
       setHighScores(); 
       
        
        
    }
    //method:setScore
    //purpose: this method will add the name and score to the high score  list if it is a new highscore.
    public boolean setScore(String name){
         if(bHS == true){
            
            User nu = new User(name, score);
            
            scores.remove(SIZE-1);
            scores.add(index, nu);
           
            saveScore();
            
        }
        
        
        bHS = false;
        return true;

    }
    
    //method: isHighScore
    //purpose: this method checks if a score is in the top 5 of high scores. 
    //         if it is, it will return true and remove the lowest high score 
    //         and add the new highscore into the arraylist. 
    public boolean isHighScore(int score) {
        
       jButton1.setText("End");
       JOptionPane.showMessageDialog(null , "Your score is: " + score);
        for(int i = 0; i < scores.size(); i++){
            if(score >= scores.get(i).getScore()){
                
               bHS = true;
               index = i;
               this.score = score;
               
               return true;
            }

        }
         bHS = true;
               index = scores.size();
               this.score = score;
        
        
        
        return false;

    }   
    //method:getHighScore
    //purpose: this method will return a string array with the current names and highscores on the leaderboard.
    public String[] getHighScore() {
        String[] ret = new String[SIZE];
        for(int i = 0; i < SIZE; i++){
            ret[i] = " ";
        }
        for(int i = 0; i < scores.size(); i++){
            ret[i] = scores.get(i).toString();
        }
        return ret;
    }
    //method:setHighScores
    //purpose: This method will update the names and scores on the highscore panel. 
     public void setHighScores(){
      
        String[] highScores = getHighScore();
        setJLabel2(highScores[0]); 
        jButton2.setToolTipText("Reset HighScores");
        jButton1.setToolTipText("Return to Main Menue");
        jTextField1.setToolTipText("Enter Name Here");
        setJLabel3(highScores[1]);
        setJLabel4(highScores[2]);
        setJLabel5(highScores[3]);
        setJLabel6(highScores[4]);
    }
    //method:saveScore
    //purpose: This method will write the arraylist scores into the serialized file. 
      public Boolean saveScore(){
        
        try (FileOutputStream fos = new FileOutputStream("highscore.ser"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(scores);
                oos.flush();
            } catch (IOException ex) {
            
        }
        return true;
    }
    //method:reset
    //purpose: This method will reset the scores and names in the arraylist. 
    public void reset(){
        scores = new ArrayList<>();
        scores.add(new User("ABC", 0));
        scores.add(new User("ABC", 0));
        scores.add(new User("ABC", 0));
        scores.add(new User("ABC", 0));
        scores.add(new User("ABC", 0));
        
        saveScore();
    }
    //method:setJLabel2-6
    //purpose: these 5 methods will modify each label to display the correct name and score. 
        public void setJLabel2(String label){
        jLabel2.setText(label);
    }
    public void setJLabel3(String label){
        jLabel3.setText(label);
    }
    public void setJLabel4(String label){
        jLabel4.setText(label);
    }
    public void setJLabel5(String label){
        jLabel5.setText(label);
    }
    public void setJLabel6(String label){
        jLabel6.setText(label);
    }
    //method:getName
    //purpose: This method is a getter for the variable name. 
    public String getName(){
        
        String nameReturn = name;
        
        name = null;
        return nameReturn;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setRequestFocusEnabled(false);

        jLabel1.setText("High Score");

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jLabel5.setText("jLabel5");

        jLabel6.setText("jLabel6");

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("Enter your name");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset Scores");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(8, 8, 8))))
            .addGroup(layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jTextField1.getAccessibleContext().setAccessibleName("inputName");
    }// </editor-fold>                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
       name = jTextField1.getText();
       jTextField1.setText(" ");  
       setScore(name);
       setHighScores();
  
    }                                           

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        hf.showPanel("menu");
        jButton1.setText("Back");
        
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        reset();
        setHighScores();
        saveScore();
    }                                        


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}
