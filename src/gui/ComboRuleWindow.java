package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import app.AppThread;
import app.ComboFilter;
import app.Rule;
import app.RuleFilter;
import app.SizeFilter;
import app.TypeFilter;
import app.nameFilter;

public class ComboRuleWindow {
	JFrame comboFrame;
	JPanel defJP;
	JButton addFilter;
	ComboRuleButton createRule;
	JButton newFolder;
	JLabel currentRule;
	JLabel FolderText;
	public File destination;
	public ComboFilter thefilter;

	/*
	 * Action listener for the new folder button
	 */
	ActionListener newFolderListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(comboFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				destination = chooser.getSelectedFile();
				FolderText.setText(chooser.getSelectedFile().getPath());
			}

		}
	};

	/*
	 * action listener for the window that will appear from this one
	 */
	ActionListener ReturnFunctionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			FilterWindowButton button = (FilterWindowButton) e
					.getSource();
			FilterWindow theWindow = button.myWindow;
			if (((String) theWindow.Filter.getSelectedItem())
					.equals("Type")) {
				TypeFilter filter;
				try {
					filter = new TypeFilter(
							((String) theWindow.Criteria.getText()));
					updateRuleAndFrame(theWindow, filter);

				} catch (Exception e1) {
					JOptionPane
							.showMessageDialog(comboFrame,
									"Error: Make sure you have a valid file type");
				}

			}

			if (((String) theWindow.Filter.getSelectedItem())
					.equals("Name Contains")) {
				nameFilter filter;
				try {
					filter = new nameFilter(
							((String) theWindow.Criteria.getText()));
					updateRuleAndFrame(theWindow, filter);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(comboFrame,
							"Error: you must enter a identifier.");
				}

			}
			if (((String) theWindow.Filter.getSelectedItem())
					.equals("Size")) {
				SizeFilter filter;
				try {
					int size = Integer
							.parseInt((String) theWindow.Criteria
									.getText());
					boolean grtrThn = theWindow.greaterThan
							.getSelectedItem().equals("Greater Than");
					filter = new SizeFilter(size, grtrThn);
					updateRuleAndFrame(theWindow, filter);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(comboFrame,
							"Error: You must have a valid file size");
				}
			}

		}

		private void updateRuleAndFrame(FilterWindow theWindow,
				RuleFilter filter) {
			thefilter.addRuleFilter(filter);
			currentRule.setText(thefilter.display());
			theWindow.mainFrame.dispose();
		}
	};
	/*
	 * action listener for the add filter button
	 */
	ActionListener addFilterListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new FilterWindow(ReturnFunctionListener);
		}
	};

	public ComboRuleWindow(ActionListener action) {
		comboFrame = new JFrame("create a rule with multiple filters");
		defJP = new JPanel(new BorderLayout());
		comboFrame.add(defJP);

		addFilter = new JButton("Add a filter");
		createRule = new ComboRuleButton(this, "Add the rule");
		newFolder = new JButton("Choose the destination");
		currentRule = new JLabel("Rule: No Filters currently made");
		FolderText = new JLabel("Folder: No folder Selected");

		thefilter = new ComboFilter();

		createRule.addActionListener(action);
		newFolder.addActionListener(newFolderListener);
		addFilter.addActionListener(addFilterListener);

		defJP.add(addFilter, BorderLayout.WEST);
		defJP.add(newFolder, BorderLayout.EAST);
		defJP.add(FolderText, BorderLayout.NORTH);
		defJP.add(currentRule, BorderLayout.SOUTH);
		defJP.add(createRule, BorderLayout.CENTER);

		comboFrame.pack();
		comboFrame.setVisible(true);

	}
}
