package fantasticfour.magiceight;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

class Magic8Task implements Magic8TaskInterface {
    private static final String ERROR_EMPTY_TAG = "Tags should not be empty strings";
    private static final String ERROR_NULL_TAGS = "tags should not contain null strings";
    private static final String ERROR_INVALID_DESCRIPTION = "description cannot be empty";
    private static final String ERROR_NON_ALPHANUMERIC_TAG = "tags must be alphanumeric";
    private static final String ERROR_INVALID_ID = "id must be non-negative";
    private int id;
    private String desc;
    private Date deadline;
    private HashSet<String> tags;
    private static DateFormat df = DateFormat.getDateInstance();

    public Magic8Task(int id, String desc, Date deadline, HashSet<String> tags)
            throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(ERROR_INVALID_ID);
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
        if (id <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_ID);
        }
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) throws IllegalArgumentException {
        if (desc == null || desc.length() == 0) {
            throw new IllegalArgumentException(ERROR_INVALID_DESCRIPTION);
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
        if (tags.contains(null)) {
            throw new IllegalArgumentException(ERROR_NULL_TAGS);
        }
        if (tags.contains("")) {
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
        if (tag == null) {
            throw new IllegalArgumentException(ERROR_NULL_TAGS);
        }
        if (tag.equals("")) {
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
