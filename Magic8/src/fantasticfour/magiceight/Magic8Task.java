package fantasticfour.magiceight;

import java.util.ArrayList;
import java.util.Date;

class Magic8Task implements Magic8TaskInterface {
    private int id;
    private String desc;
    private Date deadline;
    private ArrayList<Integer> tagIds;
    
    public Magic8Task(int id, String desc, Date deadline,
            ArrayList<Integer> tagIds) {
        this.id = id;
        this.desc = desc;
        this.deadline = deadline;
        this.tagIds = tagIds;
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

    public ArrayList<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(ArrayList<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public void addTagId(int id) {
        if (!tagIds.contains(id)) {
            this.tagIds.add(id);
        }
    }

    public void removeTagId(int id) {
        if (!tagIds.contains(id)) {
            this.tagIds.remove(id);
        }
    }
    
    
}
