package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8Parser;

public class Magic8UITerminal {
    private static void magic8UIInit() {
        
    }
    
    private static void magic8UIRun() {
        
    }
 
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
                        HashSet<String> tags = null;
                        if(cmdObj.getTags() != null) {
                             tags = new HashSet<String>(cmdObj.getTags());
                        }
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
                    	if(cmdObj.getIds() == null && cmdObj.getTags() == null) {
                    		taskListManager.clearTasks();
                    	} else if(cmdObj.getTags() == null) {
                    	    for(Integer id : cmdObj.getIds()) {
                    	        task = taskListManager.getAllTasks().get(id);
                    	        taskListManager.removeTask(task);
                    	    }
                    	} else {
                    	    for(String tag : cmdObj.getTags()) {
                    	        TreeMap<Integer, Magic8Task> tasks = taskListManager.getTasksWithTag(tag);
                    	        for(Map.Entry<Integer, Magic8Task> entry : tasks.entrySet()) {
                    	            task = entry.getValue();
                    	            taskListManager.removeTask(task);
                    	        }
                    	    }
                    	}
                        System.out.println("Task is removed succesfully");
                        break;
                        
                    case "display":
                        TreeMap<Integer, Magic8Task> tasks = taskListManager.getAllTasks();
                        for(Map.Entry<Integer, Magic8Task> entry : tasks.entrySet()) {
                            task = entry.getValue();
                            System.out.println(Integer.toString(task.getId()) + ": " + task.getDesc());
                        }
                        break;
                        
                    case "edit":
                        task = taskListManager.getAllTasks().get(cmdObj.getIds().get(0));
                        task.setDesc(cmdObj.getTaskDescription());
                        taskListManager.updateTask(task);
                        System.out.println("Task is edited succesfully");
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
                    
                    case "exit":
                        System.exit(0);
                        
                }
                
            } catch(IllegalArgumentException e) {
                System.out.println(e.toString());
            }
        }
    }
}
