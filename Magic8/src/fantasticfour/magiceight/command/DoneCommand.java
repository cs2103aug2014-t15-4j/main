package fantasticfour.magiceight.command;

import java.io.IOException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class DoneCommand extends Command {
        
    public DoneCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        int id = super.getIds().get(0);
        if(id > super.getTaskManager().getAllTasks(false).size()) {
            super.setStatus(Magic8Status.DONE_FAILURE);
        } else {
            Magic8Task task = super.getTaskManager().getAllTasks(false).get(id-1);
            task.setDone(true);
            super.getTaskManager().updateTask(task);
            super.setStatus(Magic8Status.DONE_SUCCESS);
            super.setTask(super.getTaskManager().getAllTasks(false));
        }
    }
}
