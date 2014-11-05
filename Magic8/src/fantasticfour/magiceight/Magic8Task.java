package fantasticfour.magiceight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class Magic8Task implements Magic8TaskInterface {
    private static final String STRING_EMPTY = "";
    private static final String STRING_PARSED_EMPTY = "@empty";
    private static final String STRING_PARSED_NULL = "@null";
    private static final String FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";
    private static final int INDEX_TAGS = 4;
    private static final int INDEX_END_TIME = 3;
    private static final int INDEX_START_TIME = 2;
    private static final int INDEX_DESC = 1;
    private static final int INDEX_ID = 0;
    private static final int NUM_FIELDS = 5;
    private static final String MSG_NEGATIVE_ID = "id cannot be negative";
    private static final String MSG_ZERO_ID = "id cannot be zero";
    private static final String MSG_NULL_DESCRIPTION = "description cannot be null";
    private static final String MSG_EMPTY_DESCRIPTION = "description cannot be empty";
    private static final String MSG_NULL_TAG = "tag cannot be null";
    private static final String MSG_EMPTY_TAG = "tag cannot be empty";
    private static final String MSG_NON_ALPHANUMERIC_TAG = "tag must be alphanumeric";
    private static final String MSG_INSUFFICIENT_ARGUMENTS = "Error insufficient arguments";

    private int id;
    private String desc;
    private Calendar startTime;
    private Calendar endTime;
    private HashSet<String> tags;

    public Magic8Task(int id, String desc, Calendar startTime,
            Calendar endTime, HashSet<String> tags)
            throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(MSG_NEGATIVE_ID);
        }

        this.id = id;
        setDesc(desc);
        setStartTime(startTime);
        setEndTime(endTime);
        setTags(tags);
    }

    public Magic8Task(String desc, Calendar startTime, Calendar endTime,
            HashSet<String> tags) throws IllegalArgumentException {
        this(0, desc, startTime, endTime, tags);
    }

    public Magic8Task(String desc, Calendar startTime, Calendar endTime)
            throws IllegalArgumentException {
        this(0, desc, startTime, endTime, null);
    }

    public Magic8Task(String desc, Calendar endTime, HashSet<String> tags)
            throws IllegalArgumentException {
        this(0, desc, null, endTime, tags);
    }

    public Magic8Task(String desc, Calendar endTime) {
        this(0, desc, null, endTime, null);
    }

    public Magic8Task(String desc, HashSet<String> tags)
            throws IllegalArgumentException {
        this(0, desc, null, null, tags);
    }

    public Magic8Task(String desc) throws IllegalArgumentException {
        this(0, desc, null, null, null);
    }

    public Magic8Task(Magic8Task task) {
        this(task.getId(), task.getDesc(), task.getStartTime(), task
                .getEndTime(), task.getTags());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(MSG_NEGATIVE_ID);
        }
        if (id == 0) {
            throw new IllegalArgumentException(MSG_ZERO_ID);
        }

        this.id = id;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) throws IllegalArgumentException {
        if (desc == null) {
            throw new IllegalArgumentException(MSG_NULL_DESCRIPTION);
        }
        if (desc.isEmpty()) {
            throw new IllegalArgumentException(MSG_EMPTY_DESCRIPTION);
        }

        this.desc = desc;
    }

    @Override
    public Calendar getStartTime() {
        if (startTime == null) {
            return startTime;
        }

        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(startTime.getTimeInMillis());
        return cal;
    }

    @Override
    public void setStartTime(Calendar startTime) {
        if (startTime == null) {
            this.startTime = startTime;
        } else {
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(startTime.getTimeInMillis());
            this.startTime = cal;
        }
    }

    @Override
    public Calendar getEndTime() {
        if (endTime == null) {
            return endTime;
        }

        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(endTime.getTimeInMillis());
        return cal;
    }

    @Override
    public void setEndTime(Calendar endTime) {
        if (endTime == null) {
            this.endTime = endTime;
        } else {
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(endTime.getTimeInMillis());
            this.endTime = cal;
        }
    }

    @Override
    public HashSet<String> getTags() {
        return new HashSet<String>(tags);
    }

    @Override
    public void setTags(HashSet<String> tags) throws IllegalArgumentException {
        if (tags == null) {
            this.tags = new HashSet<String>();
        } else {
            for (String tag : tags) {
                validateTag(tag);
            }

            this.tags = new HashSet<String>(tags);
        }
    }

    @Override
    public void addTag(String tag) throws IllegalArgumentException {
        validateTag(tag);

        tags.add(tag);
    }

    @Override
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    @Override
    public String[] toStringArray() {
        String[] stringArray = new String[NUM_FIELDS];
        stringArray[INDEX_ID] = Integer.toString(id);
        stringArray[INDEX_DESC] = desc;
        DateFormat df = new SimpleDateFormat(FORMAT_DATE);

        if (startTime == null) {
            stringArray[INDEX_START_TIME] = STRING_PARSED_NULL;
        } else {
            stringArray[INDEX_START_TIME] = df.format(startTime.getTime());
        }

        if (endTime == null) {
            stringArray[INDEX_END_TIME] = STRING_PARSED_NULL;
        } else {
            stringArray[INDEX_END_TIME] = df.format(endTime.getTime());
        }

        if (tags.size() == 0) {
            stringArray[INDEX_TAGS] = STRING_PARSED_EMPTY;
        } else {
            stringArray[INDEX_TAGS] = STRING_EMPTY;
            for (String tag : tags) {
                stringArray[INDEX_TAGS] += " " + tag;
            }
            stringArray[INDEX_TAGS] = stringArray[INDEX_TAGS].trim();
        }

        return stringArray;
    }

    public static Magic8Task stringArrayToMagic8Task(String[] stringArray)
            throws IllegalArgumentException, ParseException {

        DateFormat df = new SimpleDateFormat(FORMAT_DATE);

        if (stringArray.length >= NUM_FIELDS) {
            int id = Integer.parseInt(stringArray[INDEX_ID]);
            String desc = stringArray[INDEX_DESC];
            Calendar startTime = null;
            Calendar endTime = null;
            HashSet<String> tags;

            if (!stringArray[INDEX_START_TIME].equals(STRING_PARSED_NULL)) {
                startTime = Calendar.getInstance();
                startTime.setTime(df.parse(stringArray[INDEX_START_TIME]));
            }

            if (!stringArray[INDEX_END_TIME].equals(STRING_PARSED_NULL)) {
                endTime = Calendar.getInstance();
                endTime.setTime(df.parse(stringArray[INDEX_END_TIME]));
            }

            if (stringArray[INDEX_TAGS].equals(STRING_PARSED_NULL)) {
                tags = null;
            } else if (stringArray[INDEX_TAGS].equals(STRING_PARSED_EMPTY)) {
                tags = new HashSet<String>();
            } else {
                tags = new HashSet<String>();
                String[] tagStrings = stringArray[INDEX_TAGS].split("\\s+");
                for (String tagString : tagStrings) {
                    tags.add(tagString);
                }
            }

            Magic8Task task = new Magic8Task(id, desc, startTime, endTime, tags);
            return task;
        } else {
            throw new IllegalArgumentException(MSG_INSUFFICIENT_ARGUMENTS);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Magic8Task)) {
            return false;
        } else {
            Magic8Task other = (Magic8Task) obj;

            // Check id
            if (getId() != other.getId()) {
                return false;
            }
            // Check description
            if (!getDesc().equals(other.getDesc())) {
                return false;
            }
            // Check start time
            Calendar d1 = getStartTime();
            Calendar d2 = other.getStartTime();

            if (d1 == null && d2 != null) {
                return false;
            }
            if (d1 != null && d2 == null) {
                return false;
            }
            if (d1 != null && d2 != null && !d1.equals(d2)) {
                return false;
            }
            // Check end time
            d1 = getEndTime();
            d2 = other.getEndTime();

            if (d1 == null && d2 != null) {
                return false;
            }
            if (d1 != null && d2 == null) {
                return false;
            }
            if (d1 != null && d2 != null && !d1.equals(d2)) {
                return false;
            }

            // Check tags
            HashSet<String> t1 = getTags();
            HashSet<String> t2 = getTags();

            if (getTags().size() != other.getTags().size()) {
                return false;
            }

            for (String tag : t1) {
                if (!t2.contains(tag)) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    public int compareTo(Magic8Task task) {
        if (getEndTime() == null) {
            return 1;
        }
        if (task.getEndTime() == null) {
            return -1;
        }

        return getEndTime().compareTo(task.getEndTime());
    }

    private static void validateTag(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException(MSG_NULL_TAG);
        }
        if (tag.isEmpty()) {
            throw new IllegalArgumentException(MSG_EMPTY_TAG);
        }
        if (!tag.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException(MSG_NON_ALPHANUMERIC_TAG);
        }
    }
}
