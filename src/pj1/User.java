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
    public String toString(){
        String userScore = name + " " + Integer.toString(highScore);
        return userScore;
    }

    int getScore() {
        return highScore;
        
    }
    
}
