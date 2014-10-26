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
        if(super.getIds() == null && super.getTags() == null) {
            super.getTaskManager().clearTasks();
        } else if(super.getTags() == null) {
            for(Integer id : super.getIds()) {
                task = super.getTaskManager().getAllTasks().get(id);
                super.getTaskManager().removeTask(task);
            }
        } else {
            for(String tag : super.getTags()) {
                TreeMap<Integer, Magic8Task> tasks = super.getTaskManager().getTasksWithTag(tag);
                for(Map.Entry<Integer, Magic8Task> entry : tasks.entrySet()) {
                    task = entry.getValue();
                    super.getTaskManager().removeTask(task);
                }
            }
        }
        System.out.println("Task is removed succesfully");
    }
}
