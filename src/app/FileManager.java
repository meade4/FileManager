package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Base of the actual application. Mostly only has getters/setters and a move
 * function to actually move files based on the rules
 * 
 * @author meade4
 * 
 */
public class FileManager {
	File directory;
	List<Rule> defaultRules = new ArrayList<Rule>();

	/**
	 * run through the FileManagers rules in the specified file. Move files
	 * depending on those rules.
	 */
	public void run() {

		for (int i = 0; i < defaultRules.size(); i++) {

			Rule ruleAt = defaultRules.get(i);
			File[] files = directory.listFiles();
			for (int j = 0; j < files.length; j++) {
				if (files[j].isFile() && !files[j].isHidden()) {

					ruleAt.attemptMove(files[j]);
				}
			}
		}
	}

	/**
	 * save to a JSON file
	 */
	public void SaveToJSON() {
		FileWriter file = initFiles();
		writeToJSON(file);
	}

	/**
	 * actually do the writing to a JSON file
	 * 
	 * @param file
	 *            file to be written to
	 */
	private void writeToJSON(FileWriter file) {
		JSONObject obj = new JSONObject();
		obj.put("default Folder", this.getDir().getPath());
		JSONArray rules = new JSONArray();

		for (int i = 0; i < this.RuleLength(); i++) {
			Rule rule = this.getRuleAt(i);
			JSONObject ruleObj = new JSONObject();
			ruleObj.put("dest", rule.getDest().getPath());
			RuleFilter filt = rule.getFilter();
			filt.addToJsonObj(ruleObj);
			rules.add(ruleObj);
		}

		obj.put("rules", rules);
		try {
			file.write(obj.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * initialize the default file
	 * 
	 * @return the file that will be written to
	 */
	private FileWriter initFiles() {
		String compName = System.getProperty("user.name");
		FileWriter file;
		try {
			File defFolder = new File(".");
			file = new FileWriter(System.getProperty("user.dir")
					+ "\\default.json");

		} catch (IOException e) {

			e.printStackTrace();
			file = null;
		}
		return file;
	}

	/*
	 * GETTERS AND SETTERS
	 */
	public File getDir() {
		return directory;
	}

	public int RuleLength() {
		return defaultRules.size();
	}

	public Rule getRuleAt(int i) {
		return defaultRules.get(i);
	}

	public void removeRuleAt(int index) {
		defaultRules.remove(index);
	}

	public void setDir(File file) {
		this.directory = file;
	}

	public void addRule(Rule newRule) {
		this.defaultRules.add(newRule);
	}

	public FileManager(File dir) {
		setDir(dir);
	}

}
