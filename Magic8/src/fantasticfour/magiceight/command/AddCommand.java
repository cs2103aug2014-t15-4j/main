package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class AddCommand extends Command {
        
    public AddCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    public void execute() throws IOException {
        Magic8Task task = null;
        HashSet<String> tags = null;        
        if(super.getTags() != null) {
             tags = new HashSet<String>(super.getTags());
        }        
        task = new Magic8Task(super.getTaskDescription(),
                              new GregorianCalendar(),
                              super.getDeadline(), 
                              tags);        
        task = super.getTaskManager().addTask(task);
        
        if(task == null) {
        	super.setStatus(Magic8Status.ERROR);
        } else {
        	super.setStatus(Magic8Status.SUCCESS);
        	
        	ArrayList<Magic8Task> taskList = new ArrayList<Magic8Task>();
        	taskList.add(task);
        	super.setTask(taskList);
        }
    }
}
