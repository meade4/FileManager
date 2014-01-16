package app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

/**
 * the only currently supported filter type which is to filter by File Type
 * 
 * @author meade4
 * 
 */
public class TypeFilter extends RuleFilter {
	String type;

	// Taken from user: "adarshr" from StackOverflow post
	// http://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
	boolean isAlphaNum(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}

		return true;
	}

	public TypeFilter(String theType) throws Exception {
		if (theType.equals("") || !isAlphaNum(theType))
			throw new Exception();
		type = theType;
	}

	public String display() {
		return type.toString() + " files ";
	}

	public void move(File file, File dest) {
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
		obj.put("type", "FileType");
		obj.put("FileType", type);

	}

	@Override
	protected boolean isRuleSatisfied(File file) {
		String name = file.getName();
		return name.endsWith(type.toString());
	}

	// GETTER & SETTER
	public String getType() {
		return type;
	}

	public void setType(String newType) throws Exception {
		if (type.equals("") || !isAlphaNum(type))
			throw new Exception();

		type = newType;
	}
}
