package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * New Window that appears when desinating a filter for a rule
 * @author meade4
 *
 */
public class FilterWindow {
	JFrame mainFrame;
	JPanel defJP;
	JPanel CenterJP;
	JComboBox<String> Filter;
	JComboBox<String> greaterThan;
	FilterWindowButton enter;
	JTextArea Criteria;
	JLabel RuleText;
	
	ActionListener filterListener = new ActionListener() {
		// action listener needed because the second combo box will change
		// depending on the first
		public void actionPerformed(ActionEvent e) {
			mainFrame.setVisible(false);
			JComboBox<String> cb = (JComboBox<String>) e.getSource();
			String Filter = (String) cb.getSelectedItem();
			if (Filter.equals("Type")) {
				setTypeView();
			}

			if (Filter.equals("Size")) {
				setSizeView();
			}

			if (Filter.equals("Name Contains")) {
				setNameView();
			}
		}

		/**
		 * adjusts the screen for use when creating a name filter
		 */
		private void setNameView() {
			if (RuleText.getParent() != null)
				CenterJP.remove(RuleText);
			if (greaterThan.getParent() != null)
				CenterJP.remove(greaterThan);
			RuleText = new JLabel("Enter the name to filter by");
			CenterJP.add(RuleText, BorderLayout.NORTH);
			if (Criteria != null)
				CenterJP.remove(Criteria);

			Criteria = new JTextArea("");
			Criteria.setPreferredSize(new Dimension(100, 20));

			CenterJP.add(Criteria, BorderLayout.CENTER);

			CenterJP.add(enter, BorderLayout.SOUTH);
			mainFrame.setVisible(true);
		}

		/**
		 * adjusts the screen for use when creating a size filter
		 */
		private void setSizeView() {
			if (RuleText.getParent() != null)
				CenterJP.remove(RuleText);
			CenterJP.add(greaterThan, BorderLayout.NORTH);

			if (Criteria != null)
				CenterJP.remove(Criteria);
			Criteria = new JTextArea("");
			Criteria.setPreferredSize(new Dimension(100, 20));

			CenterJP.add(Criteria, BorderLayout.CENTER);

			CenterJP.add(enter, BorderLayout.SOUTH);
			mainFrame.setVisible(true);
		}

		/**
		 * adjusts the screen for use when creating a Type filter
		 */
		private void setTypeView() {
			if (RuleText.getParent() != null)
				CenterJP.remove(RuleText);
			RuleText = new JLabel("Enter the FileType");
			if (greaterThan.getParent() != null)
				CenterJP.remove(greaterThan);
			CenterJP.add(RuleText, BorderLayout.NORTH);
			if (Criteria != null)
				CenterJP.remove(Criteria);

			Criteria = new JTextArea("");
			Criteria.setPreferredSize(new Dimension(100, 20));

			CenterJP.add(Criteria, BorderLayout.CENTER);
			
			CenterJP.add(enter, BorderLayout.SOUTH);
			mainFrame.setVisible(true);
		}
	};
	public FilterWindow(ActionListener action){
		String[] FilterOptions = { "None", "Type", "Size", "Name Contains" };
		String[] greaterOrLess = { "Greater Than", "Less Than" };
		Filter = new JComboBox<String>(FilterOptions);
		greaterThan = new JComboBox<String>(greaterOrLess);
		mainFrame = new JFrame("Please enter a Filter");
		defJP = new JPanel(new BorderLayout());
		CenterJP = new JPanel(new BorderLayout());
		enter = new FilterWindowButton(this, "Enter the filter");
		RuleText = new JLabel("Enter the FileType");
		
		CenterJP.setPreferredSize(new Dimension(100,50));
		mainFrame.add(defJP);
		defJP.add(Filter, BorderLayout.NORTH);
		defJP.add(CenterJP, BorderLayout.CENTER);
		defJP.add(enter, BorderLayout.SOUTH);
		
		enter.addActionListener(action);
		Filter.addActionListener(filterListener);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	
}
