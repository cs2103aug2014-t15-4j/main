package fantasticfour.magiceight.command;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8TaskList;
import java.io.IOException;

public class CalendarCommand extends Command {
	public CalendarCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        this.setStatus(Magic8Status.CAL_SUCCESS);
    }
}
