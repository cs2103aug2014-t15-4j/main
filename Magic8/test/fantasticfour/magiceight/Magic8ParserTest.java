package fantasticfour.magiceight;

import static org.junit.Assert.*;

import org.junit.Test;

public class Magic8ParserTest {

	@Test
	public void testParseCommand() {
		fail("Not yet implemented");
	}
	@Test
	public void testIsCommandValid() {
		//All the invalid commands for the parser
		assertFalse("Invalid command", Magic8Parser.isCommandValid("ad buy eggs"));
		assertFalse("Invalid command", Magic8Parser.isCommandValid("rem buy eggs"));
		assertFalse("Invalid command", Magic8Parser.isCommandValid("dele buy eggs"));
		assertFalse("Invalid command", Magic8Parser.isCommandValid("dis buy eggs"));
		assertFalse("Invalid command", Magic8Parser.isCommandValid("show buy eggs"));
		assertFalse("Invalid command", Magic8Parser.isCommandValid("displ buy eggs"));
		assertFalse("Invalid command", Magic8Parser.isCommandValid("edi buy eggs to buy soap"));
		assertFalse("Invalid command", Magic8Parser.isCommandValid("change buy eggs to buy soap"));
		
		//All the valid add commands
		assertTrue("Task: buy eggs has been added", Magic8Parser.isCommandValid("add buy eggs"));
		assertTrue("Task: buy eggs has been added", Magic8Parser.isCommandValid("-a buy eggs"));
		
		//All the valid remove/delete commands
		assertTrue("Task: buy eggs has been deleted", Magic8Parser.isCommandValid("remove buy eggs"));
		assertTrue("Task: buy eggs has been deleted", Magic8Parser.isCommandValid("delete buy eggs"));
		assertTrue("Task: buy eggs has been deleted", Magic8Parser.isCommandValid("del buy eggs"));
		assertTrue("Task: buy eggs has been deleted", Magic8Parser.isCommandValid("-r buy eggs"));
		assertTrue("Task: buy eggs has been deleted", Magic8Parser.isCommandValid("-d buy eggs"));
		
		//All the valid edit/update commands
		assertTrue("Task: buy eggs has been edited to buy soap", Magic8Parser.isCommandValid("edit buy eggs to buy soap"));
		assertTrue("Task: buy eggs has been edited to buy soap", Magic8Parser.isCommandValid("-e buy eggs to buy soap"));
		
		//All the valid clear list commands
		assertTrue("Tasks have been cleared", Magic8Parser.isCommandValid("clear list"));
		assertTrue("Tasks have been cleared", Magic8Parser.isCommandValid("-c list"));

		//All the valid display list commands
		assertEquals("Tasklist being displayed", Magic8Parser.isCommandValid("display tasks"));
		assertEquals("Tasklist being displayed", Magic8Parser.isCommandValid("disp tasks"));
		
	}
}
	
	