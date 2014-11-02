package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.TreeMap;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class EditCommand extends Command {
        
    public EditCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        Magic8Task task = null;
        TreeMap<Integer, Magic8Task> tasks = null;
        int id = super.getIds().get(0);
        tasks = super.getTaskManager().getAllTasks();
        if(tasks.containsKey(id)) {
            task = tasks.get(id);
            task.setDesc(super.getTaskDescription());
            super.getTaskManager().updateTask(task);
            System.out.println("Task is edited succesfully");
        } else {
            System.out.println("No such task is found");
        }
    }
}
