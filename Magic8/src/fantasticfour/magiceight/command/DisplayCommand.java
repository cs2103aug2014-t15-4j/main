package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class DisplayCommand extends Command {
        
    public DisplayCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        Magic8Task task = null;
        TreeMap<Integer, Magic8Task> tasks = super.getTaskManager().getAllTasks();
        for(Map.Entry<Integer, Magic8Task> entry : tasks.entrySet()) {
            task = entry.getValue();
            System.out.println(Integer.toString(task.getId()) + ": " + task.getDesc());
        }
    }
}
