package fantasticfour.magiceight;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

interface Magic8StorageInterface {
    public void writeToFile(int id, Map<Integer, Magic8Task> taskList)
            throws IOException;

    public int getId();

    public TreeMap<Integer, Magic8Task> getTaskList();

    public TreeMap<Integer, Magic8Task> convertListToTreeMap(List<String[]> list)
            throws IllegalArgumentException, ParseException;

    public ArrayList<String[]> convertMapToArrayList(
            Map<Integer, Magic8Task> map);
}