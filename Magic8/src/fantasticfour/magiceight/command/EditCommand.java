package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class EditCommand extends Command {
        
    public EditCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        ArrayList<Magic8Task> tasks = null;
        int id = super.getIds().get(0);
        tasks = super.getTaskManager().getAllTasks();
	    for(Magic8Task task : tasks) {
	    	if(task.getId() == id) {
	            task = tasks.get(id);
	            task.setDesc(super.getTaskDescription());
	            super.getTaskManager().updateTask(task);
	            System.out.println("Task is edited succesfully");
	            return;
	    	}
	    }
    	System.out.println("No such task is found");
    }
}
