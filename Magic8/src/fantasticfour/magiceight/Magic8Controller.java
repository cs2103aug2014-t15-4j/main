package fantasticfour.magiceight;

import java.io.IOException;
import java.util.ArrayList;

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
    private static final String CAL_FUNCTION = "cal";
    
    private static final String ADD_SUCCESS_MESSAGE = "Task successfully added";  
    private static final String CLEAR_SUCCESS_MESSAGE = "Clear is successful";
    private static final String DELETE_SUCCESS_MESSAGE = "Tasks succesfullly deleted";
    private static final String DISPLAY_SUCCESS_MESSAGE = "Tasks successfully displayed";
    private static final String EDIT_SUCCESS_MESSAGE = "Task successfully edited";
    private static final String EXIT_SUCCESS_MESSAGE = "Exit successful";
    private static final String HELP_SUCCESS_MESSAGE = "Help successfully displayed";
    private static final String OPEN_SUCCESS_MESSAGE = "File successfully opened";
    private static final String REDO_SUCCESS_MESSAGE = "Redo successful";
    private static final String SEARCH_SUCCESS_MESSAGE = "Search successful";
    private static final String UNDO_SUCCESS_MESSAGE = "Undo successful";
    private static final String CAL_SUCCESS_MESSAGE = "Calendar display successful";
    
    private static final String ADD_ERROR_MESSAGE = "Add failed";  
    private static final String CLEAR_ERROR_MESSAGE = "Clear failed";
    private static final String DELETE_ERROR_MESSAGE = "Delete failed";
    private static final String DISPLAY_ERROR_MESSAGE = "Display failed";
    private static final String EDIT_ERROR_MESSAGE = "Edit failed";
    private static final String EXIT_ERROR_MESSAGE = "Exit failed";
    private static final String HELP_ERROR_MESSAGE = "Help failed";
    private static final String OPEN_ERROR_MESSAGE = "Open failed";
    private static final String REDO_ERROR_MESSAGE = "Redo failed";
    private static final String SEARCH_ERROR_MESSAGE = "Search failed";
    private static final String UNDO_ERROR_MESSAGE = "Undo failed";
    private static final String CAL_ERROR_MESSAGE = "Calendar display failed";
     
    public Magic8Controller(String input, Magic8TaskList tm) throws IOException {
    	String errorMessage = "error message";
    	String successMessage = "success message";
        Magic8CommandObject obj = Magic8Parser.parseCommand(input);
        switch(obj.getFunction()) {
            case ADD_FUNCTION:
                command = new AddCommand(obj, tm);
                successMessage = ADD_SUCCESS_MESSAGE;
                errorMessage = ADD_ERROR_MESSAGE;
                break;
            case CLEAR_FUNCTION:
                command = new ClearCommand(obj, tm);
                successMessage = CLEAR_SUCCESS_MESSAGE;
                errorMessage = CLEAR_ERROR_MESSAGE;
                break;
            case DELETE_FUNCTION:
                command = new DeleteCommand(obj, tm);
                successMessage = DELETE_SUCCESS_MESSAGE;
                errorMessage = DELETE_ERROR_MESSAGE;
                break;
            case DISPLAY_FUNCTION:
                command = new DisplayCommand(obj, tm);
                successMessage = DISPLAY_SUCCESS_MESSAGE;
                errorMessage = DISPLAY_ERROR_MESSAGE;
                break;
            case DONE_FUNCTION:
                command = new DoneCommand(obj, tm);
                successMessage = DISPLAY_SUCCESS_MESSAGE;
                errorMessage = DISPLAY_ERROR_MESSAGE;
                break;
            case EDIT_FUNCTION:
                command = new EditCommand(obj, tm);
                successMessage = EDIT_SUCCESS_MESSAGE;
                errorMessage = EDIT_ERROR_MESSAGE;
                break;
            case EXIT_FUNCTION:
                command = new ExitCommand(obj, tm);
                successMessage = EXIT_SUCCESS_MESSAGE;
                errorMessage = EXIT_ERROR_MESSAGE;
                break;
            case HELP_FUNCTION:
                command = new HelpCommand(obj, tm);
                successMessage = HELP_SUCCESS_MESSAGE;
                errorMessage = HELP_ERROR_MESSAGE;
                break;
            case OPEN_FUNCTION:
                command = new OpenCommand(obj, tm);
                successMessage = OPEN_SUCCESS_MESSAGE;
                errorMessage = OPEN_ERROR_MESSAGE;
                break;
            case REDO_FUNCTION:
                command = new RedoCommand(obj, tm);
                successMessage = REDO_SUCCESS_MESSAGE;
                errorMessage = REDO_ERROR_MESSAGE;
                break;
            case SEARCH_FUNCTION:
                command = new SearchCommand(obj, tm);
                successMessage = SEARCH_SUCCESS_MESSAGE;
                errorMessage = SEARCH_ERROR_MESSAGE;
                break;
            case UNDO_FUNCTION:
                command = new UndoCommand(obj, tm);
                successMessage = UNDO_SUCCESS_MESSAGE;
                errorMessage = UNDO_ERROR_MESSAGE;
                break;
            case CAL_FUNCTION:
                command = new HelpCommand(obj, tm);
                successMessage = CAL_SUCCESS_MESSAGE;
                errorMessage = CAL_ERROR_MESSAGE;
                break;
        }
        command.execute();
        
        if((command.getStatusInfo() == Magic8Status.ADD_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.DELETE_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.DISPLAY_SUCCESS)||
            (command.getStatusInfo() == Magic8Status.DONE_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.EDIT_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.EXIT_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.HELP_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.OPEN_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.REDO_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.SEARCH_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.UNDO_SUCCESS)||
        	(command.getStatusInfo() == Magic8Status.CAL_SUCCESS)){
            command.addStatus(successMessage);
        } else {
            command.addStatus(errorMessage);
        }
    }
    
    public String getStatusMessage() {
        return command.getStatusMessage();
    }
    
    public Magic8Status getStatus() {
        return command.getStatusInfo();
    }
    
    public ArrayList<Magic8Task> getTaskList() {
        return command.getReturnTaskList();
    }
    
    public Magic8TaskList getTaskManager() {
    	return command.getTaskManager();
    }
}
