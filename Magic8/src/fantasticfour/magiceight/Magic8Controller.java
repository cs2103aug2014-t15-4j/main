package fantasticfour.magiceight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import fantasticfour.magiceight.command.AddCommand;
import fantasticfour.magiceight.command.ClearCommand;
import fantasticfour.magiceight.command.DeleteCommand;
import fantasticfour.magiceight.command.DisplayCommand;
import fantasticfour.magiceight.command.DoneCommand;
import fantasticfour.magiceight.command.EditCommand;
import fantasticfour.magiceight.command.ExitCommand;
import fantasticfour.magiceight.command.HelpCommand;
import fantasticfour.magiceight.command.ICommand;
import fantasticfour.magiceight.command.OpenCommand;
import fantasticfour.magiceight.command.RedoCommand;
import fantasticfour.magiceight.command.SearchCommand;
import fantasticfour.magiceight.command.UndoCommand;
import fantasticfour.magiceight.parser.Magic8Parser;

//@author A0080527H
public class Magic8Controller {
    private ICommand command;
    private static final String ADD_FUNCTION = "add";
    private static final String CLEAR_FUNCTION = "clear";
    private static final String DONE_FUNCTION = "done";
    private static final String DELETE_FUNCTION = "delete";
    private static final String DISPLAY_FUNCTION = "display";
    private static final String EDIT_FUNCTION = "edit";
    private static final String EXIT_FUNCTION = "exit";
    private static final String HELP_FUNCTION = "help";
    private static final String OPEN_FUNCTION = "open";
    private static final String REDO_FUNCTION = "redo";
    private static final String SEARCH_FUNCTION = "search";
    private static final String UNDO_FUNCTION = "undo";
    
    private static final Magic8Status[] INPUT_FAILURE = {
    	Magic8Status.ADD_INPUT_MISMATCH,
    	Magic8Status.CLEAR_INPUT_MISMATCH,
    	Magic8Status.DELETE_INPUT_MISMATCH,
    	Magic8Status.DISPLAY_INPUT_MISMATCH,
    	Magic8Status.DONE_INPUT_MISMATCH,
    	Magic8Status.EDIT_INPUT_MISMATCH,
    	Magic8Status.EXIT_INPUT_MISMATCH,
    	Magic8Status.HELP_INPUT_MISMATCH,
    	Magic8Status.OPEN_INPUT_MISMATCH,
    	Magic8Status.SEARCH_INPUT_MISMATCH,
    	Magic8Status.UNDO_INPUT_MISMATCH,
    	Magic8Status.REDO_INPUT_MISMATCH,
    };
    
    public Magic8Status inputStatus;
    
    public Magic8Controller(String input, Magic8TaskList tm) throws IOException {
    	Magic8Parser.ReturnValues parsedValue = Magic8Parser.parseCommand(input);
        Magic8CommandObject obj = parsedValue.getCommandObject();
        this.inputStatus = parsedValue.getStatus();
        
        if(!Arrays.asList(INPUT_FAILURE).contains(inputStatus)) {
	        switch(obj.getFunction()) {
	            case ADD_FUNCTION:
	                command = new AddCommand(obj, tm);
	                break;
	            case CLEAR_FUNCTION:
	                command = new ClearCommand(obj, tm);
	                break;
	            case DELETE_FUNCTION:
	                command = new DeleteCommand(obj, tm);
	                break;
	            case DISPLAY_FUNCTION:
	                command = new DisplayCommand(obj, tm);
	                break;
	            case DONE_FUNCTION:
	                command = new DoneCommand(obj, tm);
	                break;
	            case EDIT_FUNCTION:
	                command = new EditCommand(obj, tm);
	                break;
	            case EXIT_FUNCTION:
	                command = new ExitCommand(obj, tm);
	                break;
	            case HELP_FUNCTION:
	                command = new HelpCommand(obj, tm);
	                break;
	            case OPEN_FUNCTION:
	                command = new OpenCommand(obj, tm);
	                break;
	            case REDO_FUNCTION:
	                command = new RedoCommand(obj, tm);
	                break;
	            case SEARCH_FUNCTION:
	                command = new SearchCommand(obj, tm);
	                break;
	            case UNDO_FUNCTION:
	                command = new UndoCommand(obj, tm);
	                break;
	        }
        }
        command.execute();
    }
    
    public String getStatusMessage() {
        return command.getStatusMessage();
    }
    
    public Magic8Status getStatus() {
        return command.getStatusInfo();
    }
    
    public Magic8Status getInputStatus() {
        return this.inputStatus;
    }
    
    public ArrayList<Magic8Task> getTaskList() {
        return command.getReturnTaskList();
    }
    
    public Magic8TaskList getTaskManager() {
    	return command.getTaskManager();
    }
}
