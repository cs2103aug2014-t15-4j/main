package fantasticfour.magiceight;

import java.util.ArrayList;
import java.util.Date;

public interface Magic8TaskInterface {
    public int getId();

    public void setId(int id);

    public String getDesc();

    public void setDesc(String desc);

    public Date getDeadline();

    public void setDeadline(Date deadline);

    public ArrayList<Integer> getTagIds();

    public void setTagIds(ArrayList<Integer> tagIds);

    public void addTagId(int id);

    public void removeTagId(int id);
}
