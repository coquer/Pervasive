package GUI;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JTextPane;

public class DisplayGUI 
{
    private JFrame myFrame;
    private JTextPane textDisplay;
    private String textToDisplay;
    
    	//constructor
        public DisplayGUI(String displayId, String startUpmessage) 
        {        	
        	//Initializing the layout of the program
            myFrame = new JFrame();
            myFrame.setLayout(new FlowLayout());
            myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myFrame.setSize(500, 400);
            myFrame.setTitle("Display for: " + displayId);
            
            //Initializing the text pane
            textDisplay = new JTextPane();
            textDisplay.setText(startUpmessage);
            textDisplay.setEditable(false);
            
            //add the text pane to the frame
            myFrame.add(textDisplay);
            
            //setting the JFrame to visible
            myFrame.setVisible(true);         
        }

        //returns text
		public String getTextToDisplay() {
			return this.textToDisplay;
		}

		//sets new text to display
		public void setTextToDisplay(String textToDisplay) {
			this.textToDisplay = textToDisplay;
			textDisplay.setText(textToDisplay);
		}      
}