package fantasticfour.magiceight;

import java.util.Date;
import java.util.HashSet;

interface Magic8TaskInterface {
    public int getId();

    public void setId(int id);

    public String getDesc();

    public void setDesc(String desc);

    public Date getDeadline();

    public void setDeadline(Date deadline);

    public HashSet<String> getTags();

    public void setTags(HashSet<String> tags);

    public void addTag(String tag);

    public void removeTag(String tag);
    
    public void replaceTag(String newTag, String oldTag);
}
