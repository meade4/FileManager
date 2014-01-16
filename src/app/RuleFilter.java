package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

/**
 * abstract class for rule filters so that more can be implemented soon.
 * 
 * @author meade4
 * 
 */
public abstract class RuleFilter {
	/**
	 * Function that adds each filters information to a JSONObj so that a rule
	 * can easily add its info to a JSONObject
	 * 
	 * @param ruleObj
	 *            the JSONObject that the information will be added to
	 */
	public abstract void addToJsonObj(JSONObject ruleObj);

	/**
	 * The only function that will every actually move anything
	 * 
	 * @param file
	 *            File to be moved
	 * @param dest
	 *            destination folder
	 */
	abstract void move(File file, File dest);

	/**
	 * display a string for the gUI
	 * 
	 * @return a String to be displayed
	 */
	abstract String display();

	/**
	 * checks if the rule is satisfied. Used for move and makes combofilter
	 * moving easier
	 * 
	 * @param file
	 *            the file that will be checked
	 * @return true if the condition of the rule is satisfied by this file.
	 *         False otherwise
	 */
	abstract boolean isRuleSatisfied(File file);

}
