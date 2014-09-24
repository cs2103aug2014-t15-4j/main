package fantasticfour.magiceight;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import fantasticfour.magiceight.Magic8Task;

public class Magic8 implements Magic8Interface {
    String fileName;
    File file;
    ArrayList<Magic8Task> taskList;
    ArrayList<Magic8Task> bufferedTaskList;
    PrintWriter pw;

    public Magic8(String fileName) {
        this.fileName = fileName;
        this.taskList = new ArrayList<Magic8Task>();
        this.bufferedTaskList = new ArrayList<Magic8Task>();

        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Magic8Task addTask(Magic8Task task) {
        this.taskList.add(task);
        this.writeTaskListToFile();
        return task;
    }

    public Magic8Task removeTask(int id) {
        return null;
    }

    public Magic8Task updateTask(Magic8Task task) {
        return null;
    }
    
    public void displayAll() {

    }

    public void writeTaskListToFile() {
        try {
            pw = new PrintWriter(fileName);

            JSONArray taskJsonArr = new JSONArray();
            pw.flush();
            for (Magic8Task task : this.taskList) {
                JSONObject taskJsonObj = new JSONObject();
                taskJsonObj.put("id", task.getId());
                taskJsonObj.put("desc", task.getDesc());
                taskJsonObj.put("deadline", task.getDeadline().toString());

                // Adding tags to map using linked list
                LinkedList tagIdList = new LinkedList();
                for (int tagId : task.getTagIds()) {
                    tagIdList.add(tagId);
                }
                taskJsonObj.put("tags", tagIdList);

                taskJsonArr.add(taskJsonObj);
            }
            pw.write(JSONValue.toJSONString(taskJsonArr));

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
