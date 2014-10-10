package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashSet;

import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8Parser;

public class Magic8UITerminal {
    public static void main(String[] args) throws IOException, ParseException {    	
    	String cmd, filename;
    	
    	Magic8Task task = null;
    	Magic8TaskList taskListManager = null;
        Magic8Parser.CommandObject cmdObj = new Magic8Parser.CommandObject();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Filename: ");
        filename = br.readLine();
        
        taskListManager = new Magic8TaskList(filename);
        
        while(cmdObj.getFunction() != "quit") {
            try {
                System.out.print("Command > ");
                cmd = br.readLine();
                cmdObj = Magic8Parser.parseCommand(cmd);
                
                switch(cmdObj.getFunction()) {
                    case "add":
                    	HashSet<String> tags = new HashSet<String>(cmdObj.getTags());
                        task = new Magic8Task(cmdObj.getTaskDescription(), 
                        					  cmdObj.getDeadline(), 
                        					  tags);
                        taskListManager.addTask(task);
                        System.out.println("Task is added succesfully");
                        break;
                    case "clear":
                        System.out.println("Calling out clear method");
                        break;
                    case "delete":
                    	if(cmdObj.getLineNumber() == -1 && cmdObj.getTags().size() == 0) {
                    		taskListManager.clearTasks();
                    	} else if(cmdObj.getTags().size() == 0) {
                    		taskListManager.getAllTasks().get(cmdObj.getLineNumber());
                    	}
                        break;
                    case "display":
                        System.out.println("Calling out display method");
                        break;
                    case "edit":
                        System.out.println("Calling out edit method");
                        break;
                    case "help":
                        System.out.println("Calling out help method");
                        break;
                    case "search":
                        System.out.println("Calling out search method");
                        break;
                    case "undo":
                        System.out.println("Calling out undo method");
                        break;
                }
                
            } catch(IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }
}
