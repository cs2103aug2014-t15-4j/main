package fantasticfour.magiceight;

import java.util.Date;
import java.util.HashSet;

class Magic8Task implements Magic8TaskInterface {
    private int id;
    private String desc;
    private Date deadline;
    private HashSet<String> tags;
    
    public Magic8Task(int id, String desc, Date deadline,
            HashSet<String> tags) {
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
}
