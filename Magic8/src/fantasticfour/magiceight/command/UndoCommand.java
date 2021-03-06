package fantasticfour.magiceight.command;

import java.io.IOException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8TaskList;

//@author A0080527H
public class UndoCommand extends Command {
        
    public UndoCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        if(!super.getTaskManager().undo()) {
            this.setStatus(Magic8Status.UNDO_FAILURE);
        } else {
            this.setStatus(Magic8Status.UNDO_SUCCESS);
            this.setTask(super.getTaskManager().getAllTasks(false));
        }
    }
}
