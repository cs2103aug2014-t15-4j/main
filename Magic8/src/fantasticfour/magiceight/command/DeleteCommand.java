package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class DeleteCommand extends Command {
        
    public DeleteCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        ArrayList<Magic8Task> tasks = new ArrayList<Magic8Task>();
        
        if(super.getIds() == null && super.getTags() == null) {
            tasks = super.getTaskManager().clearTasks();
        } else if(super.getTags() == null) {
            for(Integer id : super.getIds()) {
                if(id > super.getTaskManager().getAllTasks().size()) {
                	this.setStatus(Magic8Status.ERROR);
                    return;
                }
                tasks.add(super.getTaskManager().getAllTasks().get(id-1));
            }
            tasks = super.getTaskManager().removeTasks(tasks);            
        } else {
            for(String tag : super.getTags()) {
                tasks = super.getTaskManager().removeTasksWithTag(tag);
            }
        }
        if(tasks == null) {
        	this.setStatus(Magic8Status.ERROR);
            return;
        }
        this.setStatus(Magic8Status.SUCCESS);
        this.setTask(super.getTaskManager().getAllTasks());
    }
}
