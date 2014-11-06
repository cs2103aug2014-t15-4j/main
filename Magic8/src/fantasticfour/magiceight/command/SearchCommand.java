package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

public class SearchCommand extends Command {
        
    public SearchCommand(Magic8CommandObject obj, Magic8TaskList tm) {
        super(obj, tm);
    }
    
    /*
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
        if(task.getEndTime() == null) {
            System.out. println("not specified");
        } else {
            System.out.println(task.getEndTime().toString());
        }
    }
    */
    
    public void execute() throws IOException {
        for(String keyword : super.getKeywords()) {
            ArrayList<Magic8Task> tasks = super.getTaskManager().getTasksWithWord(keyword, false);
            if(tasks == null) {
                this.setStatus(Magic8Status.SEARCH_INPUT_MISMATCH);
            } else {
                this.setStatus(Magic8Status.SEARCH_SUCCESS);
                this.setTask(tasks);
            }
        }
            
    }
}
