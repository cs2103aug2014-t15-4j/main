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
        int lineNumber;
        int lineNumberTo;
        Date deadline;

        CommandObject() {
            function = null;
            taskDescription = null;
            tags = null;
            lineNumber = -1;
            lineNumberTo = -1;
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

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public int getLineNumberTo() {
            return lineNumberTo;
        }

        public void setLineNumberTo(int lineNumberTo) {
            this.lineNumberTo = lineNumberTo;
        }

        public Date getDeadline() {
            return deadline;
        }

        public void setDeadline(Date deadline) {
            this.deadline = deadline;
        }
    }

    private static ArrayList<String> commandRegexList = new ArrayList<String>() {
        {
            add("add \\w+((\\s#\\w+)+)?( by \\w+)?");
            add("clear");
            add("delete (\\w+|\\d+ to \\d+|\\*");
            add("display");
            add("edit \\d+( \\w+)+");
            add("help");
            add("search \\w+");
            add("undo \\d+?");
        }
    };

    public static CommandObject parseCommand(String command) {
        CommandObject parsedCmdOutput = new CommandObject();
        if (isCommandValid(command)) {
            int lineNum = -1;
            int lineNumTo = -1;
            Date deadline = null;
            ArrayList<String> taskDesc = new ArrayList<String>();
            ArrayList<String> tags = new ArrayList<String>();
            ArrayList<String> keywords = new ArrayList<String>();

            String[] commandElems = command.split("\\s+");
            String function = commandElems[0];
            String[] commandParams = Arrays.copyOfRange(commandElems, 1,
                    commandElems.length - 1);

            parsedCmdOutput.setFunction(function);

            if (commandElems.length == 1) {
                return parsedCmdOutput;
            }

            switch (function) {
                case "add" :
                    boolean deadlineFlag = false;

                    for (String commandParam : commandParams) {
                        if (commandParam == "by") {
                            deadlineFlag = true;
                            continue;
                        }
                        if (deadlineFlag) {
                            try {
                                deadline = new SimpleDateFormat("MMMM d, yyyy",
                                        Locale.ENGLISH).parse(commandParam);
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
                    boolean lineToFlag = false;

                    for (String commandParam : commandParams) {
                        if (commandParam == "*" || commandParam == "all") {
                            break;
                        } else if (lineToFlag) {
                            if (commandParam == "to") {
                                continue;
                            }
                            lineNumTo = Integer.parseInt(commandParam);
                        } else if (Pattern.matches("\\d+", commandParam)) {
                            lineToFlag = true;
                            lineNum = Integer.parseInt(commandParam);
                        } else {
                            tags.add(commandParam);
                        }
                    }

                    break;
                case "edit" :
                    lineNum = Integer.parseInt(commandParams[0]);

                    for (int i = 1; i < commandParams.length; i++) {
                        taskDesc.add(commandParams[i]);
                    }
                    
                    break;
                case "search" :
                    for(String keyword : commandParams) {
                        keywords.add(keyword);
                    }
                    
                    break;
                case "undo" :
                    lineNum = Integer.parseInt(commandParams[0]);
                    
                    break;
                default:
                    System.out.println("New functions not implemented!");
            }

            if (taskDesc.size() > 0) {
                parsedCmdOutput.setTaskDescription(taskDesc.toString());
            }

            if (tags.size() > 0) {
                parsedCmdOutput.setTags(tags);
            }

            if (keywords.size() > 0) {
                parsedCmdOutput.setKeywords(keywords);
            }
            
            parsedCmdOutput.setDeadline(deadline);
            parsedCmdOutput.setLineNumber(lineNum);
            parsedCmdOutput.setLineNumberTo(lineNumTo);
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
