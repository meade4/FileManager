package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import app.FileManager;
import app.Rule;
import app.TypeFilter;
import app.nameFilter;

public class MoveByNameTest {

	@Test
	public void testWholeName() throws Exception {
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		Rule newRule = new Rule(new nameFilter("test"),RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\test.txt");
		file.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");
		assertTrue(f.exists());
		f.delete();
	}
	
	@Test
	public void testBeginningName() throws Exception {
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		Rule newRule = new Rule(new nameFilter("te"),RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\test.txt");
		file.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");
		
		assertTrue(f.exists());
		f.delete();
	}
	
	@Test
	public void testEndingName() throws Exception {
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		Rule newRule = new Rule(new nameFilter("st"),RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\test.txt");
		file.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");
		
		assertTrue(f.exists());
		f.delete();
	}
	
	@Test
	public void testMiddleName() throws Exception {
		File dest = new File( "C:\\Users\\The Mountain\\");
		File RuleDest = new File("C:\\Users\\The Mountain\\Downloads");
		FileManager manager = new FileManager(dest);
		Rule newRule = new Rule(new nameFilter("es"),RuleDest);
		manager.addRule(newRule);
		
		File file = new File("C:\\Users\\The Mountain\\test.txt");
		file.createNewFile();
		
		manager.run();
		File f = new File("C:\\Users\\The Mountain\\Downloads\\test.txt");
		
		assertTrue(f.exists());
		f.delete();
	}
	


}
