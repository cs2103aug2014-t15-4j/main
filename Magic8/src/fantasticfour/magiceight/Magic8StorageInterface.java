package fantasticfour.magiceight;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

interface Magic8StorageInterface {
    public void writeToFile(int id, TreeMap<Integer, Magic8Task> taskList)
            throws IOException;

    public int getId();

    public HashMap<Integer, Magic8Task> getTaskList();
}
