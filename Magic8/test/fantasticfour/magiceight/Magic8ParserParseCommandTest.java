package fantasticfour.magiceight;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import fantasticfour.magiceight.parser.Magic8Parser;

//@author A0080527H-unused
//Parser implementation wes revamped and broke this test
public class Magic8ParserParseCommandTest {
    private final static String FUNCTION_MISMATCH = "function is not matching";
    private final static String TASK_DESC_MISMATCH = "task description is not matching";
    private final static String TAGS_MISMATCH = "list of tags is not matching";
    private final static String KEYWORDS_MISMATCH = "list of keywords is not matching";
    private final static String IDS_MISMATCH = "list of ids is not matching";
    private final static String STARTDATE_MISMATCH = "startDate is not matching";
    private final static String DEADLINE_MISMATCH = "deadline is not matching";
    private static String message;
    private static String inputTest;
    private static ArrayList<String> tagsInputTest;
    private static ArrayList<String> keywordsInputTest;
    private static ArrayList<Integer> idsInputTest;
    private static Magic8CommandObject testCommandObj;
    
    private <T> boolean generalComparator(T var1, T var2) {
        if(var1 == null) {
            if(var2 != null) {
                return false;
            }
        } else {
            return var1.equals(var2);
        }
        return true;
    }
    
    private <T> boolean listGeneralComparator(ArrayList<T> list1, ArrayList<T> list2) {
        if(list1 == null) {
            if(list1 != list2) {
                return false;
            }
        } else {
            if(list1.size() != list2.size()) {
                return false;
            }
            for(int i = 0; i < list1.size(); i++) {
                if(!list1.get(i).equals(list2.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean parseComparator(Magic8CommandObject obj1, Magic8CommandObject obj2) {
        if(!generalComparator(obj1.getFunction(), obj2.getFunction())) {
            message = FUNCTION_MISMATCH;
            return false;
        }      
        if(!generalComparator(obj1.getTaskDescription(), obj2.getTaskDescription())) {
            message = TASK_DESC_MISMATCH;
            return false;
        }
        if(!listGeneralComparator(obj1.getTags(), obj2.getTags())) {
            message = TAGS_MISMATCH;
            return false;
        }
        if(!listGeneralComparator(obj1.getKeywords(), obj2.getKeywords())) {
            message = KEYWORDS_MISMATCH;
            return false;
        }
        if(!listGeneralComparator(obj1.getIds(), obj2.getIds())) {
            message = IDS_MISMATCH;
            return false;
        }
        if(!generalComparator(obj1.getDeadline(), obj2.getDeadline())) {
            message = DEADLINE_MISMATCH;
            return false;
        }
        if(!generalComparator(obj1.getStartDate(), obj2.getStartDate())) {
            message = STARTDATE_MISMATCH;
            return false;
        }
        return true;
    }
    
    @Before
    public void initAllTest() {
        tagsInputTest = new ArrayList<String>();
        keywordsInputTest = new ArrayList<String>();
        idsInputTest = new ArrayList<Integer>();
        testCommandObj = new Magic8CommandObject();
    }
    
    @Test
    public void addCommandTest() {
        // Test 1
        inputTest = "add buy egg";
        testCommandObj.setFunction("add");
        testCommandObj.setTaskDescription("buy egg");
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setStartDate(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
        
        // Test 2
        inputTest = "add buy egg #grocery #chores";
        tagsInputTest.clear();
        tagsInputTest.add("grocery");
        tagsInputTest.add("chores");
        testCommandObj.setFunction("add");
        testCommandObj.setTaskDescription("buy egg ");
        testCommandObj.setTags(tagsInputTest);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 2: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    
    @Test
    public void clearCommandTest() {
        // Test 1
        inputTest = "clear";
        testCommandObj.setFunction("clear");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    
    @Test
    public void deleteCommandTest() {
        // Test 1
        inputTest = "delete 1,2,3,5";
        idsInputTest.clear();
        idsInputTest.add(1);
        idsInputTest.add(2);
        idsInputTest.add(3);
        idsInputTest.add(5);
        testCommandObj.setFunction("delete");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(idsInputTest);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
        
        // Test 2
        inputTest = "delete 1 to 3";
        idsInputTest.clear();
        idsInputTest.add(1);
        idsInputTest.add(2);
        idsInputTest.add(3);
        testCommandObj.setFunction("delete");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(idsInputTest);
        testCommandObj.setDeadline(null);
        assertTrue("Test 2: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
        
        // Test 3
        inputTest = "delete #abcd";
        tagsInputTest.clear();
        tagsInputTest.add("abcd");
        testCommandObj.setFunction("delete");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(tagsInputTest);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 3: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
        
        // Test 4
        inputTest = "delete *";
        testCommandObj.setFunction("delete");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 4: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    
    @Test
    public void displayCommandTest() {
        // Test 1
        inputTest = "display";
        testCommandObj.setFunction("display");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
        
        // Test 2
        inputTest = "display #grocery #abcd";
        tagsInputTest.clear();
        tagsInputTest.add("grocery");
        tagsInputTest.add("abcd");
        testCommandObj.setFunction("display");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(tagsInputTest);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 2: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    @Test
    public void editCommandTest() {
        // Test 1
        inputTest = "edit 1 abcd";
        idsInputTest.clear();
        idsInputTest.add(1);
        testCommandObj.setFunction("edit");
        testCommandObj.setTaskDescription("abcd");
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(idsInputTest);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    
    @Test
    public void exitCommandTest() {
        // Test 1
        inputTest = "exit";
        testCommandObj.setFunction("exit");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    
    @Test
    public void helpCommandTest() {
        // Test 1
        inputTest = "help";
        testCommandObj.setFunction("help");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    
    @Test
    public void searchCommandTest() {
        // Test 1
        inputTest = "search homework";
        keywordsInputTest.clear();
        keywordsInputTest.add("homework");
        testCommandObj.setFunction("search");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(keywordsInputTest);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
    
    @Test
    public void undoCommandTest() {
        // Test 1
        inputTest = "undo";
        testCommandObj.setFunction("undo");
        testCommandObj.setTaskDescription(null);
        testCommandObj.setTags(null);
        testCommandObj.setKeywords(null);
        testCommandObj.setIds(null);
        testCommandObj.setDeadline(null);
        assertTrue("Test 1: " + message, parseComparator(testCommandObj, Magic8Parser.parseCommand(inputTest).getCommandObject()));
    }
}
