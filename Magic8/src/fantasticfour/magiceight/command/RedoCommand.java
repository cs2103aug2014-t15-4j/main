package fantasticfour.magiceight.command;

import java.io.IOException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8TaskList;

public class RedoCommand extends Command {
        
    public RedoCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        if(!super.getTaskManager().redo()) {
            this.setStatus(Magic8Status.REDO_FAILURE);
        } else {
            this.setStatus(Magic8Status.REDO_SUCCESS);
            this.setTask(super.getTaskManager().getAllTasks());
        }
    }
}
