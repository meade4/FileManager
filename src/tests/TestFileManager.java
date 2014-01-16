package tests;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import app.FileManager;

import app.Rule;
import app.TypeFilter;

/**
 * Tests that the Application behind the GUI is working properly.
 * @author meade4
 *
 */
public class TestFileManager {

	/**
	 * Test that the constructor is working
	 */
	@Test
	public void testCreateManager() {
		File dest = new File("C:\\");
		FileManager manager = new FileManager(dest);
		
	}
	
	/**
	 * Test that adding a rule works
	 * @throws Exception 
	 */
	@Test 
	public void testAddRule() throws Exception{
		File dest = new File("C:\\");
		File RuleDest = new File("C:\\Users");
		FileManager manager = new FileManager(dest);
		Rule newRule = new Rule(new TypeFilter("txt"),RuleDest);
		manager.addRule(newRule);
		assertTrue(manager.getRuleAt(0).equals(newRule));
	}
	
	/**
	 * Test that the program actually does what I want it to
	 * @throws Exception 
	 */
	@Test
	public void testMove() throws Exception{
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		Rule newRule = new Rule(new TypeFilter("txt"),RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\test.txt");
		file.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");
		assertTrue(f.exists());
		f.delete();
		
	}

}
