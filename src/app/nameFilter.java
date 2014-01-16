package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

/**
 * rule filter extension for the name catagory
 * @author meade4
 *
 */
public class nameFilter extends RuleFilter {
	String substring;

	/**
	 * constructor for a name filter
	 * @param newSubstring substring that will cause a file to be moved
	 * @throws Exception if the string is empty
	 */
	public nameFilter(String newSubstring) throws Exception {
		if (newSubstring.equals(""))
			throw new Exception();
		substring = newSubstring;
	}

	
	String display() {
		return "named with " + substring;
	}

	void move(File file, File dest) {

		if (isRuleSatisfied(file)) {

			try {
				Files.move(
						Paths.get(file.getCanonicalPath()),
						Paths.get(dest.getCanonicalPath() + "\\"
								+ file.getName()));
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

	public void addToJsonObj(JSONObject obj) {
		obj.put("type", "name");
		obj.put("name", substring);

	}

	
	boolean isRuleSatisfied(File file) {
		String name = file.getName();
		return name.indexOf(substring) != -1;
	}

	/*
	 * GETTERS AND SETTERS
	 */
	String getSubstring() {
		return substring;
	}

	void setSubstring(String newSubstring) {
		substring = newSubstring;
	}
}
