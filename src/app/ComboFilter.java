package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Rule filter that is determined by multiple filters
 * @author meade4
 *
 */
public class ComboFilter extends RuleFilter {
	List<RuleFilter> combinedRules;

	public ComboFilter(){
		combinedRules = new ArrayList<RuleFilter>();
	}

	public void addRuleFilter(RuleFilter filt) {
		combinedRules.add(filt);
	}

	@Override
	public void addToJsonObj(JSONObject ruleObj) {
		ruleObj.put("type", "combo");
		JSONArray filters = new JSONArray();
		for (int i = 0; i < combinedRules.size(); i++) {
			JSONObject filterObj = new JSONObject();
			RuleFilter filt = combinedRules.get(i);
			filt.addToJsonObj(filterObj);
			filters.add(filterObj);
		}
		ruleObj.put("filters", filters);

	}

	@Override
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

	@Override
	public
	String display() {
		String displayText = "";
		for (int i = 0; i < combinedRules.size(); i++) {
			RuleFilter filt = combinedRules.get(i);
			displayText = displayText + filt.display();
			if (i != combinedRules.size() - 1)
				displayText = displayText + (" AND are ");
		}
		return displayText;
	}

	@Override
	boolean isRuleSatisfied(File file) {
		for (int i = 0; i < combinedRules.size(); i++) {
			RuleFilter filt = combinedRules.get(i);
			if (!filt.isRuleSatisfied(file))
				return false;
		}
		return true;
	}

}
