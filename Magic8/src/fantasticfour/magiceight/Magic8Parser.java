package fantasticfour.magiceight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class Magic8Parser {
    private final static String ADD_FUNCTION = "add";
    private final static String ADD_DESC = "(\\s[^#][\\w\\p{Punct}]+)+";
    private final static String ADD_TAGS = "(\\s#\\w+)*";
    private final static String ADD_DEADLINE = "(\\sby\\s\\d{1,2}/\\d{1,2}/\\d{2,4})?";
    
    private final static String CLEAR_FUNCTION ="clear";
    
    private final static String DELETE_FUNCTION = "delete";
    private final static String DELETE_ALL = "\\sall|\\s\\*";
    private final static String DELETE_BY_INT = "\\s\\d+( to \\d+|(,\\d+)+)?";
    private final static String DELETE_BY_TAG = "(\\s#\\w+)+";
    private final static String DELETE_CONJUNC = "(" + DELETE_ALL + "|" + DELETE_BY_INT + "|" + DELETE_BY_TAG + ")";
    
    private final static String DISPLAY_FUNCTION = "display";
    private final static String DISPLAY_BY_TAG = "(\\s#\\w+)*";
    
    private final static String EDIT_FUNCTION = "edit";
    private final static String EDIT_ID = "\\s\\d+";
    private final static String EDIT_DESC = "(\\s\\w+)+";
    
    private final static String EXIT_FUNCTION = "exit";
    
    private final static String HELP_FUNCTION = "help|\\-h";
    
    private final static String OPEN_FUNCTION = "open";
    private final static String OPEN_FILENAME = "\\s[\\w\\p{Punct}]+";
    
    private final static String SEARCH_FUNCTION = "search";
    private final static String SEARCH_BY_KEYWORD = "(\\s+\\w+)+";
    
    private final static String UNDO_FUNCTION = "undo";
    
    private final static String REDO_FUNCTION = "redo";
    
    private final static String BLOCK_FUNCTION = "block";
    
    private final static String ADD_REGEX = ADD_FUNCTION + ADD_DESC + ADD_DEADLINE + ADD_TAGS;
    private final static String CLEAR_REGEX = CLEAR_FUNCTION;
    private final static String DELETE_REGEX = DELETE_FUNCTION + DELETE_CONJUNC;
    private final static String DISPLAY_REGEX = DISPLAY_FUNCTION + DISPLAY_BY_TAG;
    private final static String EDIT_REGEX = EDIT_FUNCTION + EDIT_ID + EDIT_DESC;
    private final static String EXIT_REGEX = EXIT_FUNCTION;
    private final static String HELP_REGEX = HELP_FUNCTION;
    private final static String OPEN_REGEX = OPEN_FUNCTION + OPEN_FILENAME;
    private final static String SEARCH_REGEX = SEARCH_FUNCTION + SEARCH_BY_KEYWORD;
    private final static String UNDO_REGEX = UNDO_FUNCTION;
    private final static String REDO_REGEX = REDO_FUNCTION;
    private final static String BLOCK_REGEX = BLOCK_FUNCTION;
    
    private static ArrayList<String> commandRegexList = new ArrayList<String>() {
        {
            add(ADD_REGEX);
            add(CLEAR_REGEX);
            add(DELETE_REGEX);
            add(DISPLAY_REGEX);
            add(EDIT_REGEX);
            add(EXIT_REGEX);
            add(HELP_REGEX);
            add(OPEN_REGEX);
            add(SEARCH_REGEX);
            add(UNDO_REGEX);
            add(REDO_REGEX);
            add(BLOCK_REGEX);
        }
    };
    
    private static String listToString(ArrayList<String> list) {
        String output = "";
        for(String str : list) {
            output += str;
            output += " ";
        }
        if(list.size() == 0) {
            return output;
        } else {
            return output.substring(0, output.length() - 1);
        }
    }

    public static Magic8CommandObject parseCommand(String command) throws IllegalArgumentException {
        Magic8CommandObject parsedCmdOutput = new Magic8CommandObject();
        if (isCommandValid(command)) {
            Calendar deadline = null;
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ArrayList<String> taskDesc = new ArrayList<String>();
            ArrayList<String> tags = new ArrayList<String>();
            ArrayList<String> keywords = new ArrayList<String>();
            
            String[] commandElems = command.split("\\s+");
            String function = commandElems[0];
            String[] commandParams = null;
            
            if(commandElems.length > 1) {
                commandParams = Arrays.copyOfRange(commandElems, 1, commandElems.length);
            }

            switch (function) {
                case ADD_FUNCTION :
                    boolean deadlineFlag = false;

                    for (String commandParam : commandParams) {
                        if (commandParam.equals("by")) {
                            deadlineFlag = true;
                            deadline = new GregorianCalendar();
                            continue;
                        }
                        if (deadlineFlag) {
                            try {
                                deadline.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(commandParam));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if (commandParam.startsWith("#")) {
                            tags.add(commandParam.substring(1));
                        } else {
                            taskDesc.add(commandParam);
                        }
                    }
                    break;
                    
                case DELETE_FUNCTION :
                    int id = 0;
                    boolean lineToFlag = false;
                    
                    for (String commandParam : commandParams) {
                        if (commandParam.equals("*") || commandParam.equals("all")) {
                            break;
                        } else if (lineToFlag) {
                            if (commandParam.equals("to")) {
                                continue;
                            }
                            for(int i = id+1; i <= Integer.parseInt(commandParam); i++) {
                                ids.add(i);
                            }
                        } else if (Pattern.matches("\\d+", commandParam)) {
                            lineToFlag = true;
                            id = Integer.parseInt(commandParam);
                            ids.add(id);
                        } else if (Pattern.matches("\\d+(,\\d+)+?", commandParam)){
                            String[] idList = commandParam.split(",");
                            for(String sId : idList) {
                                ids.add(Integer.parseInt(sId));
                            }
                        } else {                            
                            tags.add(commandParam.substring(1));
                        }
                    }                    
                    break;
                    
                case DISPLAY_FUNCTION:
                    if(commandParams != null) {
                        for(String commandParam : commandParams) {
                            if (commandParam.startsWith("#")) {
                                tags.add(commandParam.substring(1));
                            }
                        }
                    }
                    break;
                    
                case EDIT_FUNCTION :
                    ids.add(Integer.parseInt(commandParams[0]));

                    for (int i = 1; i < commandParams.length; i++) {
                        taskDesc.add(commandParams[i]);
                    }                    
                    break;
                    
                case SEARCH_FUNCTION :
                    for(String keyword : commandParams) {
                        keywords.add(keyword);
                    }                    
                    break;
                    
                case OPEN_FUNCTION :
                    taskDesc.add(commandParams[0]);                 
                    break;
                    
            }

            if (taskDesc.size() > 0) {
                parsedCmdOutput.setTaskDescription(listToString(taskDesc));
            }

            if (tags.size() > 0) {
                parsedCmdOutput.setTags(tags);
            }

            if (keywords.size() > 0) {
                parsedCmdOutput.setKeywords(keywords);
            }
            if (ids.size() > 0) {
                parsedCmdOutput.setIds(ids);
            }

            parsedCmdOutput.setFunction(function);
            parsedCmdOutput.setDeadline(deadline);
        } else {
            throw new IllegalArgumentException("Invalid Command!");
        }
        return parsedCmdOutput;
    }

    public static boolean isCommandValid(String command) {
        for (String allowedCommandRegex : commandRegexList) {
            if (Pattern.matches(allowedCommandRegex, command)) {
                return true;
            }
        }
        return false;
    }
}
