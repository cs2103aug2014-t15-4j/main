package fantasticfour.magiceight.command;

import java.io.IOException;
import java.util.ArrayList;

import fantasticfour.magiceight.Magic8Status;
import fantasticfour.magiceight.Magic8Task;

public interface ICommand {
    public abstract void execute() throws IOException;
    public abstract void addStatus(String message);
    public abstract String getStatusMessage();
    public abstract Magic8Status getStatusInfo();
    public abstract ArrayList<Magic8Task> getReturnTaskList();
}   
