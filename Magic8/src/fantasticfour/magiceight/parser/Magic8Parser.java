package fantasticfour.magiceight.parser;

import java.util.ArrayList;
import java.util.regex.Pattern;

import fantasticfour.magiceight.Magic8CommandObject;
import fantasticfour.magiceight.Magic8Status;

public class Magic8Parser {
    private enum Command_t {
        add,
        clear,
        delete,
        display,
        edit,
        exit,
        help,
        open,
        undo,
        redo
    }
    
    public class ReturnValues {
        private Magic8CommandObject commandObject;
        private Magic8Status status;
        
        private ReturnValues(Magic8CommandObject obj, Magic8Status stat) {
            commandObject = obj;
            status = stat;
        }
        
        public Magic8CommandObject getCommandObject() {
            return commandObject;
        }
        
        public Magic8Status getStatus() {
            return status;
        }
    }
    
    private final static String LETTER = "[^#][\\p{Alnum}\\p{Punct}]";
    private final static String WORD = LETTER + "+";
    private final static String TAG = "#" + WORD;
    private final static String TAGS = "(" + TAG + ")" + "*";
    private final static String SENTENCE = "(" + WORD + ")" + "+";
    private final static String NUMBER = "\\d+";
    private final static String NUMBER_GROUP = "[" + NUMBER + "\\s,-]+"; 
    
    
    private static String generateRegex(String[] strArr) {
        String regex = "\\s*";
        for(String str : strArr) {
            regex = regex + str + "\\s*";
        }
        return regex;
    }
        
    private final static String ADD_FUNCTION = "add";
    private final static String[] ADD = {ADD_FUNCTION, SENTENCE, TAGS};
    
    private final static String CLEAR_FUNCTION ="clear";
    private final static String[] CLEAR = {CLEAR_FUNCTION};
    
    private final static String DELETE_FUNCTION = "delete";
    private final static String[] DELETE_ALL = {DELETE_FUNCTION, "all|\\*"};
    private final static String[] DELETE_INDEX = {DELETE_FUNCTION, NUMBER_GROUP};
    private final static String[] DELETE_TAG = {DELETE_FUNCTION, TAGS};
    
    private final static String DISPLAY_FUNCTION = "display";
    private final static String[] DISPLAY_ALL = {DISPLAY_FUNCTION};
    private final static String[] DISPLAY_TAG = {DISPLAY_FUNCTION, TAG};
    private final static String[] DISPLAY_KWORD = {DISPLAY_FUNCTION, WORD};
    
    private final static String DONE_FUNCTION = "done";
    private final static String[] DONE_ALL = {DONE_FUNCTION, "all|\\*"};
    private final static String[] DONE_INDEX = {DONE_FUNCTION, NUMBER_GROUP};
    private final static String[] DONE_TAG = {DONE_FUNCTION, TAGS};
    
    private final static String EDIT_FUNCTION = "edit";
    private final static String[] EDIT = {EDIT_FUNCTION, NUMBER, SENTENCE, TAGS};
    
    private final static String EXIT_FUNCTION = "exit";
    private final static String[] EXIT = {EXIT_FUNCTION};
    
    private final static String HELP_FUNCTION = "help";
    private final static String[] HELP = {HELP_FUNCTION};
    
    private final static String OPEN_FUNCTION = "open";
    private final static String[] OPEN = {OPEN_FUNCTION, WORD};
    
    private final static String UNDO_FUNCTION = "undo";
    private final static String[] UNDO = {UNDO_FUNCTION};
    
    private final static String REDO_FUNCTION = "redo";
    private final static String[] REDO = {REDO_FUNCTION};    
    
    private static ArrayList<String> REGEX_GROUP = new ArrayList<String>() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
            add(generateRegex(ADD));
            add(generateRegex(CLEAR));
            add(generateRegex(DELETE_ALL));
            add(generateRegex(DELETE_INDEX));
            add(generateRegex(DELETE_TAG));
            add(generateRegex(DONE_ALL));
            add(generateRegex(DONE_INDEX));
            add(generateRegex(DONE_TAG));
            add(generateRegex(DISPLAY_ALL));
            add(generateRegex(DISPLAY_KWORD));
            add(generateRegex(DISPLAY_TAG));
            add(generateRegex(EDIT));
            add(generateRegex(EXIT));
            add(generateRegex(HELP));
            add(generateRegex(OPEN));
            add(generateRegex(UNDO));
            add(generateRegex(REDO));
        }
    };

    public static ReturnValues parseCommand(String command) throws IllegalArgumentException {
    	Magic8Parser _this = new Magic8Parser();
        Magic8CommandObject cmdObj = null;
        Magic8Status status = null;
        IParser parser = null;
        
        if(command.startsWith(ADD_FUNCTION)) {
            if(isCommandValid(command)) {
                parser = new AddParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.ADD_INPUT_MATCH;
            } else {
            	status = Magic8Status.ADD_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(CLEAR_FUNCTION)) {
            if(isCommandValid(command)) {
                status = Magic8Status.CLEAR_INPUT_MATCH;
            } else {
                status = Magic8Status.CLEAR_INPUT_MISMATCH;
            }                
        } else if(command.startsWith(DELETE_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new DeleteParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.DELETE_INPUT_MATCH;
            } else {
                status = Magic8Status.DELETE_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(DONE_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new DoneParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.DONE_INPUT_MATCH;
            } else {
                status = Magic8Status.DONE_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(DISPLAY_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new DisplayParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.DISPLAY_INPUT_MATCH;
            } else {
                status =  Magic8Status.DISPLAY_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(EDIT_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new EditParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.EDIT_INPUT_MATCH;
            } else {
                status = Magic8Status.EDIT_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(EXIT_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new ExitParser();
                cmdObj = parser.parse(command);
            } else {
                status = Magic8Status.EXIT_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(HELP_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new HelpParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.HELP_INPUT_MATCH;
            } else {
                status = Magic8Status.HELP_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(OPEN_FUNCTION)) {
            if(isCommandValid(command)) {
                
            } else {
                status = Magic8Status.OPEN_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(UNDO_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new UndoParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.UNDO_INPUT_MATCH;
            } else {
                status = Magic8Status.UNDO_INPUT_MISMATCH;
            }            
        } else if(command.startsWith(REDO_FUNCTION)) {
            if(isCommandValid(command)) {
            	parser = new RedoParser();
                cmdObj = parser.parse(command);
                status = Magic8Status.REDO_INPUT_MATCH;
            } else {
                status = Magic8Status.REDO_INPUT_MISMATCH;
            }            
        } else {
            status = Magic8Status.INPUT_MISMATCH;
        }
		return _this.new ReturnValues(cmdObj, status);
    }

    public static boolean isCommandValid(String command) {
        for (String allowedCommandRegex : REGEX_GROUP) {
            if (Pattern.matches(allowedCommandRegex, command)) {
                return true;
            }
        }
        return false;
    }
}
