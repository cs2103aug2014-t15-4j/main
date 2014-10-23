package fantasticfour.magiceight;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.TreeMap;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Magic8StorageTest {

    @Test
    public void testMagic8Storage() {
        // Test with valid parameters
        try {
            new Magic8Storage("testFileName");
            File testFile = new File("testFileName");
            testFile.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with file that does not exist
        try {
            File testFile = new File("testFileName");
            if (testFile.exists()) {
                testFile.delete();
            }
            new Magic8Storage("testFileName");
            testFile.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with existing empty file
        try {
            File testFile = new File("testFileName");
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            new Magic8Storage("testFileName");
            testFile.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with existing non-empty file
        try {
            File testFile = new File("testFileName");
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            CSVWriter cw = new CSVWriter(new FileWriter(testFile));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[4];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "@null";
            line[3] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();

            Magic8Storage testStorage = new Magic8Storage("testFileName");
            TreeMap<Integer, Magic8Task> testTaskList = testStorage
                    .getTaskList();

            assertTrue(testTaskList.size() == 3);

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2103T");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Software Engineering homework", null, testTags);

            assertTrue(testTaskList.get(1).equals(testMagic8Task));

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2010");
            testMagic8Task = new Magic8Task(2,
                    "Do Data Structures and Algorithms homework", null,
                    testTags);

            assertTrue(testTaskList.get(2).equals(testMagic8Task));

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("LSM1302");
            testMagic8Task = new Magic8Task(3, "Do Biology homework", null,
                    testTags);

            assertTrue(testTaskList.get(3).equals(testMagic8Task));

        } catch (AssertionError e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } finally {
            File testFile = new File("testFileName");
            if (testFile.exists()) {
                testFile.delete();
            }
        }
    }

    @Test
    public void testWriteToFile() {
        // Test with file that does not exist
        try {
            File testFile = new File("testFileName");
            if (testFile.exists()) {
                testFile.delete();
            }
            Magic8Storage testStorage = new Magic8Storage("testFileName");

            TreeMap<Integer, Magic8Task> testTaskList = new TreeMap<Integer, Magic8Task>();

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2103T");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Software Engineering homework", null, testTags);

            testTaskList.put(1, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2010");
            testMagic8Task = new Magic8Task(2,
                    "Do Data Structures and Algorithms homework", null,
                    testTags);

            testTaskList.put(2, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("LSM1302");
            testMagic8Task = new Magic8Task(3, "Do Biology homework", null,
                    testTags);

            testTaskList.put(3, testMagic8Task);

            testStorage.writeToFile(4, testTaskList);
            File expected = new File("expected");
            CSVWriter cw = new CSVWriter(new FileWriter(expected));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[4];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "@null";
            line[3] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();

            BufferedReader brExpected = new BufferedReader(new FileReader(
                    expected));

            BufferedReader brActual = new BufferedReader(new FileReader(
                    testFile));
            String lineExpected;
            String lineActual;
            while ((lineExpected = brExpected.readLine()) == null) {
                lineActual = brActual.readLine();
                if (lineActual == null) {
                    fail("Actual file differs from expected file");
                } else {
                    assertTrue(lineExpected.equals(lineActual));
                }
            }
            brExpected.close();
            brActual.close();

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            File testFile = new File("testFileName");
            File expected = new File("expected");
            if (testFile.exists()) {
                testFile.delete();
            }
            if (expected.exists()) {
                expected.delete();
            }
        }

        // Test with existing empty file
        try {
            File testFile = new File("testFileName");
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            Magic8Storage testStorage = new Magic8Storage("testFileName");

            TreeMap<Integer, Magic8Task> testTaskList = new TreeMap<Integer, Magic8Task>();

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2103T");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Software Engineering homework", null, testTags);

            testTaskList.put(1, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2010");
            testMagic8Task = new Magic8Task(2,
                    "Do Data Structures and Algorithms homework", null,
                    testTags);

            testTaskList.put(2, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("LSM1302");
            testMagic8Task = new Magic8Task(3, "Do Biology homework", null,
                    testTags);

            testTaskList.put(3, testMagic8Task);

            testStorage.writeToFile(4, testTaskList);
            File expected = new File("expected");
            CSVWriter cw = new CSVWriter(new FileWriter(expected));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[4];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "@null";
            line[3] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();

            BufferedReader brExpected = new BufferedReader(new FileReader(
                    expected));

            BufferedReader brActual = new BufferedReader(new FileReader(
                    testFile));
            String lineExpected;
            String lineActual;
            while ((lineExpected = brExpected.readLine()) == null) {
                lineActual = brActual.readLine();
                if (lineActual == null) {
                    fail("Actual file differs from expected file");
                } else {
                    assertTrue(lineExpected.equals(lineActual));
                }
            }
            brExpected.close();
            brActual.close();

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            File testFile = new File("testFileName");
            File expected = new File("expected");
            if (testFile.exists()) {
                testFile.delete();
            }
            if (expected.exists()) {
                expected.delete();
            }
        }

        // Test with existing non-empty file
        try {
            File testFile = new File("testFileName");
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            CSVWriter cw = new CSVWriter(new FileWriter(testFile));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[4];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "@null";
            line[3] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();
            Magic8Storage testStorage = new Magic8Storage("testFileName");

            TreeMap<Integer, Magic8Task> testTaskList = new TreeMap<Integer, Magic8Task>();

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2105");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Computer Networks homework", null, testTags);

            testTaskList.put(4, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2101");
            testMagic8Task = new Magic8Task(2,
                    "Do Effective Communication homework", null, testTags);

            testTaskList.put(5, testMagic8Task);

            testStorage.writeToFile(6, testTaskList);
            File expected = new File("expected");
            cw = new CSVWriter(new FileWriter(expected));
            line = new String[1];
            line[0] = "6";

            cw.writeNext(line);
            line = new String[4];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "@null";
            line[3] = "NUS Homework LSM1302";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "4";
            line[1] = "Do Computer Networks homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2105";
            cw.writeNext(line);
            line = new String[4];
            line[0] = "5";
            line[1] = "Do Effective Communication homework";
            line[2] = "@null";
            line[3] = "NUS Homework CS2101";
            cw.writeNext(line);
            cw.close();

            BufferedReader brExpected = new BufferedReader(new FileReader(
                    expected));

            BufferedReader brActual = new BufferedReader(new FileReader(
                    testFile));
            String lineExpected;
            String lineActual;
            while ((lineExpected = brExpected.readLine()) == null) {
                lineActual = brActual.readLine();
                if (lineActual == null) {
                    fail("Actual file differs from expected file");
                } else {
                    assertTrue(lineExpected.equals(lineActual));
                }
            }
            brExpected.close();
            brActual.close();

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            File testFile = new File("testFileName");
            File expected = new File("expected");
            if (testFile.exists()) {
                testFile.delete();
            }
            if (expected.exists()) {
                expected.delete();
            }
        }
    }

    @Test
    public void testGetId() {

    }

    @Test
    public void testGetTaskList() {

    }

}
