package fantasticfour.magiceight;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

class Magic8Task implements Magic8TaskInterface {
    private static final String ERROR_NEGATIVE_ID = "id cannot be negative";
    private static final String ERROR_ZERO_ID = "id cannot be zero";
    private static final String ERROR_EMPTY_DESCRIPTION = "description must be non-empty string";
    private static final String ERROR_EMPTY_TAG = "tag cannot be empty";
    private static final String ERROR_NON_ALPHANUMERIC_TAG = "tag must be alphanumeric";

    private static final String EMPTY_STRING = "";

    private int id;
    private String desc;
    private Date deadline;
    private HashSet<String> tags;
    private static DateFormat df = DateFormat.getDateInstance();

    public Magic8Task(int id, String desc, Date deadline, HashSet<String> tags)
            throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_ID);
        }

        this.id = id;
        setDesc(desc);
        setDeadline(deadline);
        setTags(tags);
    }

    public Magic8Task(String desc, Date deadline, HashSet<String> tags)
            throws IllegalArgumentException {
        this(0, desc, deadline, tags);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_ID);
        }

        if (id == 0) {
            throw new IllegalArgumentException(ERROR_ZERO_ID);
        }

        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) throws IllegalArgumentException {
        if (desc == null || desc.isEmpty()) {
            throw new IllegalArgumentException(ERROR_EMPTY_DESCRIPTION);
        }

        this.desc = desc;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public HashSet<String> getTags() {
        return this.tags;
    }

    public void setTags(HashSet<String> tags) throws IllegalArgumentException {
        if (tags == null) {
            tags = new HashSet<String>();
        }

        if (tags.contains(null) || tags.contains(EMPTY_STRING)) {
            throw new IllegalArgumentException(ERROR_EMPTY_TAG);
        }

        for (String tag : tags) {
            if (!tag.matches("[A-Za-z0-9]+")) {
                throw new IllegalArgumentException(ERROR_NON_ALPHANUMERIC_TAG);
            }
        }
        this.tags = tags;
    }

    public void addTag(String tag) throws IllegalArgumentException {
        if (tag == null || tag.equals(EMPTY_STRING)) {
            throw new IllegalArgumentException(ERROR_EMPTY_TAG);
        }

        if (!tag.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException(ERROR_NON_ALPHANUMERIC_TAG);
        }

        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public void replaceTag(String newTag, String oldTag)
            throws IllegalArgumentException {
        removeTag(oldTag);
        addTag(newTag);
    }

    public String toString(String delimiter) {
        String taskId = Integer.toString(this.id);
        String desc = this.desc.replace(delimiter, "\\" + delimiter);
        String deadline = df.format(this.deadline);
        String tags = "";
        for (String tag : this.tags) {
            tags += " " + tag;
        }
        return taskId + delimiter + desc + delimiter + deadline + delimiter
                + tags;
    }

    public static Magic8Task parse(String s, String delimiter)
            throws ParseException {
        String regex = "(?<!\\\\)" + delimiter;
        String[] tokens = s.split(regex);

        int taskId = Integer.parseInt(tokens[0]);
        String desc = tokens[1];
        Date deadline = df.parse(tokens[2]);
        HashSet<String> tags = new HashSet<String>(Arrays.asList(tokens[3]
                .split(" ")));
        Magic8Task task = new Magic8Task(taskId, desc, deadline, tags);
        return task;
    }
}
