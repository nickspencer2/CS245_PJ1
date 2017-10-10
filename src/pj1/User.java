/***************************************************************
* file: User.java
* author: Daniel Chow
* class: CS 245 – Graphical User Interface
*
* assignment: Swing Project v1.0
* date last modified: 10/02/2017
*
* purpose: This Class is used to hold an individual's users
* name and High score. 
* 
*
****************************************************************/ 
package pj1;

import java.io.Serializable;


public class User implements Serializable{
    String name;
    int highScore;
    
    User(String name, int highScore){
        this.name = name;
        this.highScore = highScore;
    }
    @Override
    //method:toString
    //purpose: this method will return a string with both the name and score. 
    public String toString(){
        String userScore = name + " " + Integer.toString(highScore);
        return userScore;
    }
    //method:getScore
    //purpose: this method will return just the highscore of the object
    int getScore() {
        return highScore;
        
    }
    
}
