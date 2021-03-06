package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;

import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;
import fantasticfour.magiceight.Magic8TaskList;

//@author A0080527H
public interface ICommand {
    public abstract void execute() throws IOException;
    public abstract void addStatus(String message);
    public abstract String getStatusMessage();
    public abstract Magic8Status getStatusInfo();
    public abstract ArrayList<Magic8Task> getReturnTaskList();
    public abstract Magic8TaskList getTaskManager();
}   
