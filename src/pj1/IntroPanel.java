/***************************************************************
* file: IntroPanel.java
* author: Nick Spencer
* class: CS 245
*
* assignment: Project v1.2
* date last modified: 10/31/2017
*
* purpose: Class for making the intro panel for project v1.2
*
****************************************************************/ 
package pj1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick
 */
class IntroPanel extends JPanel {
    private javax.swing.JPanel bottomLabelsPane;
    
    private Map<String, JLabel> labels;
    
    private javax.swing.BoxLayout layout;
    
    private final String[] INIT_LBL_NAMES = {"groupLabel", "membersLabel", "versionLabel"};
    private final int LBL_WIDTH = 30;
    private final int LBL_HEIGHT = 10;
    private final boolean DEBUG = false;
    
    /**
     * default constructor
     */
    public IntroPanel(){
        super();//Use default constructor for JPanel
        setupProjectLabel();
        
        setupLabels();
        
        //Setup up the layout for the panel
        layout = new javax.swing.BoxLayout(this, BoxLayout.PAGE_AXIS);
        this.setLayout(layout);
    }
    
    /**
     * Set up the label that reads "Project 1"
     */
    private void setupProjectLabel(){
        labels = new HashMap<String, JLabel>();
        javax.swing.JLabel projectLabel = new javax.swing.JLabel("Project 1");
        labels.put("projectLabel", projectLabel);
        javax.swing.JPanel projectPane = new javax.swing.JPanel();
        javax.swing.BoxLayout projectPaneLayout = new javax.swing.BoxLayout(projectPane, BoxLayout.LINE_AXIS);
        projectPane.setLayout(projectPaneLayout);
        projectLabel.setFont(new Font("Arial", 0, 48));
        if(DEBUG) projectLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        projectPane.add(Box.createHorizontalGlue());
        projectPane.add(projectLabel);
        projectPane.add(Box.createHorizontalGlue());
        this.add(projectPane);
        this.add(Box.createVerticalGlue());
    }
    
    /**
     * Set up the labels and their layouts to align them correctly
     */
    private void setupLabels(){
        //create a panel for holding the bottom labels (group name, members, version)
        bottomLabelsPane = new javax.swing.JPanel();
        this.add(bottomLabelsPane);
        javax.swing.BoxLayout bottomLabelsPanelLayout = new javax.swing.BoxLayout(bottomLabelsPane, BoxLayout.PAGE_AXIS);
        bottomLabelsPane.setLayout(bottomLabelsPanelLayout);
        //create a panel for holding group label
        javax.swing.JLabel groupLabel = new javax.swing.JLabel("Group: N.B.D.");
        javax.swing.JPanel groupPane = new javax.swing.JPanel();
        javax.swing.BoxLayout groupPaneLayout = new javax.swing.BoxLayout(groupPane, BoxLayout.LINE_AXIS);
        groupPane.setLayout(groupPaneLayout);
        groupPane.add(Box.createHorizontalGlue());
        groupPane.add(groupLabel);
        groupPane.add(Box.createHorizontalGlue());
        //create a panel for holding version to right justify it
        javax.swing.JPanel lastLinePane = new javax.swing.JPanel();
        javax.swing.JLabel versionLabel = new javax.swing.JLabel("v1.2");
        javax.swing.JLabel membersLabel = new javax.swing.JLabel("  Members: Nick Spencer, Blake Gilmartin, Daniel Chow");
        javax.swing.BoxLayout versionPaneLayout = new javax.swing.BoxLayout(lastLinePane, BoxLayout.LINE_AXIS);
        lastLinePane.setLayout(versionPaneLayout);
        lastLinePane.add(Box.createHorizontalGlue());
        lastLinePane.add(membersLabel);
        lastLinePane.add(Box.createHorizontalGlue());
        lastLinePane.add(versionLabel);
        if(DEBUG) lastLinePane.setBorder(BorderFactory.createLineBorder(Color.black));
        
        labels.put("groupLabel", groupLabel);
        labels.put("membersLabel", membersLabel);
        labels.put("versionLabel", versionLabel);
        for(JLabel l : labels.values()){
            l.setFont(new Font("Arial", 0, 20));
        }
        versionLabel.setFont(new Font("Arial", 0, 10));
        bottomLabelsPane.add(groupPane);
        bottomLabelsPane.add(lastLinePane);
        labels.get("projectLabel").setFont(new Font("Arial", 0, 48));
        if(DEBUG) bottomLabelsPane.setBorder(BorderFactory.createLineBorder(Color.black));
        if(DEBUG) this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    /**
     * Methods to set the size of the panel
     * @return the dimension
     */
    public Dimension getPreferredSize() {
        return new Dimension(600, 380);
    }
    public Dimension getMinimumSize() {
        return new Dimension(600, 200);
    }
    public Dimension getMaximumSize() {
        return new Dimension(600, 380);
    }
}
