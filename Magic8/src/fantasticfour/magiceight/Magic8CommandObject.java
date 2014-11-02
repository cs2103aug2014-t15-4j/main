package fantasticfour.magiceight;

import java.util.ArrayList;
import java.util.Calendar;

public class Magic8CommandObject {
    
    String function;
    String taskDescription;
    ArrayList<String> tags;
    ArrayList<String> keywords;
    ArrayList<Integer> ids;
    Calendar deadline;

    Magic8CommandObject() {
        function = null;
        taskDescription = null;
        tags = null;
        ids = null;
        deadline = null;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }
}