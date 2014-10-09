package fantasticfour.magiceight;

import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;

public class Magic8TaskTest {
    private static final String EXCEPTION_EXPECTED_NEGATIVE_ID = "Exception expected for negative id";
    private static final String EXCEPTION_EXPECTED_ZERO_ID = "Exception expected for zero id";
    private static final String EXCEPTION_EXPECTED_EMPTY_DESCRIPTION = "Exception expected for empty description";
    private static final String EXCEPTION_EXPECTED_EMPTY_TAG = "Exception expected for empty tag";
    private static final String EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG = "Exception expected for non-alphanumeric tag";

    private static final int TEST_NEGATIVE_ID = -1;
    private static final int TEST_ZERO_ID = 0;
    private static final int TEST_POSITIVE_ID = 1;
    private static final String TEST_DESCRIPTION = "Task description";
    private static final Date TEST_DATE = new Date(0);
    private static final HashSet<String> TEST_TAGS = new HashSet<String>(
            Arrays.asList("tag1", "tag2", "tag3"));

    private static final String TEST_EMPTY_STRING = "";
    private static final String TEST_NON_ALPHANUMERIC_TAG = "tag4!";

    private static DateFormat df = DateFormat.getDateInstance();
    private static final String TEST_DATE_STRING = df.format(TEST_DATE);

    @Test
    public void testMagic8Task() {
        // Test with valid parameters
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_DESCRIPTION, TEST_DATE,
                    TEST_TAGS);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test without id parameter
        try {
            new Magic8Task(TEST_DESCRIPTION, TEST_DATE, TEST_TAGS);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with zero id
        try {
            new Magic8Task(TEST_ZERO_ID, TEST_DESCRIPTION, TEST_DATE, TEST_TAGS);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with negative id
        try {
            new Magic8Task(TEST_NEGATIVE_ID, TEST_DESCRIPTION, TEST_DATE,
                    TEST_TAGS);
            fail(EXCEPTION_EXPECTED_NEGATIVE_ID);
        } catch (Exception e) {
        }

        // Test with null description
        try {
            new Magic8Task(TEST_POSITIVE_ID, null, TEST_DATE, TEST_TAGS);
            fail(EXCEPTION_EXPECTED_EMPTY_DESCRIPTION);
        } catch (Exception e) {
        }

        // Test with empty string description
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_EMPTY_STRING, TEST_DATE,
                    TEST_TAGS);
            fail(EXCEPTION_EXPECTED_EMPTY_DESCRIPTION);
        } catch (Exception e) {
        }

        // Test without date parameter
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_DESCRIPTION, null, TEST_TAGS);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test with null tags
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_EMPTY_STRING, TEST_DATE, null);
        } catch (Exception e) {
            e.getMessage();
        }

        // Test with tags containing null
        try {
            HashSet<String> tags = (HashSet<String>) TEST_TAGS.clone();
            tags.add(null);

            new Magic8Task(TEST_POSITIVE_ID, TEST_DESCRIPTION, TEST_DATE, tags);
            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (Exception e) {
        }

        // Test with tags containing empty string
        try {
            HashSet<String> tags = (HashSet<String>) TEST_TAGS.clone();
            tags.add(TEST_EMPTY_STRING);

            new Magic8Task(TEST_POSITIVE_ID, TEST_DESCRIPTION, TEST_DATE, tags);
            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (Exception e) {
        }

        // Test with tags containing non-alphanumeric tag
        try {
            HashSet<String> tags = (HashSet<String>) TEST_TAGS.clone();
            tags.add(TEST_NON_ALPHANUMERIC_TAG);

            new Magic8Task(TEST_POSITIVE_ID, TEST_DESCRIPTION, TEST_DATE, tags);
            fail(EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG);
        } catch (Exception e) {
        }
    }

    public Magic8Task getTestTask() {
        return new Magic8Task(TEST_POSITIVE_ID, TEST_DESCRIPTION, TEST_DATE,
                TEST_TAGS);
    }

    @Test
    public void testSetId() {
        Magic8Task task = getTestTask();

        // Test valid id
        try {
            task.setId(TEST_POSITIVE_ID);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test zero id
        try {
            task.setId(TEST_ZERO_ID);
            fail(EXCEPTION_EXPECTED_ZERO_ID);
        } catch (IllegalArgumentException e) {
        }

        // Test negative id
        try {
            task.setId(TEST_NEGATIVE_ID);
            fail(EXCEPTION_EXPECTED_NEGATIVE_ID);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSetDesc() {
        Magic8Task task = getTestTask();

        // Test valid description
        try {
            task.setDesc(TEST_DESCRIPTION);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test null description
        try {
            task.setDesc(null);
            fail(EXCEPTION_EXPECTED_EMPTY_DESCRIPTION);
        } catch (IllegalArgumentException e) {
        }

        // Test empty string description
        try {
            task.setDesc(TEST_EMPTY_STRING);
            fail(EXCEPTION_EXPECTED_EMPTY_DESCRIPTION);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSetTags() {
        Magic8Task task = getTestTask();

        // Test valid tags
        try {
            task.setTags(TEST_TAGS);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test null tags
        try {
            task.setTags(null);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test tags containing null
        try {
            HashSet<String> tags = (HashSet<String>) TEST_TAGS.clone();
            tags.add(null);

            task.setTags(tags);
            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (IllegalArgumentException e) {
        }

        // Test tags containing empty string
        try {
            HashSet<String> tags = (HashSet<String>) TEST_TAGS.clone();
            tags.add(null);

            task.setTags(tags);
            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (IllegalArgumentException e) {
        }

        // Test tags containing non-alphanumeric string
        try {
            HashSet<String> tags = (HashSet<String>) TEST_TAGS.clone();
            tags.add(TEST_NON_ALPHANUMERIC_TAG);

            task.setTags(tags);
            fail(EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testAddTag() {
        Magic8Task task = getTestTask();

        // Test valid tag
        try {
            task.addTag("tag4");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        // Test null tag
        try {
            task.addTag(null);
            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (IllegalArgumentException e) {
        }

        // Test empty tag
        try {
            task.addTag("TEST_EMPTY_STRING");
            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (IllegalArgumentException e) {
        }

        // Test non-alphanumeric tag
        try {
            task.addTag(TEST_NON_ALPHANUMERIC_TAG);
            fail(EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG);
        } catch (Exception e) {
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
