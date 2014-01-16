package tests;

import static org.junit.Assert.*;
import gui.Control;

import java.io.File;

import org.junit.Test;

import app.FileManager;
import app.Rule;
import app.SizeFilter;
import app.TypeFilter;
import app.nameFilter;

public class JSONTests {

	@Test
	public void testNoRuleManager() {
		File dest = new File("C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager fileMan = new FileManager(dest);

		fileMan.SaveToJSON();
		Control ctr = new Control();
		FileManager fileMan2 = ctr.getModel().getDefaultInfo();
		assertTrue(fileMan.getDir().equals(fileMan2.getDir()));
	}

	@Test
	public void testJSONWithRules() {
		File dest = new File("C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager fileMan = new FileManager(dest);

		try {
			Rule newRule = new Rule(new TypeFilter("txt"), RuleDest);
		} catch (Exception e) {

			e.printStackTrace();
		}
		try {
			Rule newRule = new Rule(new SizeFilter(2, true), RuleDest);
		} catch (Exception e) {

			e.printStackTrace();
		}
		try {
			Rule newRule = new Rule(new nameFilter("blah"), RuleDest);
		} catch (Exception e) {

			e.printStackTrace();
		}
		fileMan.SaveToJSON();
		Control ctr = new Control();
		FileManager fileMan2 = ctr.getModel().getDefaultInfo();
		assertTrue(fileMan.getDir().equals(fileMan2.getDir()));
		for (int i = 0; i < fileMan.RuleLength(); i++) {
			assertTrue(fileMan.getRuleAt(i).getDest()
					.equals(fileMan2.getRuleAt(i).getDest()));

		}
	}

}
