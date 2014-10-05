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
		assertFalse(Magic8Parser.isCommandValid("ad buy eggs"));
		assertFalse(Magic8Parser.isCommandValid("rem buy eggs"));
		assertFalse(Magic8Parser.isCommandValid("dele buy eggs"));
		assertFalse(Magic8Parser.isCommandValid("dis buy eggs"));
		assertFalse(Magic8Parser.isCommandValid("show buy eggs"));
		assertFalse(Magic8Parser.isCommandValid("displ buy eggs"));
		
		assertEquals("Task buy eggs has been added", Magic8Parser.isCommandValid("add buy eggs"));
		assertEquals("Task buy eggs has been added", Magic8Parser.isCommandValid("-a buy eggs"));
		
		assertEquals("Task buy eggs has been deleted", Magic8Parser.isCommandValid("remove buy eggs"));
		assertEquals("Task buy eggs has been deleted", Magic8Parser.isCommandValid("delete buy eggs"));
		assertEquals("Task buy eggs has been deleted", Magic8Parser.isCommandValid("del buy eggs"));
		assertEquals("Task buy eggs has been deleted", Magic8Parser.isCommandValid("-r buy eggs"));
		assertEquals("Task buy eggs has been deleted", Magic8Parser.isCommandValid("-d buy eggs"));
		
		assertEquals("Tasks have been cleared", Magic8Parser.isCommandValid("clear list"));
		
		assertEquals("Tasklist being displayed", Magic8Parser.isCommandValid("display tasks"));
		assertEquals("Tasklist being displayed", Magic8Parser.isCommandValid("disp tasks"));
		
	}
}
	
	