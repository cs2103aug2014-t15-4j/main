package fantasticfour.magiceight;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import fantasticfour.magiceight.Magic8Task;


public class Magic8 implements Magic8Interface {
    String fileName;
    File file;
    ArrayList<Magic8Task> taskList;
    ArrayList<Magic8Task> bufferedTaskList;
    PrintWriter pw;
    
    public Magic8(String fileName) {
        this.fileName = fileName;
        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Magic8Task addTask(Magic8Task task) {
        try {
            pw = new PrintWriter(fileName);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Magic8Task removeTask(int id) {
        return null;
    }

    public Magic8Task updateTask(Magic8Task task) {
        return null;
    }

    public void displayAll() {

    }
    
}
