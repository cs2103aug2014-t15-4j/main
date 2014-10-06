package fantasticfour.magiceight;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

class Magic8Task implements Magic8TaskInterface {
    private int id;
    private String desc;
    private Date deadline;
    private HashSet<String> tags;

    private static DateFormat df = DateFormat.getDateInstance();

    public Magic8Task(int id, String desc, Date deadline, HashSet<String> tags) {
        this.id = id;
        this.desc = desc;
        this.deadline = deadline;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
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

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public void replaceTag(String newTag, String oldTag) {
        this.tags.remove(oldTag);
        this.tags.add(newTag);
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
        HashSet<String> tags = new HashSet(Arrays.asList(tokens[3].split(" ")));
        Magic8Task task = new Magic8Task(taskId, desc, deadline, tags);
        return task;
    }
}
