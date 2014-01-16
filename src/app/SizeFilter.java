package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

/**
 * rule filter extension for the size catagory
 * @author meade4
 *
 */
public class SizeFilter extends RuleFilter {

	int size;
	boolean greaterThan; // true if you want to filter files bigger than the
							// size false otherwise
	//constructor
	public SizeFilter(int theType, boolean grtrThn) {
		greaterThan = grtrThn;
		size = theType;
	}

	
	String display() {
		if (greaterThan)
			return "greater than " + size + " size ";
		else
			return "less than " + size + " size ";
	}

	
	void move(File file, File dest) {

		String name = file.getName();

		if (isRuleSatisfied(file)) {
			try {
				Files.move(Paths.get(file.getCanonicalPath()),
						Paths.get(dest.getCanonicalPath() + "\\" + name));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void addToJsonObj(JSONObject obj) {
		obj.put("type", "size");
		obj.put("size", "" + size);
		obj.put("GreaterThn", "" + greaterThan);

	}

	@Override
	boolean isRuleSatisfied(File file) {
		if (greaterThan) {
			return file.length() > size;
		} else {
			return file.length() < size;
		}
	}
	//GETTERS AND SETTERS
	int getType() {
		return size;
	}

	void setType(int newSize) {

		size = newSize;
	}

}
