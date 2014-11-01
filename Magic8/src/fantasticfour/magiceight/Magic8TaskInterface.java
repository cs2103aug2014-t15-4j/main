package fantasticfour.magiceight;

import java.util.Date;
import java.util.HashSet;

interface Magic8TaskInterface {
    public int getId();

    public void setId(int id);

    public String getDesc();

    public void setDesc(String desc);

    public Date getStartTime();

    public void setStartTime(Date startTime);

    public Date getEndTime();

    public void setEndTime(Date endTime);

    public HashSet<String> getTags();

    public void setTags(HashSet<String> tags);

    public void addTag(String tag);

    public void removeTag(String tag);

    public String[] toStringArray();
}
