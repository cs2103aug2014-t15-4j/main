package fantasticfour.magiceight.command;

import java.io.IOException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8TaskList;

public class HelpCommand extends Command {
        
    public HelpCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        System.out.println("Calling out help method");
    }
}
