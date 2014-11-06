package fantasticfour.magiceight;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

//@author A0078774L
public class Magic8Task implements Magic8TaskInterface {
    private static final String MSG_NEGATIVE_ID = "id cannot be negative";
    private static final String MSG_ZERO_ID = "id cannot be zero";
    private static final String MSG_NULL_DESCRIPTION = "description cannot be null";
    private static final String MSG_EMPTY_DESCRIPTION = "description cannot be empty";
    private static final String MSG_NULL_TAG = "tag cannot be null";
    private static final String MSG_EMPTY_TAG = "tag cannot be empty";
    private static final String MSG_NON_ALPHANUMERIC_TAG = "tag must be alphanumeric";

    private int id;
    private String desc;
    private boolean isDone;
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
        setDone(false);
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
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void setDone(boolean isDone) {
        this.isDone = isDone;
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
