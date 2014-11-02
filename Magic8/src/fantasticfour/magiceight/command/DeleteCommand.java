package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class DeleteCommand extends Command {
        
    public DeleteCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        Magic8Task task = null;
        int count = 0;
        if(super.getIds() == null && super.getTags() == null) {
            super.getTaskManager().clearTasks();
        } else if(super.getTags() == null) {
            for(Integer id : super.getIds()) {
                task = super.getTaskManager().getAllTasks().get(id);
                if(task != null) {
                    super.getTaskManager().removeTask(task);
                    count++;
                }
            }
        } else {
            for(String tag : super.getTags()) {
                super.getTaskManager().removeTasksWithTag(tag);
            }
        }
        
        if(count > 1)
            System.out.println(Integer.toString(count) + " tasks are removed");
        else
            System.out.println(Integer.toString(count) + " task is removed");
    }
}
