package gui;

import javax.swing.JButton;

/**
 * JButton extension so a we can call a reference of the combo rule window
 * @author meade4
 *
 */
public class ComboRuleButton extends JButton{
	ComboRuleWindow myWindow;
		
		public ComboRuleButton(ComboRuleWindow parentWindow, String name){
			super(name);
			myWindow = parentWindow;
		}
}
