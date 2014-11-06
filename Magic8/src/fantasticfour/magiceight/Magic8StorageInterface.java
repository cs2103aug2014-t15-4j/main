package fantasticfour.magiceight;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

//@author A0113605U
interface Magic8StorageInterface {
    public void writeToFile(int id, Map<Integer, Magic8Task> taskList)
            throws IOException;

    public int getId();

    public TreeMap<Integer, Magic8Task> getTaskList();
    
    

}