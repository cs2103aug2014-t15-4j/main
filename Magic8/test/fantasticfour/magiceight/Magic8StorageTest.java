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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

//@author A0113605U
public class Magic8StorageTest {
    private static final String EXPECTED_FILE_NAME = "expected";
    private static final String TEST_FILE_NAME = "storageTestFile";
    private static final String SUFFIX = "tmp";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss");
    private static final String START_DATE = "01/01/2014 00:00:00";
    private static final String END_DATE = "12/12/2014 00:00:00";
    private static Calendar startCalender = new GregorianCalendar();
    private static Calendar endCalender = new GregorianCalendar();

    @Test
    public void testMagic8Storage() {
        try {
            startCalender.setTime(DATE_FORMAT.parse("01/01/2014 00:00:00"));
            endCalender.setTime(DATE_FORMAT.parse("12/12/2014 00:00:00"));
        } catch (ParseException e) {
            fail(e.getMessage());
        }

        // Test with valid parameters
        try {
            File testFile = new File(generateRandomString());
            new Magic8Storage(testFile.getName());

            testFile.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with file that does not exist
        try {
            File testFile = new File(generateRandomString());
            if (testFile.exists()) {
                testFile.delete();
            }
            new Magic8Storage(testFile.getName());
            testFile.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with existing empty file
        try {
            File testFile = new File(generateRandomString());
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            new Magic8Storage(testFile.getName());
            testFile.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with existing non-empty file
        try {
            File testFile = new File(generateRandomString());
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            CSVWriter cw = new CSVWriter(new FileWriter(testFile));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[6];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();

            Magic8Storage testStorage = new Magic8Storage(testFile.getName());
            TreeMap<Integer, Magic8Task> testTaskList = testStorage
                    .getTaskList();

            assertTrue(testTaskList.size() == 3);

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2103T");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Software Engineering homework", startCalender,
                    endCalender, testTags);

            assertTrue(testTaskList.get(1).equals(testMagic8Task));

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2010");
            testMagic8Task = new Magic8Task(2,
                    "Do Data Structures and Algorithms homework",
                    startCalender, endCalender, testTags);

            assertTrue(testTaskList.get(2).equals(testMagic8Task));

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("LSM1302");
            testMagic8Task = new Magic8Task(3, "Do Biology homework",
                    startCalender, endCalender, testTags);

            assertTrue(testTaskList.get(3).equals(testMagic8Task));
            testFile.delete();
        } catch (AssertionError | IOException | ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testWriteToFile() {
        CSVParser cp = new CSVParser();

        try {
            startCalender.setTime(DATE_FORMAT.parse("01/01/2014 00:00:00"));
            endCalender.setTime(DATE_FORMAT.parse("12/12/2014 00:00:00"));
        } catch (ParseException e) {
            fail(e.getMessage());
        }

        // Test with file that does not exist
        try {
            File testFile = new File(generateRandomString());

            Magic8Storage testStorage = new Magic8Storage(testFile.getName());

            TreeMap<Integer, Magic8Task> testTaskList = new TreeMap<Integer, Magic8Task>();

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2103T");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Software Engineering homework", startCalender,
                    endCalender, testTags);

            testTaskList.put(1, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2010");
            testMagic8Task = new Magic8Task(2,
                    "Do Data Structures and Algorithms homework",
                    startCalender, endCalender, testTags);

            testTaskList.put(2, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("LSM1302");
            testMagic8Task = new Magic8Task(3, "Do Biology homework",
                    startCalender, endCalender, testTags);

            testTaskList.put(3, testMagic8Task);

            testStorage.writeToFile(4, testTaskList);

            File expected = new File(generateRandomString());

            CSVWriter cw = new CSVWriter(new FileWriter(expected));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[6];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();

            BufferedReader brExpected = new BufferedReader(new FileReader(
                    expected));

            BufferedReader brActual = new BufferedReader(new FileReader(
                    testFile));
            String lineExpected;
            String lineActual;
            while ((lineExpected = brExpected.readLine()) != null) {
                lineActual = brActual.readLine();
                if (lineActual == null) {
                    fail("Actual file differs from expected file");
                } else {
                    if (cp.parseLine(lineExpected).length == 1) {

                        assertTrue(lineExpected.equals(lineActual));
                    } else {
                        Magic8Task actualTask = Magic8Storage
                                .stringArrayToMagic8Task(cp
                                        .parseLine(lineActual));
                        Magic8Task expectedTask = Magic8Storage
                                .stringArrayToMagic8Task(cp
                                        .parseLine(lineExpected));
                        assertTrue(expectedTask.equals(actualTask));
                    }
                }
            }
            brExpected.close();
            brActual.close();

            testFile.delete();
            expected.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with existing empty file
        try {
            File testFile = new File(generateRandomString());

            Magic8Storage testStorage = new Magic8Storage(testFile.getName());

            TreeMap<Integer, Magic8Task> testTaskList = new TreeMap<Integer, Magic8Task>();

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2103T");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Software Engineering homework", startCalender,
                    endCalender, testTags);

            testTaskList.put(1, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2010");
            testMagic8Task = new Magic8Task(2,
                    "Do Data Structures and Algorithms homework",
                    startCalender, endCalender, testTags);

            testTaskList.put(2, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("LSM1302");
            testMagic8Task = new Magic8Task(3, "Do Biology homework",
                    startCalender, endCalender, testTags);

            testTaskList.put(3, testMagic8Task);

            testStorage.writeToFile(4, testTaskList);

            File expected = new File(generateRandomString());

            CSVWriter cw = new CSVWriter(new FileWriter(expected));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[6];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();

            BufferedReader brExpected = new BufferedReader(new FileReader(
                    expected));

            BufferedReader brActual = new BufferedReader(new FileReader(
                    testFile));
            String lineExpected;
            String lineActual;
            while ((lineExpected = brExpected.readLine()) != null) {
                lineActual = brActual.readLine();
                if (lineActual == null) {
                    fail("Actual file differs from expected file");
                } else {
                    if (cp.parseLine(lineExpected).length == 1) {
                        assertTrue(lineExpected.equals(lineActual));
                    } else {
                        Magic8Task actualTask = Magic8Storage
                                .stringArrayToMagic8Task(cp
                                        .parseLine(lineActual));
                        Magic8Task expectedTask = Magic8Storage
                                .stringArrayToMagic8Task(cp
                                        .parseLine(lineExpected));
                        assertTrue(expectedTask.equals(actualTask));
                    }
                }
            }
            brExpected.close();
            brActual.close();

            testFile.delete();
            expected.delete();

        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with existing non-empty file
        try {
            File testFile = new File(generateRandomString());

            CSVWriter cw = new CSVWriter(new FileWriter(testFile));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[6];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();
            Magic8Storage testStorage = new Magic8Storage(testFile.getName());

            TreeMap<Integer, Magic8Task> testTaskList = testStorage
                    .getTaskList();

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2105");
            Magic8Task testMagic8Task = new Magic8Task(4,
                    "Do Computer Networks homework", startCalender,
                    endCalender, testTags);

            testTaskList.put(4, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2101");
            testMagic8Task = new Magic8Task(5,
                    "Do Effective Communication homework", startCalender,
                    endCalender, testTags);

            testTaskList.put(5, testMagic8Task);

            testStorage.writeToFile(6, testTaskList);

            File expected = new File(generateRandomString());

            cw = new CSVWriter(new FileWriter(expected));
            line = new String[1];
            line[0] = "6";

            cw.writeNext(line);
            line = new String[6];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework LSM1302";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "4";
            line[1] = "Do Computer Networks homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2105";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "5";
            line[1] = "Do Effective Communication homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2101";
            cw.writeNext(line);
            cw.close();

            BufferedReader brExpected = new BufferedReader(new FileReader(
                    expected));

            BufferedReader brActual = new BufferedReader(new FileReader(
                    testFile));
            String lineExpected;
            String lineActual;
            while ((lineExpected = brExpected.readLine()) != null) {
                lineActual = brActual.readLine();
                if (lineActual == null) {
                    fail("Actual file differs from expected file");
                } else {
                    if (cp.parseLine(lineExpected).length == 1) {
                        assertTrue(lineExpected.equals(lineActual));
                    } else {
                        Magic8Task actualTask = Magic8Storage
                                .stringArrayToMagic8Task(cp
                                        .parseLine(lineActual));
                        Magic8Task expectedTask = Magic8Storage
                                .stringArrayToMagic8Task(cp
                                        .parseLine(lineExpected));
                        assertTrue(expectedTask.equals(actualTask));
                    }
                }
            }
            brExpected.close();
            brActual.close();

            testFile.delete();
            expected.delete();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetId() {
        // test get id for empty file
        try {
            File testFile = new File(generateRandomString());
            Magic8Storage testStorage = new Magic8Storage(testFile.getName());
            assertEquals(1, testStorage.getId());
            testFile.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }

        // test get id for non-empty file
        try {
            File testFile = new File(generateRandomString());
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            CSVWriter cw = new CSVWriter(new FileWriter(testFile));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[6];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();
            Magic8Storage testStorage = new Magic8Storage(testFile.getName());
            assertEquals(4, testStorage.getId());
            testFile.delete();
        } catch (IOException | ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetTaskList() {

        // test for file that does not exist
        try {
            File testFile = new File(generateRandomString());
            Magic8Storage testStorage = new Magic8Storage(testFile.getName());
            assertTrue(testStorage.getTaskList().isEmpty());
            testFile.delete();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // test for empty file
        try {
            File testFile = new File(generateRandomString());
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            Magic8Storage testStorage = new Magic8Storage(testFile.getName());
            assertTrue(testStorage.getTaskList().isEmpty());
            testFile.delete();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // test for non-empty file
        try {
            File testFile = new File(generateRandomString());
            if (testFile.exists()) {
                testFile.delete();
            }
            testFile.createNewFile();
            CSVWriter cw = new CSVWriter(new FileWriter(testFile));
            String[] line = new String[1];
            line[0] = "4";

            cw.writeNext(line);
            line = new String[6];
            line[0] = "1";
            line[1] = "Do Software Engineering homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2103T";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "2";
            line[1] = "Do Data Structures and Algorithms homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework CS2010";
            cw.writeNext(line);
            line = new String[6];
            line[0] = "3";
            line[1] = "Do Biology homework";
            line[2] = "false";
            line[3] = START_DATE;
            line[4] = END_DATE;
            line[5] = "NUS Homework LSM1302";
            cw.writeNext(line);
            cw.close();
            
            TreeMap<Integer, Magic8Task> expectedTaskList = new TreeMap<Integer, Magic8Task>();

            HashSet<String> testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2103T");
            Magic8Task testMagic8Task = new Magic8Task(1,
                    "Do Software Engineering homework", startCalender,
                    endCalender, testTags);

            expectedTaskList.put(1, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("CS2010");
            testMagic8Task = new Magic8Task(2,
                    "Do Data Structures and Algorithms homework",
                    startCalender, endCalender, testTags);

            expectedTaskList.put(2, testMagic8Task);

            testTags = new HashSet<String>();
            testTags.add("NUS");
            testTags.add("Homework");
            testTags.add("LSM1302");
            testMagic8Task = new Magic8Task(3, "Do Biology homework",
                    startCalender, endCalender, testTags);

            expectedTaskList.put(3, testMagic8Task);

            Magic8Storage testStorage = new Magic8Storage(testFile.getName());
            TreeMap<Integer, Magic8Task> actualTaskList = testStorage
                    .getTaskList();
            assertEquals(expectedTaskList.size(), actualTaskList.size());
            for (Map.Entry<Integer, Magic8Task> entry : expectedTaskList
                    .entrySet()) {
                assertEquals(entry.getValue(),
                        actualTaskList.get(entry.getKey()));
            }

            testFile.delete();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String generateRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit
                    + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
}
