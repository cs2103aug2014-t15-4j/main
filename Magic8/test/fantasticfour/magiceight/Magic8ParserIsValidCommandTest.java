package fantasticfour.magiceight;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fantasticfour.magiceight.parser.Magic8Parser;

//@author A0080527H
public class Magic8ParserIsValidCommandTest {

    // List of testing for add function
    List<String> POSITIVE_ADD_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_ADD_TESTING = new ArrayList<String>();

    // List of testing for delete function
    List<String> POSITIVE_CLEAR_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_CLEAR_TESTING = new ArrayList<String>();

    // List of testing for delete function
    List<String> POSITIVE_DELETE_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_DELETE_TESTING = new ArrayList<String>();

    // List of testing for display function
    List<String> POSITIVE_DISPLAY_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_DISPLAY_TESTING = new ArrayList<String>();

    // List of testing for edit function
    List<String> POSITIVE_EDIT_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_EDIT_TESTING = new ArrayList<String>();

    // List of testing for exit function
    List<String> POSITIVE_EXIT_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_EXIT_TESTING = new ArrayList<String>();

    // List of testing for help function
    List<String> POSITIVE_HELP_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_HELP_TESTING = new ArrayList<String>();

    // List of testing for search function
    List<String> POSITIVE_SEARCH_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_SEARCH_TESTING = new ArrayList<String>();

    // List of testing for undo function
    List<String> POSITIVE_UNDO_TESTING = new ArrayList<String>();
    List<String> NEGATIVE_UNDO_TESTING = new ArrayList<String>();

    @Before
    public void addTestInsertion() {
        POSITIVE_ADD_TESTING.add("add homework");
        POSITIVE_ADD_TESTING.add("add buy an egg's");
        POSITIVE_ADD_TESTING.add("add buy an egg");
        POSITIVE_ADD_TESTING.add("add buy an egg (20/14/2014");
        POSITIVE_ADD_TESTING.add("add buy an egg #grocery #chores");
        POSITIVE_ADD_TESTING.add("add buy an egg #grocery testing");

        NEGATIVE_ADD_TESTING.add("add");
    }

    @Test
    public void addCommandTest() {
        for (String command : POSITIVE_ADD_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_ADD_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }

    @Before
    public void clearTestInsertion() {
        POSITIVE_CLEAR_TESTING.add("clear");

        NEGATIVE_CLEAR_TESTING.add("clear all");
    }

    @Test
    public void clearCommandTest() {
        for (String command : POSITIVE_CLEAR_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_CLEAR_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }

    @Before
    public void deleteTestInsertion() {
        POSITIVE_DELETE_TESTING.add("delete 1");
        POSITIVE_DELETE_TESTING.add("delete 1,2,3,4,5");
        POSITIVE_DELETE_TESTING.add("delete 1 to 3");
        POSITIVE_DELETE_TESTING.add("delete #abcd");
        POSITIVE_DELETE_TESTING.add("delete *");
        POSITIVE_DELETE_TESTING.add("delete all");
        POSITIVE_DELETE_TESTING.add("delete");
        
        NEGATIVE_DELETE_TESTING.add("delete abcd");
    }

    @Test
    public void deleteCommandTest() {
        for (String command : POSITIVE_DELETE_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_DELETE_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }

    @Before
    public void displayTestInsertion() {
        POSITIVE_DISPLAY_TESTING.add("display");
        POSITIVE_DISPLAY_TESTING.add("display #grocery");
        POSITIVE_DISPLAY_TESTING.add("display keyword");
        POSITIVE_DISPLAY_TESTING.add("display all");
    }

    @Test
    public void displayCommandTest() {
        for (String command : POSITIVE_DISPLAY_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_DISPLAY_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }

    @Before
    public void editTestInsertion() {
        POSITIVE_EDIT_TESTING.add("edit 1 abcd");

        NEGATIVE_EDIT_TESTING.add("edit");
        NEGATIVE_EDIT_TESTING.add("edit 1");
        NEGATIVE_EDIT_TESTING.add("edit abcd");
    }

    @Test
    public void editCommandTest() {
        for (String command : POSITIVE_EDIT_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_EDIT_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }

    @Before
    public void exitTestInsertion() {
        POSITIVE_EXIT_TESTING.add("exit");

        NEGATIVE_EXIT_TESTING.add("exit all");
    }

    @Test
    public void exitCommandTest() {
        for (String command : POSITIVE_EXIT_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_EXIT_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }

    @Before
    public void helpTestInsertion() {
        POSITIVE_HELP_TESTING.add("help");

        NEGATIVE_HELP_TESTING.add("help all");
    }

    @Test
    public void helpCommandTest() {
        for (String command : POSITIVE_HELP_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_HELP_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }

    /* deprecated
    @Before
    public void searchTestInsertion() {
        POSITIVE_SEARCH_TESTING.add("search homework");

        NEGATIVE_SEARCH_TESTING.add("search");
    }
    
    @Test
    public void searchCommandTest() {
        for (String command : POSITIVE_SEARCH_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_SEARCH_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }
	*/
    
    @Before
    public void undoTestInsertion() {
        POSITIVE_UNDO_TESTING.add("undo");

        NEGATIVE_UNDO_TESTING.add("undo all");
    }

    @Test
    public void undoCommandTest() {
        for (String command : POSITIVE_UNDO_TESTING) {
            assertTrue("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
        for (String command : NEGATIVE_UNDO_TESTING) {
            assertFalse("error at test case: " + command,
                    Magic8Parser.isCommandValid(command));
        }
    }
}
