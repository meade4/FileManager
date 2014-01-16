package gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import app.Rule;

/**
 * Class is responsible for all of the swing components.
 * 
 * @author meade4
 * 
 */
public class View {
	JButton setDefFolder;
	public JFrame frame;
	DefaultListModel<String> listModel;
	public JList<String> ruleList;
	JButton deleteRule;
	JButton addComboRule;
	JPanel jp;
	JPanel jpNorth;
	JPanel jpSouth;

	/**
	 * initialization of all the view's components
	 */
	private void initComponents() {
		frame = new JFrame("Final Project");
		jpNorth = new JPanel();
		jpSouth = new JPanel();
		frame.getContentPane().add(jpNorth, BorderLayout.NORTH);
		frame.getContentPane().add(jpSouth, BorderLayout.CENTER);
		jpSouth.setPreferredSize(new Dimension(400, 305));
		jpNorth.setPreferredSize(new Dimension(400, 50));
		setDefFolder = new JButton("Set Folder");
		jpNorth.add(setDefFolder, BorderLayout.WEST);
		jpSouth.setLayout(new GridBagLayout());
		addComboRule = new JButton("Add Rule");
		deleteRule = new JButton("Delete Selected Rule");
		jpNorth.add(addComboRule);
		jpNorth.add(deleteRule);
	}

	/**
	 * sets up the GridBagConstraints for use in the view's constructor
	 * @return The GridBagConstraints c
	 */
	private GridBagConstraints setupGridBag() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridheight = 2;
		c.gridwidth = 2;
		return c;
	}

	/**
	 * sets up the list model and the scroll bar for the list
	 * @return the JScrollPane to be added to the JPanel
	 */
	private JScrollPane setupListAndScrollbar() {
		listModel = new DefaultListModel<String>();
		// Create list for current rules
		ruleList = new JList<String>(listModel);
		ruleList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ruleList.setLayoutOrientation(JList.VERTICAL);
		ruleList.setPreferredSize(new Dimension(400, 300));
		JScrollPane listScroller = new JScrollPane();
		listScroller.setPreferredSize(new Dimension(400, 300));
		listScroller.setViewportView(ruleList);
		return listScroller;
	}

	/**
	 * constructor for the view class
	 */
	public View() {

		initComponents();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		try {
			addSystemTray();
		} catch (AWTException e) {
			System.out.println("Error: Could not add tray icon");
		}

		jpSouth.add(setupListAndScrollbar(), setupGridBag());
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * adds a rule to the list component
	 * @param theRule the rule that will be added
	 */
	public void addRuletoList(Rule theRule) {
		int index = listModel.getSize();
		listModel.insertElementAt(theRule.display(), index);
		ruleList.setSelectedIndex(index);
		ruleList.ensureIndexIsVisible(index);

	}
	/**
	 * adds the functionality of the system tray so that the program can run in the background
	 * @throws AWTException
	 */
	public void addSystemTray() throws AWTException {
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		MenuItem openItem = new MenuItem("Open");
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
			}
		});
		final PopupMenu popup = new PopupMenu();
		popup.add(openItem);
		popup.add(exitMenuItem);

		//throw away image
		Image img = Toolkit.getDefaultToolkit().getImage(
				System.getProperty("user.dir")+"\\Raul.jpg");
		final TrayIcon trayIcon = new TrayIcon(img, "file manager", popup);
		SystemTray.getSystemTray().add(trayIcon);

	}

	/**
	 * removes a rule from the list component on the GUI
	 * @param index the index of the rule to be removed
	 */
	public void removeRuleFromListAt(int index) {
		listModel.removeElementAt(index);
	}

	/*
	 * FROM HERE DOWN ARE METHODS TO ADD ACTION LISTENERS TO BUTTONS
	 */
	

	

	public void showComboRuleWindow(ActionListener action) {
		new ComboRuleWindow(action);
	}

	public void setDefFolderListener(ActionListener action) {
		setDefFolder.addActionListener(action);
	}

	public void comboRuleListener(ActionListener action) {
		addComboRule.addActionListener(action);
	}

	

	public void setDeleteButtonListener(ActionListener action) {
		deleteRule.addActionListener(action);
	}

}
