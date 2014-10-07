package fantasticfour.magiceight;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.HashSet;

import org.junit.Test;

import java.util.Date;

public class Magic8TaskTest {
    private static final String FAILURE_EXCEPTION_EXPECTED_FOR_NULL_TAG_LIST = "Exception expected for null tag list";
    private static final String FAILURE_EXCEPTION_EXPECTED_FOR_ID_ZERO = "Exception expected for id = 0";
    private static final String FAILURE_EXCEPTION_EXPECTED_FOR_EMPTY_TAGS = "Exception expected for empty tags";
    private static final String FAILURE_EXCEPTION_EXPECTED_FOR_NULL_TAGS = "Exception expected for null tags";
    private static final String FAILURE_EXCEPTION_EXPECTED_FOR_NON_ALPHANUMERIC_TAG_INPUT = "Exception expected for non-alphanumeric tag input";
    private static final String FAILURE_EXCEPTION_EXPECTED_FOR_EMPTY_DESCRIPTION_INPUT = "Exception expected for empty description input";
    private static final String FAILURE_EXCEPTION_EXPECTED_FOR_NULL_DESCRIPTION_INPUT = "Exception expected for null description input";
    private static final String FAILURE_EXCEPTION_EXPCETED_FOR_NEGATIVE_ID_INPUT = "Exception expected for negative id input";

    private static final int TEST_POSITIVE_ID = 1;
    private static final int TEST_NEGATIVE_ID = -1;
    private static final String TEST_TASK_DESCRIPTION = "Task description";
    private static final Date TEST_DATE = new Date(0);
    private static DateFormat df = DateFormat.getDateInstance();
    private static final String TEST_DATE_STRING = df.format(TEST_DATE);

    @Test
    public void testMagic8Task() {
        HashSet<String> testTags = new HashSet<String>();
        testTags.add("tag1");
        testTags.add("tag2");
        testTags.add("tag3");

        try {
            new Magic8Task(0, TEST_TASK_DESCRIPTION, TEST_DATE, testTags);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Magic8Task(0, TEST_TASK_DESCRIPTION, null, testTags);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            new Magic8Task(0, TEST_TASK_DESCRIPTION, TEST_DATE, null);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NULL_TAG_LIST);
        } catch (Exception e) {

        }

        try {
            new Magic8Task(TEST_NEGATIVE_ID, TEST_TASK_DESCRIPTION, TEST_DATE,
                    testTags);
            fail(FAILURE_EXCEPTION_EXPCETED_FOR_NEGATIVE_ID_INPUT);
        } catch (IllegalArgumentException e) {
        }

        try {
            new Magic8Task(0, null, TEST_DATE, testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NULL_DESCRIPTION_INPUT);
        } catch (IllegalArgumentException e) {
        }

        try {
            new Magic8Task(0, "", TEST_DATE, testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_EMPTY_DESCRIPTION_INPUT);
        } catch (IllegalArgumentException e) {
        }

        try {
            testTags.add("tag!");
            new Magic8Task(0, "", TEST_DATE, testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NON_ALPHANUMERIC_TAG_INPUT);
        } catch (IllegalArgumentException e) {
            testTags.remove("tag!");
        }

        try {
            testTags.add(null);
            new Magic8Task(0, "", TEST_DATE, testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NULL_TAGS);
        } catch (IllegalArgumentException e) {
            testTags.remove(null);
        }

        try {
            testTags.add("");
            new Magic8Task(0, "", TEST_DATE, testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_EMPTY_TAGS);
        } catch (IllegalArgumentException e) {
            testTags.remove("");
        }
    }

    @Test
    public void testSetId() {
        HashSet<String> testTags = new HashSet<String>();
        testTags.add("tag1");
        testTags.add("tag2");
        testTags.add("tag3");
        Magic8Task testTask = new Magic8Task(0, TEST_TASK_DESCRIPTION,
                TEST_DATE, testTags);

        try {
            testTask.setId(TEST_POSITIVE_ID);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            testTask.setId(0);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_ID_ZERO);
        } catch (Exception e) {

        }

        try {
            testTask.setId(TEST_NEGATIVE_ID);
            fail(FAILURE_EXCEPTION_EXPCETED_FOR_NEGATIVE_ID_INPUT);
        } catch (Exception e) {
        }
    }

    @Test
    public void testSetDesc() {
        HashSet<String> testTags = new HashSet<String>();
        testTags.add("tag1");
        testTags.add("tag2");
        testTags.add("tag3");
        Magic8Task testTask = new Magic8Task(0, TEST_TASK_DESCRIPTION,
                TEST_DATE, testTags);

        try {
            testTask.setDesc(TEST_TASK_DESCRIPTION);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            testTask.setDesc(null);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NULL_DESCRIPTION_INPUT);
        } catch (Exception e) {
        }

        try {
            testTask.setDesc(null);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_EMPTY_DESCRIPTION_INPUT);
        } catch (Exception e) {
        }
    }

    @Test
    public void testSetTags() {
        HashSet<String> testTags = new HashSet<String>();
        testTags.add("tag1");
        testTags.add("tag2");
        testTags.add("tag3");
        Magic8Task testTask = new Magic8Task(0, TEST_TASK_DESCRIPTION,
                TEST_DATE, testTags);
        try {
            testTask.setTags(testTags);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            testTags.add("tag!");
            testTask.setTags(testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NON_ALPHANUMERIC_TAG_INPUT);
        } catch (Exception e) {
            testTask.removeTag("tag!");
        }

        try {
            testTags.add(null);
            testTask.setTags(testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NULL_TAGS);
        } catch (Exception e) {
            testTask.removeTag(null);
        }

        try {
            testTags.add("");
            testTask.setTags(testTags);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_EMPTY_TAGS);
        } catch (Exception e) {
            testTask.removeTag("");
        }

    }

    @Test
    public void testAddTag() {
        HashSet<String> testTags = new HashSet<String>();
        testTags.add("tag1");
        testTags.add("tag2");
        testTags.add("tag3");
        Magic8Task testTask = new Magic8Task(0, TEST_TASK_DESCRIPTION,
                TEST_DATE, testTags);
        try {
            testTask.addTag("tag4");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            testTask.addTag("tag!");
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NON_ALPHANUMERIC_TAG_INPUT);
        } catch (Exception e) {
            testTask.removeTag("tag!");
        }

        try {
            testTask.addTag(null);
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_NULL_TAGS);
        } catch (Exception e) {
            testTask.removeTag(null);
        }

        try {
            testTask.addTag("");
            fail(FAILURE_EXCEPTION_EXPECTED_FOR_EMPTY_TAGS);
        } catch (Exception e) {
            testTask.removeTag("");
        }
    }

    /*@Test
    public void testToStringString() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testParse() {
        fail("Not yet implemented"); // TODO
    }*/
}
