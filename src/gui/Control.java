package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.AppThread;
import app.ComboFilter;
import app.FileManager;
import app.Rule;
import app.RuleFilter;
import app.SizeFilter;
import app.TypeFilter;
import app.nameFilter;

/**
 * This class is responsible for calling all of the responsible functions when
 * an action is made from the view.
 * 
 * @author meade4
 * 
 */
public class Control {
	Model model;
	View view;
	AppThread runningManager;

	/**
	 * getter for the model class because it needs to be referenced in a test
	 * 
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/*
	 * Actionlistener for the delete button
	 */
	ActionListener deleteButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int[] ruleIndex = view.ruleList.getSelectedIndices();
			for (int i = ruleIndex.length - 1; i >= 0; i--) {
				view.removeRuleFromListAt(ruleIndex[i]);
				model.getFileManager().removeRuleAt(ruleIndex[i]);
			}
		}
	};

	/*
	 * Action listener to set the default folder
	 */
	ActionListener DefaultFolderListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			runningManager.interrupt();

			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(view.frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.print(chooser.getSelectedFile().getName());
				model.getFileManager().setDir(chooser.getSelectedFile());
				runningManager = (new AppThread(model.getFileManager()));
				runningManager.start();
			}

		}
	};

	/*
	 * Action listener for the Add Rule button
	 */
	ActionListener RuleListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			runningManager.interrupt();
			view.showComboRuleWindow(new ActionListener() {
				public void actionPerformed(ActionEvent e2) {
					ComboRuleButton button = (ComboRuleButton) e2.getSource();
					ComboRuleWindow theWindow = button.myWindow;
					try {
						Rule comboRule = new Rule(theWindow.thefilter,
								theWindow.destination);
						model.getFileManager().addRule(comboRule);
						view.addRuletoList(comboRule);
						theWindow.comboFrame.dispose();
						runningManager = (new AppThread(model.getFileManager()));
						runningManager.start();
					} catch (Exception e) {

						JOptionPane.showMessageDialog(view.frame,
								"Error: You must select a file");
					}

				}
			});
		}
	};

	/**
	 * Constructor for the control class. Everything is even driven
	 */
	public Control() {
		// set function to save settings on program exit
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				runningManager.interrupt();
				model.getFileManager().SaveToJSON();
			}

		}));

		view = new View();
		model = new Model(view);

		runningManager = (new AppThread(model.getFileManager()));
		runningManager.start();

		view.setDeleteButtonListener(deleteButtonListener);
		view.setDefFolderListener(DefaultFolderListener);
		view.comboRuleListener(RuleListener);

	}

	public static void main(String[] args) {
		new Control();

	}
}
