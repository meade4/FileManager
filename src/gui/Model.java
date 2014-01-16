package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.AppThread;
import app.ComboFilter;
import app.FileManager;
import app.Rule;
import app.SizeFilter;
import app.TypeFilter;
import app.nameFilter;

/**
 * This class is only responsible for setting up the applications default values
 * when the application is launched
 * 
 * @author meade4
 * 
 */
public class Model {
	FileManager fileManager;

	/**
	 * constructor for the model. restores or sets up a new filemanager
	 * 
	 * @param view
	 *            The view is passed in so that we can react if a previous file
	 *            isn't found
	 */
	public Model(View view) {

		File def = new File(System.getProperty("user.dir") + "\\default.json");

		if (!def.exists()) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(view.frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.print(chooser.getSelectedFile().getName());
				fileManager = new FileManager(chooser.getSelectedFile());

			}
		} else {
			fileManager = getDefaultInfo();
			for (int i = 0; i < fileManager.RuleLength(); i++) {
				view.addRuletoList(fileManager.getRuleAt(i));
			}

		}
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public FileManager getFileManager() {
		return fileManager;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

	/**
	 * restores the info from a previous json file
	 * 
	 * @return
	 */
	public FileManager getDefaultInfo() {
		JSONParser parser = new JSONParser();
		Object obj;
		FileManager fileMan = null;
		try {
			obj = parser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\default.json"));
			JSONObject jsonObject = (JSONObject) obj;

			String def = (String) jsonObject.get("default Folder");
			File directory = new File(def);
			fileMan = new FileManager(directory);
			JSONArray rules = (JSONArray) jsonObject.get("rules");
			for (int i = 0; i < rules.size(); i++) {
				JSONObject ruleObj = (JSONObject) rules.get(i);
				if (ruleObj.get("type").equals("size")) {
					parseSize(fileMan, ruleObj);
				} else if (ruleObj.get("type").equals("FileType")) {
					parseFileType(fileMan, ruleObj);
				} else if (ruleObj.get("type").equals("name")) {
					parseName(fileMan, ruleObj);
				} else if (ruleObj.get("type").equals("combo")) {
					parseCombo(fileMan, ruleObj);
				}

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return fileMan;
	}

	/**
	 * restore rule from a JSON file if the category was a Combination rule
	 * 
	 * @param fileMan
	 *            file manager for the rule to be added to
	 * @param ruleObj
	 *            JSON file that is being parsed
	 */
	private void parseCombo(FileManager fileMan, JSONObject ruleObj) {
		File dest = new File((String) ruleObj.get("dest"));
		ComboFilter combo = new ComboFilter();
		JSONArray filters = (JSONArray) ruleObj.get("filters");
		for (int j = 0; j < filters.size(); j++) {
			JSONObject FilterObj = (JSONObject) filters.get(j);
			if (FilterObj.get("type").equals("size")) {
				int size = (int) Integer.parseInt((String) FilterObj
						.get("size"));
				boolean GreatThn = Boolean.parseBoolean((String) FilterObj
						.get("GreaterThn"));
				SizeFilter filt = new SizeFilter(size, GreatThn);
				combo.addRuleFilter(filt);
			} else if (FilterObj.get("type").equals("FileType")) {
				String type = (String) FilterObj.get("FileType");
				TypeFilter filt = null;
				try {
					filt = new TypeFilter(type);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				combo.addRuleFilter(filt);
			} else if (FilterObj.get("type").equals("name")) {
				String name = (String) FilterObj.get("name");

				nameFilter filt = null;
				try {
					filt = new nameFilter(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
				combo.addRuleFilter(filt);
			}
		}
		try {
			fileMan.addRule(new Rule(combo, dest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * restore rule from a JSON file if the category was a name
	 * 
	 * @param fileMan
	 *            file manager for the rule to be added to
	 * @param ruleObj
	 *            JSON file that is being parsed
	 */
	private void parseName(FileManager fileMan, JSONObject ruleObj) {
		String name = (String) ruleObj.get("name");

		nameFilter filt = null;
		try {
			filt = new nameFilter(name);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File dest = new File((String) ruleObj.get("dest"));
		try {
			fileMan.addRule(new Rule(filt, dest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * restore rule from a JSON file if the category was a FileType
	 * 
	 * @param fileMan
	 *            file manager for the rule to be added to
	 * @param ruleObj
	 *            JSON file that is being parsed
	 */
	private void parseFileType(FileManager fileMan, JSONObject ruleObj) {
		String type = (String) ruleObj.get("FileType");

		TypeFilter filt = null;
		try {
			filt = new TypeFilter(type);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		File dest = new File((String) ruleObj.get("dest"));
		try {
			fileMan.addRule(new Rule(filt, dest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * restore rule from a JSON file if the category was a Size
	 * 
	 * @param fileMan
	 *            file manager for the rule to be added to
	 * @param ruleObj
	 *            JSON file that is being parsed
	 */
	private void parseSize(FileManager fileMan, JSONObject ruleObj) {
		int size = (int) Integer.parseInt((String) ruleObj.get("size"));
		boolean GreatThn = Boolean.parseBoolean((String) ruleObj
				.get("GreaterThn"));
		SizeFilter filt = new SizeFilter(size, GreatThn);
		File dest = new File((String) ruleObj.get("dest"));
		try {
			fileMan.addRule(new Rule(filt, dest));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
