package fantasticfour.magiceight.command;

import java.io.IOException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class EditCommand extends Command {
        
    public EditCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        Magic8Task task = null;
        task = super.getTaskManager().getAllTasks().get(super.getIds().get(0));
        task.setDesc(super.getTaskDescription());
        super.getTaskManager().updateTask(task);
        System.out.println("Task is edited succesfully");
    }
}
