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
    
    private void display(Magic8Task task) {
        System.out.println(Integer.toString(task.getId()) + ": " + task.getDesc() + " ");
        if(task.getTags() != null) {
            System.out.print("tags:");
            for(String tag : task.getTags()) {
                System.out.print(tag + ", ");
            }
            System.out.println();
        }
        System.out.print("deadline: ");
        if(task.getDeadline() == null) {
            System.out. println("not specified");
        } else {
            System.out.println(task.getDeadline().toString());
        }
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
