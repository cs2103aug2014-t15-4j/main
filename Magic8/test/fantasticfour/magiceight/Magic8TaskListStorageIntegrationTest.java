package fantasticfour.magiceight;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class Magic8TaskListStorageIntegrationTest {
    private static final String EXCEPTION_EXPECTED_NEGATIVE_ID = "Exception expected for negative id";
    private static final String EXCEPTION_EXPECTED_ZERO_ID = "Exception expected for zero id";
    private static final String EXCEPTION_EXPECTED_NULL_DESCRIPTION = "Exception expected for null string description";
    private static final String EXCEPTION_EXPECTED_EMPTY_DESCRIPTION = "Exception expected for empty string description";
    private static final String EXCEPTION_EXPECTED_NULL_TAG = "Exception expected for null string tag";
    private static final String EXCEPTION_EXPECTED_EMPTY_TAG = "Exception expected for empty string tag";
    private static final String EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG = "Exception expected for non-alphanumeric tag";

    private static final int TEST_NEGATIVE_ID = -1;
    private static final int TEST_ZERO_ID = 0;
    private static final int TEST_POSITIVE_ID = 1;
    private static final String TEST_EMPTY_STRING = "";
    private static final String TEST_NON_ALPHANUMERIC_STRING = "string!";
    private static final String TEST_ALPHANUMERIC_STRING = "tag4";
    private static final Calendar TEST_DATE = new GregorianCalendar();
    private static final HashSet<String> TEST_TAGS = new HashSet<String>(
            Arrays.asList("tag1", "tag2", "tag3"));
    private static final Magic8Task TEST_TASK = new Magic8Task(
            TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_TAGS);

    private static final File TEST_FILE = new File("testfile.txt");

    @Test
    public void testAddTask() {

        // test add for single task
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 2);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("2", testFileContents.get(0)[0]);

            // check if second line has correct number of tokens
            assertTrue(testFileContents.get(1).length == 4);

            // check if second line has correct content
            assertEquals("1", testFileContents.get(1)[0]);
            assertEquals("string!", testFileContents.get(1)[1]);
            assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                    testFileContents.get(1)[2]);
            assertEquals("tag2 tag3 tag1", testFileContents.get(1)[3]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test add for multiple(five) tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 6);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }
            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveTask() {

        // test remove one task
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(1);
            m8tl.removeTask(task);
            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 1);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("2", testFileContents.get(0)[0]);

            csvr.close();
            TEST_FILE.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // test remove multiple(five) tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            for (int i = 1; i <= 5; i++) {
                Magic8Task task = new Magic8Task(TEST_TASK);
                task.setId(i);
                m8tl.removeTask(task);
            }

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 1);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            csvr.close();
            TEST_FILE.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateTask() {

        // test updating of one task from list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(3);
            task.setDesc("Updated task description");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            Calendar cal = new GregorianCalendar();
            cal.setTime(df.parse("29/10/2014 11:11:11"));
            task.setEndTime(cal);
            
            HashSet<String> tags = new HashSet<String>(Arrays.asList("tag4",
                    "tag5"));
            task.setTags(tags);

            m8tl.updateTask(task);

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 6);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            // check if rest of lines have correct number of tokens and content
            for (int i = 1; i < testFileContents.size(); i++) {

                assertTrue(testFileContents.get(i).length == 4);
                if (i == 3) {
                    assertEquals(Integer.toString(i),
                            testFileContents.get(i)[0]);
                    assertEquals("Updated task description",
                            testFileContents.get(i)[1]);
                    assertEquals("Wed Oct 29 11:11:11 SGT 2014",
                            testFileContents.get(i)[2]);
                    assertEquals("tag4 tag5", testFileContents.get(i)[3]);

                } else {

                    assertEquals(Integer.toString(i),
                            testFileContents.get(i)[0]);
                    assertEquals("string!", testFileContents.get(i)[1]);
                    assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                            testFileContents.get(i)[2]);
                    assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
                }
            }
            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testClearTasks() {

        // test clearing a list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.clearTasks();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 1);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testUndo() {
        // test single undo after adding one task
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.undo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 1);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("1", testFileContents.get(0)[0]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test single undo after adding five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.undo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 5);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("5", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test multiple(three) undos after adding five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.undo();
            m8tl.undo();
            m8tl.undo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 3);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("3", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test undo removing of one task
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(1);

            m8tl.removeTask(task);
            m8tl.undo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 2);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("2", testFileContents.get(0)[0]);

            // check if second line has correct content
            assertEquals("1", testFileContents.get(1)[0]);
            assertEquals("string!", testFileContents.get(1)[1]);
            assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                    testFileContents.get(1)[2]);
            assertEquals("tag2 tag3 tag1", testFileContents.get(1)[3]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test undo updating of one task
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(1);
            task.setDesc("Updated task description");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Calendar cal = new GregorianCalendar();
            cal.setTime(df.parse("29/10/2014 11:11:11"));
            task.setEndTime(cal);
            
            HashSet<String> tags = new HashSet<String>(Arrays.asList("tag4",
                    "tag5"));
            task.setTags(tags);

            m8tl.updateTask(task);
            m8tl.undo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 2);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("2", testFileContents.get(0)[0]);

            // check if second line has correct content
            assertEquals("1", testFileContents.get(1)[0]);
            assertEquals("string!", testFileContents.get(1)[1]);
            assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                    testFileContents.get(1)[2]);
            assertEquals("tag2 tag3 tag1", testFileContents.get(1)[3]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test undo clearing a list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.clearTasks();
            m8tl.undo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 6);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }
            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test multiple undos throughout series of actions
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());

            // add five tasks
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            // undo once
            m8tl.undo();

            // update task with id 3
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(3);
            task.setDesc("Updated task description");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Calendar cal = new GregorianCalendar();
            cal.setTime(df.parse("29/10/2014 11:11:11"));
            task.setEndTime(cal);
            
            HashSet<String> tags = new HashSet<String>(Arrays.asList("tag4",
                    "tag5"));
            task.setTags(tags);
            m8tl.updateTask(task);

            // remove task with id 1
            task = new Magic8Task(TEST_TASK);
            task.setId(1);
            m8tl.removeTask(task);

            // clear tasks
            m8tl.clearTasks();

            // undo twice
            m8tl.undo();
            m8tl.undo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 5);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("5", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                if (i == 3) {
                    assertEquals(Integer.toString(i),
                            testFileContents.get(i)[0]);
                    assertEquals("Updated task description",
                            testFileContents.get(i)[1]);
                    assertEquals("Wed Oct 29 11:11:11 SGT 2014",
                            testFileContents.get(i)[2]);
                    assertEquals("tag4 tag5", testFileContents.get(i)[3]);

                } else {

                    assertEquals(Integer.toString(i),
                            testFileContents.get(i)[0]);
                    assertEquals("string!", testFileContents.get(i)[1]);
                    assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                            testFileContents.get(i)[2]);
                    assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
                }
            }

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void testRedo() {

        // test single redo after no undos on list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.redo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 6);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }
            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test single redo after single add undo on list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.undo();
            m8tl.redo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 6);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }
            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test five redos after five add undos on list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.undo();
            m8tl.undo();
            m8tl.undo();
            m8tl.undo();
            m8tl.undo();

            m8tl.redo();
            m8tl.redo();
            m8tl.redo();
            m8tl.redo();
            m8tl.redo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 6);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }
            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test three redos after five add undos on list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.undo();
            m8tl.undo();
            m8tl.undo();
            m8tl.undo();
            m8tl.undo();

            m8tl.redo();
            m8tl.redo();
            m8tl.redo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 4);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("4", testFileContents.get(0)[0]);

            for (int i = 1; i < testFileContents.size(); i++) {
                // check if rest of lines have correct number of tokens
                assertTrue(testFileContents.get(i).length == 4);

                // check if rest of lines have correct content
                assertEquals(Integer.toString(i), testFileContents.get(i)[0]);
                assertEquals("string!", testFileContents.get(i)[1]);
                assertEquals("Thu Jan 01 07:30:00 SGT 1970",
                        testFileContents.get(i)[2]);
                assertEquals("tag2 tag3 tag1", testFileContents.get(i)[3]);
            }
            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test single redo after a single undo for removal of one task from a
        // list of one task
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(1);

            m8tl.removeTask(task);
            m8tl.undo();
            m8tl.redo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 1);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("2", testFileContents.get(0)[0]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }
        
     // test redo of undo for updating of one task
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(1);
            task.setDesc("Updated task description");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
           
            Calendar cal = new GregorianCalendar();
            cal.setTime(df.parse("29/10/2014 11:11:11"));
            task.setEndTime(cal);
            
            HashSet<String> tags = new HashSet<String>(Arrays.asList("tag4",
                    "tag5"));
            task.setTags(tags);

            m8tl.updateTask(task);
            m8tl.undo();
            m8tl.redo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 2);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("2", testFileContents.get(0)[0]);

            // check if second line has correct content
            assertEquals("1",
                    testFileContents.get(1)[0]);
            assertEquals("Updated task description",
                    testFileContents.get(1)[1]);
            assertEquals("Wed Oct 29 11:11:11 SGT 2014",
                    testFileContents.get(1)[2]);
            assertEquals("tag4 tag5", testFileContents.get(1)[3]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }
        
        // test redo of undo for clearing a list of five tasks
        try {
            TEST_FILE.delete();
            TEST_FILE.createNewFile();

            Magic8TaskList m8tl = new Magic8TaskList(TEST_FILE.getName());
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));
            m8tl.addTask(new Magic8Task(TEST_TASK));

            m8tl.clearTasks();
            m8tl.undo();
            m8tl.redo();

            CSVReader csvr = new CSVReader(new BufferedReader(
                    new InputStreamReader(new FileInputStream(TEST_FILE))));
            ArrayList<String[]> testFileContents = (ArrayList<String[]>) csvr
                    .readAll();

            // check if file has correct number of lines
            assertTrue(testFileContents.size() == 1);

            // check if first line has correct number of tokens
            assertTrue(testFileContents.get(0).length == 1);

            // check if first line has correct content
            assertEquals("6", testFileContents.get(0)[0]);

            csvr.close();
            TEST_FILE.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }
    }

}
