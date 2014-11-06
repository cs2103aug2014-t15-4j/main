package fantasticfour.magiceight;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

//@author A0078774L
public class Magic8TaskTest {
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
            TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING, TEST_DATE,
            TEST_DATE, TEST_TAGS);

    @Test
    public void testMagic8Task() {
        // Test with valid parameters
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, TEST_DATE, TEST_TAGS);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test without id parameter
        try {
            new Magic8Task(TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_TAGS);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with zero id
        try {
            new Magic8Task(0, TEST_NON_ALPHANUMERIC_STRING, TEST_DATE,
                    TEST_DATE, TEST_TAGS);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with negative id
        try {
            new Magic8Task(TEST_NEGATIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, TEST_DATE, TEST_TAGS);
            fail(EXCEPTION_EXPECTED_NEGATIVE_ID);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null description
        try {
            new Magic8Task(TEST_POSITIVE_ID, null, TEST_DATE, TEST_DATE,
                    TEST_TAGS);
            fail(EXCEPTION_EXPECTED_NULL_DESCRIPTION);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with empty string description
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_EMPTY_STRING, TEST_DATE,
                    TEST_DATE, TEST_TAGS);
            fail(EXCEPTION_EXPECTED_EMPTY_DESCRIPTION);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null start time
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    null, TEST_DATE, TEST_TAGS);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null end time
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, null, TEST_TAGS);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null tags
        try {
            new Magic8Task(TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, TEST_DATE, null);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with tags containing null string tag
        try {
            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(null);

            new Magic8Task(TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, TEST_DATE, tags);
            fail(EXCEPTION_EXPECTED_NULL_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with tags containing empty string tag
        try {
            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(TEST_EMPTY_STRING);

            new Magic8Task(TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, TEST_DATE, tags);
            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with tags containing non-alphanumeric tag
        try {
            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(TEST_NON_ALPHANUMERIC_STRING);

            new Magic8Task(TEST_POSITIVE_ID, TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, TEST_DATE, tags);
            fail(EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testEquals() {
        // Test reflexivity
        try {
            assert TEST_TASK.equals(TEST_TASK);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test symmetry
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);

            assert task.equals(TEST_TASK);
            assert TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test transitivity
        try {
            Magic8Task task1 = new Magic8Task(TEST_TASK);
            Magic8Task task2 = new Magic8Task(task1);

            assert TEST_TASK.equals(task1);
            assert task1.equals(task2);
            assert TEST_TASK.equals(task2);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test consistency
        try {
            assert TEST_TASK.equals(TEST_TASK);
            assert TEST_TASK.equals(TEST_TASK);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null
        try {
            assert !TEST_TASK.equals(null);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with different id
        try {
            Magic8Task task = new Magic8Task(TEST_NON_ALPHANUMERIC_STRING,
                    TEST_DATE, TEST_TAGS);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with different description
        try {
            Magic8Task task = new Magic8Task(TEST_POSITIVE_ID,
                    "different description", TEST_DATE, TEST_DATE, TEST_TAGS);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null start time
        try {
            Magic8Task task = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, null, TEST_DATE, TEST_TAGS);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null end time
        try {
            Magic8Task task = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, null, TEST_TAGS);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with different start time
        try {
            Magic8Task task = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, new GregorianCalendar(),
                    TEST_DATE, TEST_TAGS);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with different end time
        try {
            Magic8Task task = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE,
                    new GregorianCalendar(), TEST_TAGS);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with null tags
        try {
            Magic8Task task = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_DATE, null);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test with different tags
        try {
            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(TEST_ALPHANUMERIC_STRING);
            Magic8Task task = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_DATE, tags);

            assert !task.equals(TEST_TASK);
            assert !TEST_TASK.equals(task);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testSetId() {
        // Test valid id
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            int id = Integer.MAX_VALUE;
            task.setId(id);

            Magic8Task expectedTask = new Magic8Task(id,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_DATE,
                    TEST_TAGS);

            assert task.equals(expectedTask);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test zero id
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(TEST_ZERO_ID);

            fail(EXCEPTION_EXPECTED_ZERO_ID);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test negative id
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setId(TEST_NEGATIVE_ID);

            fail(EXCEPTION_EXPECTED_NEGATIVE_ID);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testSetDesc() {
        // Test valid description
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            String desc = "Original cheez doodles the CHEEZIER snack";
            task.setDesc(desc);

            Magic8Task expectedTask = new Magic8Task(TEST_POSITIVE_ID, desc,
                    TEST_DATE, TEST_DATE, TEST_TAGS);

            assert task.equals(expectedTask);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test null string description
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setDesc(null);

            fail(EXCEPTION_EXPECTED_NULL_DESCRIPTION);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test empty string description
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setDesc(TEST_EMPTY_STRING);

            fail(EXCEPTION_EXPECTED_EMPTY_DESCRIPTION);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testSetStartTime() {
        // Test valid date
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            Calendar date = new GregorianCalendar();
            task.setStartTime(date);

            Magic8Task expectedTask = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, date, TEST_DATE, TEST_TAGS);

            assert task.equals(expectedTask);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testSetEndTime() {
        // Test valid date
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            Calendar date = new GregorianCalendar();
            task.setEndTime(date);

            Magic8Task expectedTask = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, date, TEST_TAGS);

            assert task.equals(expectedTask);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testSetTags() {
        // Test valid tags
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            HashSet<String> tags = new HashSet<String>(Arrays.asList("sugar",
                    "spice"));
            task.setTags(tags);

            Magic8Task expectedTask = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_DATE, tags);

            assert task.equals(expectedTask);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test null tags
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.setTags(null);

            Magic8Task expectedTask = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_DATE, null);

            assert task.equals(expectedTask);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test tags containing null string tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(null);
            task.setTags(tags);

            fail(EXCEPTION_EXPECTED_NULL_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test tags containing empty string tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(TEST_EMPTY_STRING);
            task.setTags(tags);

            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test tags containing non-alphanumeric string tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(TEST_NON_ALPHANUMERIC_STRING);
            task.setTags(tags);

            fail(EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testAddTag() {
        // Test valid tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            String tag = "snack";
            task.addTag(tag);

            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.add(tag);
            Magic8Task expectedTask = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_DATE, tags);

            assert task.equals(expectedTask);
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test null string tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.addTag(null);

            fail(EXCEPTION_EXPECTED_NULL_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test empty string tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.addTag(TEST_EMPTY_STRING);

            fail(EXCEPTION_EXPECTED_EMPTY_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }

        // Test non-alphanumeric string tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            task.addTag(EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG);

            fail(EXCEPTION_EXPECTED_NON_ALPHANUMERIC_TAG);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testRemoveTag() {
        // Test valid tag
        try {
            Magic8Task task = new Magic8Task(TEST_TASK);
            String tag = "tag1";
            task.removeTag(tag);

            HashSet<String> tags = new HashSet<String>(TEST_TAGS);
            tags.remove(tag);

            Magic8Task expectedTask = new Magic8Task(TEST_POSITIVE_ID,
                    TEST_NON_ALPHANUMERIC_STRING, TEST_DATE, TEST_DATE, tags);

            assert task.equals(expectedTask);
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
