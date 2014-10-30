package fantasticfour.magiceight.command;

import java.io.IOException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8TaskList;

public class RedoCommand extends Command {
        
    public RedoCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        super.getTaskManager().redo();
    }
}
