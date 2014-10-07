package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;

class Magic8Storage implements Magic8StorageInterface {
    private static final String DELIMITER = ",";
    private String fileName;
    private File file;
    private int id;
    private TreeMap<Integer, Magic8Task> taskList;

    public Magic8Storage(String fileName) throws IOException, ParseException {
        this.fileName = fileName;
        this.file = new File(this.fileName);
        this.taskList = new TreeMap<Integer, Magic8Task>();

        if (!this.file.exists()) {
            this.file.createNewFile();
            this.id = 1;
        } else {
            this.parseFile();
        }
    }

    private void parseFile() throws IOException, ParseException {
        String line;
        Magic8Task task;

        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.file)));
        this.id = Integer.parseInt(br.readLine());
        while ((line = br.readLine()) != null) {
            task = Magic8Task.parse(line, DELIMITER);
            this.taskList.put(task.getId(), task);
        }
        br.close();
    }

    public void writeToFile(int id, Map<Integer, Magic8Task> taskList)
            throws IOException {
        if (taskList instanceof TreeMap) {
            this.taskList = (TreeMap<Integer, Magic8Task>) taskList;
        } else {
            this.taskList.putAll(taskList);
        }
        this.id = id;
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                new FileWriter(file)));
        Magic8Task task;
        pw.println(this.id);
        for (Map.Entry<Integer, Magic8Task> entry : this.taskList.entrySet()) {
            task = entry.getValue();
            pw.println(task.toString(DELIMITER));
        }
        pw.close();
    }

    public int getId() {
        return this.id;
    }

    public TreeMap<Integer, Magic8Task> getTaskList() {
        return this.taskList;
    }
}
