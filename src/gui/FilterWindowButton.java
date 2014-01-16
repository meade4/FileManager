package gui;

import javax.swing.JButton;

/**
 * Button used for the Filter Window. Only used to reference back to the window
 * @author meade4
 *
 */
public class FilterWindowButton extends JButton{
	FilterWindow myWindow;
	
	public FilterWindowButton(FilterWindow parentWindow, String name){
		super(name);
		myWindow = parentWindow;
	}
}
