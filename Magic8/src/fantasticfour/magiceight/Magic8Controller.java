package fantasticfour.magiceight;

import java.io.IOException;
import java.util.ArrayList;

import fantasticfour.magiceight.command.AddCommand;
import fantasticfour.magiceight.command.ClearCommand;
import fantasticfour.magiceight.command.DeleteCommand;
import fantasticfour.magiceight.command.DisplayCommand;
import fantasticfour.magiceight.command.EditCommand;
import fantasticfour.magiceight.command.ExitCommand;
import fantasticfour.magiceight.command.HelpCommand;
import fantasticfour.magiceight.command.ICommand;
import fantasticfour.magiceight.command.RedoCommand;
import fantasticfour.magiceight.command.SearchCommand;
import fantasticfour.magiceight.command.UndoCommand;

public class Magic8Controller {
    private ICommand command;
    private static final String ADD_FUNCTION = "add";
    private static final String CLEAR_FUNCTION = "clear";
    private static final String DELETE_FUNCTION = "delete";
    private static final String DISPLAY_FUNCTION = "display";
    private static final String EDIT_FUNCTION = "edit";
    private static final String EXIT_FUNCTION = "exit";
    private static final String HELP_FUNCTION = "help";
    private static final String REDO_FUNCTION = "redo";
    private static final String SEARCH_FUNCTION = "search";
    private static final String UNDO_FUNCTION = "undo";
    
    private static final String ADD_SUCCESS_MESSAGE = "task is successfully added";  
    private static final String CLEAR_SUCCESS_MESSAGE = "clear is successful";
    private static final String DELETE_SUCCESS_MESSAGE = "tasks are succesfullly deleted";
    private static final String DISPLAY_SUCCESS_MESSAGE = "tasks are successfully displayed";
    private static final String EDIT_SUCCESS_MESSAGE = "task is successfully edited";
    private static final String EXIT_SUCCESS_MESSAGE = "exit is successful";
    private static final String HELP_SUCCESS_MESSAGE = "help is successfully displayed";
    private static final String REDO_SUCCESS_MESSAGE = "redo is successful";
    private static final String SEARCH_SUCCESS_MESSAGE = "search is successful";
    private static final String UNDO_SUCCESS_MESSAGE = "undo is successful";
    
    private static final String ADD_ERROR_MESSAGE = "add fails";  
    private static final String CLEAR_ERROR_MESSAGE = "clear fails";
    private static final String DELETE_ERROR_MESSAGE = "delete fails";
    private static final String DISPLAY_ERROR_MESSAGE = "display fails";
    private static final String EDIT_ERROR_MESSAGE = "edit fails";
    private static final String EXIT_ERROR_MESSAGE = "exit fails";
    private static final String HELP_ERROR_MESSAGE = "help fails";
    private static final String REDO_ERROR_MESSAGE = "redo fails";
    private static final String SEARCH_ERROR_MESSAGE = "search fails";
    private static final String UNDO_ERROR_MESSAGE = "undo fails";
     
    public Magic8Controller(String input, Magic8TaskList tm) throws IOException {
    	String errorMessage = null;
    	String successMessage = null;
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
        }
        command.execute();
        
        if(command.getStatusInfo() == Magic8Status.SUCCESS)
            command.addStatus(successMessage);
        else
            command.addStatus(errorMessage);
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
}
