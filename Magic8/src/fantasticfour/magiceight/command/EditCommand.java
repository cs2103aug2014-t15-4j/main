package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class EditCommand extends Command {
        
    public EditCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        int id = super.getIds().get(0);
	    for(Magic8Task task : super.getTaskManager().getAllTasks()) {
	    	if(task.getId() == id) {
	            task = super.getTaskManager().getAllTasks().get(id);
	            task.setDesc(super.getTaskDescription());
	            if(super.getTaskManager().updateTask(task)) {
	            	this.setStatus(Magic8Status.SUCCESS);
	            	this.setTask(super.getTaskManager().getAllTasks());
	                return;
	            }
	    	}
	    }
	    this.setStatus(Magic8Status.ERROR);
	    return;
    }
}
