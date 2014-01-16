package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import app.ComboFilter;
import app.FileManager;
import app.Rule;
import app.TypeFilter;
import app.nameFilter;

public class TestComboRules {

	@Test
	public void testOneRule() throws Exception {
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		ComboFilter filt = new ComboFilter();
		filt.addRuleFilter(new nameFilter("test"));
		Rule newRule = new Rule(filt,RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\test.txt");
		file.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");
		assertTrue(f.exists());
		f.delete();
	}
	
	@Test
	public void testTwoRules() throws Exception {
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		ComboFilter filt = new ComboFilter();
		filt.addRuleFilter(new nameFilter("test"));
		filt.addRuleFilter(new TypeFilter("txt"));
		Rule newRule = new Rule(filt,RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\test.txt");
		file.createNewFile();
		
		File file2 = new File("C:\\Users\\The Mountain\\test.jpg");
		file2.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");
		File f2 = new File("C:\\Users\\The Mountain\\Downloads\\test.jpg");
		assertTrue(f.exists());
		assertFalse(f2.exists());
		f.delete();
		f2.delete();
	}
	
	@Test
	public void testThreeRules() throws Exception {
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		ComboFilter filt = new ComboFilter();
		filt.addRuleFilter(new nameFilter("test"));
		filt.addRuleFilter(new nameFilter("file"));
		filt.addRuleFilter(new TypeFilter("txt"));
		Rule newRule = new Rule(filt,RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\testfile.txt");
		file.createNewFile();
		
		File file2 = new File("C:\\Users\\The Mountain\\testfile.jpg");
		file2.createNewFile();
		
		File file3 = new File("C:\\Users\\The Mountain\\test.txt");
		file3.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\testfile.txt");
		File f2 = new File("C:\\Users\\The Mountain\\Downloads\\testfile.jpg");
		File f3 = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");

		assertTrue(f.exists());
		assertFalse(f2.exists());
		assertFalse(f3.exists());
		f.delete();
		f2.delete();
		f3.delete();
	}

}
