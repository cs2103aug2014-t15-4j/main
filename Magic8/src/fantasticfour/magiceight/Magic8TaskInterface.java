package fantasticfour.magiceight;

import java.util.Calendar;
import java.util.HashSet;

public interface Magic8TaskInterface extends Comparable<Magic8Task> {
    public int getId();

    public void setId(int id);

    public String getDesc();

    public void setDesc(String desc);

    public boolean isDone();

    public void setDone(boolean isDone);

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
