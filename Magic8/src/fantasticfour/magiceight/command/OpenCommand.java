package fantasticfour.magiceight.command;

import java.io.IOException;
import java.text.ParseException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8TaskList;

public class OpenCommand extends Command{
    
    public OpenCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() {
        try {
            super.setTaskManager(new Magic8TaskList(super.getTaskDescription()));
            this.setStatus(Magic8Status.OPEN_SUCCESS);
            this.setTask(super.getTaskManager().getAllTasks());
        } catch (IOException | ParseException e) {
            this.setStatus(Magic8Status.OPEN_INPUT_MISMATCH);
        }
    }
}
