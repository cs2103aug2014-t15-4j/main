package fantasticfour.magiceight;

import java.util.Calendar;
import java.util.HashSet;

interface Magic8TaskInterface {
    public int getId();

    public void setId(int id);

    public String getDesc();

    public void setDesc(String desc);

    public Calendar getStartTime();

    public void setStartTime(Calendar startTime);

    public Calendar getEndTime();

    public void setEndTime(Calendar endTime);

    public HashSet<String> getTags();

    public void setTags(HashSet<String> tags);

    public void addTag(String tag);

    public void removeTag(String tag);

    public String[] toStringArray();
}
