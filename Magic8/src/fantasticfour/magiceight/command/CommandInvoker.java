package fantasticfour.magiceight.command;

import java.io.IOException;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8TaskList;

public class CommandInvoker {
    private ICommand command;
    private static final String ADD_FUNCTION = "add";
    private static final String CLEAR_FUNCTION = "clear";
    private static final String DELETE_FUNCTION = "delete";
    private static final String DISPLAY_FUNCTION = "display";
    private static final String EDIT_FUNCTION = "edit";
    private static final String EXIT_FUNCTION = "exit";
    private static final String HELP_FUNCTION = "help";
    private static final String SEARCH_FUNCTION = "search";
    private static final String UNDO_FUNCTION = "undo";
    
    public CommandInvoker(Magic8CommandObject obj, Magic8TaskList tm) throws IOException {
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
            case EDIT_FUNCTION:
                command = new EditCommand(obj, tm);
                break;
            case EXIT_FUNCTION:
                command = new ExitCommand(obj, tm);
                break;
            case HELP_FUNCTION:
                command = new HelpCommand(obj, tm);
                break;
            case SEARCH_FUNCTION:
                command = new SearchCommand(obj, tm);
                break;
            case UNDO_FUNCTION:
                command = new UndoCommand(obj, tm);
                break;
        }
        command.execute();
    }
}
