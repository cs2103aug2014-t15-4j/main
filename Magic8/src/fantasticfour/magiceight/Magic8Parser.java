package fantasticfour.magiceight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Magic8Parser {
    static class CommandObject {
        String function;
        String taskDescription;
        ArrayList<String> tags;
        ArrayList<String> keywords;
        ArrayList<Integer> ids;
        Date deadline;

        CommandObject() {
            function = null;
            taskDescription = null;
            tags = null;
            ids = null;
            deadline = null;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public void setTaskDescription(String taskDescription) {
            this.taskDescription = taskDescription;
        }

        public ArrayList<String> getTags() {
            return tags;
        }

        public void setTags(ArrayList<String> tags) {
            this.tags = tags;
        }

        public ArrayList<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(ArrayList<String> keywords) {
            this.keywords = keywords;
        }

        public ArrayList<Integer> getIds() {
            return ids;
        }

        public void setIds(ArrayList<Integer> ids) {
            this.ids = ids;
        }

        public Date getDeadline() {
            return deadline;
        }

        public void setDeadline(Date deadline) {
            this.deadline = deadline;
        }

		public int getLineNumber() {
			// TODO Auto-generated method stub
			return 0;
		}
    }

    private static ArrayList<String> commandRegexList = new ArrayList<String>() {
        {
            add("add \\w+(\\s\\w+)+?((\\s#\\w+)+)?( by \\d{1,2}/\\d{1,2}/\\d{2,4})?");
            add("clear");
            add("delete(\\sall|\\s\\d+( to \\d+|(,\\d+)+)?|\\s\\*|\\s#\\w+((\\s#\\w+)+)?)");
            add("display((\\s#\\w+)+)?");
            add("edit \\d+( \\w+)+");
            add("exit");
            add("help");
            add("search \\w+");
            add("undo");
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

    public static CommandObject parseCommand(String command) throws IllegalArgumentException {
        CommandObject parsedCmdOutput = new CommandObject();
        if (isCommandValid(command)) {
            Date deadline = null;
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
            parsedCmdOutput.setFunction(function);

            switch (function) {
                case "add" :
                    boolean deadlineFlag = false;

                    for (String commandParam : commandParams) {
                        if (commandParam.equals("by")) {
                            deadlineFlag = true;
                            continue;
                        }
                        if (deadlineFlag) {
                            try {
                                deadline = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(commandParam);
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
                case "delete" :
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
                case "edit" :
                    ids.add(Integer.parseInt(commandParams[0]));

                    for (int i = 1; i < commandParams.length; i++) {
                        taskDesc.add(commandParams[i]);
                    }
                    
                    break;
                case "search" :
                    for(String keyword : commandParams) {
                        keywords.add(keyword);
                    }
                    
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
