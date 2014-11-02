package fantasticfour.magiceight.command;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class DisplayCommand extends Command {
        
    public DisplayCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    /*
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
            System.out.println(df.format(task.getEndTime().getTime()));
        }
        
        System.out.println();
    }
    */
    
    public void execute() throws IOException {
        if(super.getTags() != null) {
            for(String tag : super.getTags()) {
                ArrayList<Magic8Task> tasks = super.getTaskManager().getTasksWithWord(tag, true);
                if(tasks == null) {
                	this.setStatus(Magic8Status.ERROR);
                	return;
                }
                this.setTask(tasks);
                this.setStatus(Magic8Status.SUCCESS);
                return;
            }
        }
        ArrayList<Magic8Task> tasks = super.getTaskManager().getAllTasks();
        if(tasks == null) {
        	this.setStatus(Magic8Status.ERROR);
        	return;
        }
        this.setTask(tasks);
        this.setStatus(Magic8Status.SUCCESS);
    }
}
