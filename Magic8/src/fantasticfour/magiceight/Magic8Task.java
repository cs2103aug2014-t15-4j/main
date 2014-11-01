package fantasticfour.magiceight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

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
    private Date startTime;
    private Date endTime;
    private HashSet<String> tags;

    public Magic8Task(int id, String desc, Date startTime, Date endTime,
            HashSet<String> tags) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(MSG_NEGATIVE_ID);
        }

        this.id = id;
        setDesc(desc);
        setStartTime(startTime);
        setEndTime(endTime);
        setTags(tags);
    }

    public Magic8Task(String desc, Date startTime, Date endTime,
            HashSet<String> tags) throws IllegalArgumentException {
        this(0, desc, startTime, endTime, tags);
    }

    public Magic8Task(String desc, Date startTime, Date endTime)
            throws IllegalArgumentException {
        this(0, desc, startTime, endTime, null);
    }

    public Magic8Task(String desc, Date endTime, HashSet<String> tags)
            throws IllegalArgumentException {
        this(0, desc, null, endTime, tags);
    }

    public Magic8Task(String desc, Date endTime) {
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
    public Date getStartTime() {
        if (startTime == null) {
            return startTime;
        }

        return new Date(startTime.getTime());
    }

    @Override
    public void setStartTime(Date startTime) {
        if (startTime == null) {
            this.startTime = startTime;
        } else {
            this.startTime = new Date(startTime.getTime());
        }
    }

    @Override
    public Date getEndTime() {
        if (endTime == null) {
            return endTime;
        }

        return new Date(endTime.getTime());
    }

    @Override
    public void setEndTime(Date endTime) {
        if (endTime == null) {
            this.endTime = endTime;
        } else {
            this.endTime = new Date(endTime.getTime());
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
        String[] stringArray = new String[5];
        stringArray[0] = Integer.toString(id);
        stringArray[1] = desc;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        if (startTime == null) {
            stringArray[2] = "@null";
        } else {
            stringArray[2] = df.format(startTime);
        }

        if (endTime == null) {
            stringArray[3] = "@null";
        } else {
            stringArray[3] = df.format(endTime);
        }

        if (tags == null) {
            stringArray[4] = "@null";
        } else if (tags.size() == 0) {
            stringArray[4] = "@empty";
        } else {
            stringArray[4] = "";
            for (String tag : tags) {
                stringArray[3] += " " + tag;
            }
            stringArray[4] = stringArray[4].trim();
        }

        return stringArray;
    }

    public static Magic8Task stringArrayToMagic8Task(String[] stringArray)
            throws IllegalArgumentException, ParseException {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (stringArray.length >= 4) {
            int id = Integer.parseInt(stringArray[0]);
            String desc = stringArray[1];
            Date startTime;
            Date endTime;
            HashSet<String> tags;

            if (stringArray[2].equals("@null")) {
                startTime = null;
            } else {
                startTime = df.parse(stringArray[2]);
            }

            if (stringArray[3].equals("@null")) {
                endTime = null;
            } else {
                endTime = df.parse(stringArray[3]);
            }

            if (stringArray[4].equals("@null")) {
                tags = null;
            } else if (stringArray[4].equals("@empty")) {
                tags = new HashSet<String>();
            } else {
                tags = new HashSet<String>();
                String[] tagStrings = stringArray[4].split("\\s+");
                for (String tagString : tagStrings) {
                    tags.add(tagString);
                }
            }

            Magic8Task task = new Magic8Task(id, desc, startTime, endTime, tags);
            return task;
        } else {
            throw new IllegalArgumentException("Error insufficient arguments");
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
            // Check end time
            Date d1 = getEndTime();
            Date d2 = other.getEndTime();

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
