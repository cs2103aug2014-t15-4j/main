package fantasticfour.magiceight;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Magic8Parser {
    private static ArrayList<String> commandRegexList = new ArrayList<String>() {
        {
            add("add \\w+((\\s#\\w+)+)?( by \\w+)?");
            add("clear");
            add("delete (\\w+|\\d+ to \\d+|\\*");
            add("display");
            add("edit \\d+ \\w+");
            add("help");
            add("search \\w+");
            add("undo( \\d+)+");
        }
    };
    
    public static void parseCommand(String command) {
        // TODO Call Magic8Interface Commands
    }

    public static boolean isCommandValid(String command) {
        for(String allowedCommandRegex : commandRegexList) {
            if(Pattern.matches(allowedCommandRegex, command)) {
                return true;
            }
        }
        return false;
    }
       
}
