package fantasticfour.magiceight;

import java.util.ArrayList;
import java.util.Calendar;

public class Magic8CommandObject {
    
    String function;
    String taskDescription;
    ArrayList<String> tags;
    ArrayList<String> keywords;
    ArrayList<Integer> ids;
    Calendar startDate;
	Calendar deadline;

    public Magic8CommandObject() {
        function = null;
        taskDescription = null;
        tags = null;
        keywords = null;
        ids = null;
        startDate = null;
        deadline = null;
    }
    
    public Magic8CommandObject(String fn, String td, ArrayList<String> tg, 
    		ArrayList<String> kw, ArrayList<Integer> id, Calendar sd, Calendar dd) {
        function = fn;
        taskDescription = td;
        tags = tg;
        keywords = kw;
        ids = id;
        startDate = sd;
        deadline = dd;
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

    public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }
    
    public void display() {
    	System.out.println("function: " + this.function);
    	System.out.println("task desc: " + this.taskDescription);
    	System.out.print("tags: ");
    	if(this.tags != null)
	    	for(String tag : this.tags)
	    		System.out.print(tag + " ");
    	System.out.println();
    	System.out.print("keywords: ");
    	if(this.keywords != null)
	    	for(String k : this.keywords)
	    		System.out.print(k + " ");
    	System.out.println();
    	System.out.print("ids: ");
    	if(this.ids != null)
	    	for(Integer id : this.ids)
	    		System.out.print(id + " ");
    	System.out.println();
    	if(this.startDate != null)
    		System.out.println("startTime: " + this.startDate.getTime());
    	if(this.deadline != null)
    		System.out.println("endTime: " + this.deadline.getTime());
    	System.out.println();
    }
}