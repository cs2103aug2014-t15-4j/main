package fantasticfour.magiceight.command;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class DisplayCommand extends Command {
        
    public DisplayCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    private void display(Magic8Task task) {
        System.out.println(Integer.toString(task.getId()) + ": " + task.getDesc() + " ");
        if(task.getTags() != null) {
            System.out.print("tags: ");
            int i = 0;
            for(String tag : task.getTags()) {
                System.out.print(tag);
                if (i != task.getTags().size()-1){
                	System.out.print(", ");
                }
                i++;
            }
            System.out.println();
        }
        System.out.print("deadline: ");
        if(task.getEndTime() == null) {
            System.out. println("not specified");
        } else {
        	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(df.format(task.getEndTime()));
        }
        
        System.out.println();
    }
    
    public void execute() throws IOException {
        Magic8Task task = null;
        if(super.getTags() != null) {
            for(String tag : super.getTags()) {
                TreeMap<Integer, Magic8Task> tasks = super.getTaskManager().getTasksWithTag(tag);
                for(Map.Entry<Integer, Magic8Task> entry : tasks.entrySet()) {
                    task = entry.getValue();
                    display(task);
                }
            }
            return;
        }
        TreeMap<Integer, Magic8Task> tasks = super.getTaskManager().getAllTasks();
        for(Map.Entry<Integer, Magic8Task> entry : tasks.entrySet()) {
            task = entry.getValue();
            display(task);
        }
    }
}
