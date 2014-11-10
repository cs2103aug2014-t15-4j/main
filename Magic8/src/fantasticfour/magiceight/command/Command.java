package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class Command implements ICommand {
    private Magic8TaskList taskManager;
    private String function;
    private String taskDescription;
    private ArrayList<String> tags;
    private ArrayList<String> keywords;
    private ArrayList<Integer> ids;
    private Calendar deadline;
    private Calendar startDate;
    
    private ArrayList<Magic8Task> task;
	private String infoMessage;
    private Magic8Status status;
    
    public ArrayList<Magic8Task> getTask() {
		return task;
	}

	public void setTask(ArrayList<Magic8Task> task) {
		this.task = task;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public Magic8Status getStatus() {
		return status;
	}

	public void setStatus(Magic8Status status) {
		this.status = status;
	}
    
    public Magic8TaskList getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(Magic8TaskList taskManager) {
        this.taskManager = taskManager;
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
    
    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    
    public Command(Magic8CommandObject obj, Magic8TaskList tm) {
        this.function = obj.getFunction();
        this.taskDescription = obj.getTaskDescription();
        this.tags = obj.getTags() == null ? null : new ArrayList<String>(obj.getTags());
        this.keywords = obj.getKeywords() == null ? null : new ArrayList<String>(obj.getKeywords());
        this.ids = obj.getIds() == null ? null : new ArrayList<Integer>(obj.getIds());
        this.deadline = obj.getDeadline();
        this.startDate = obj.getStartDate();
        this.taskManager = tm;
    }
    
    public void execute() throws IOException {
        // TODO Auto-generated method stub
    }
    
    public void addStatus(String message) {
    	this.setInfoMessage(message);
    }    

    public Magic8Status getStatusInfo() {
    	return this.getStatus();
    }
    
    public ArrayList<Magic8Task> getReturnTaskList() {
    	return this.getTask();
    }
    
    public String getStatusMessage() {
        return this.getInfoMessage();
    }
}
