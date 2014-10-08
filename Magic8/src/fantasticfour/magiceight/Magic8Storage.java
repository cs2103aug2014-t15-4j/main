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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import au.com.bytecode.opencsv.CSVReader;

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
        CSVReader csvr = new CSVReader(new BufferedReader(
                new InputStreamReader(new FileInputStream(this.file))));

        this.id = Integer.parseInt(csvr.readNext()[0]);
        this.taskList = convertListToTreeMap(csvr.readAll());
        csvr.close();
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

    public TreeMap<Integer, Magic8Task> convertListToTreeMap(List<String[]> list)
            throws IllegalArgumentException, ParseException {
        TreeMap<Integer, Magic8Task> treeMap = new TreeMap<Integer, Magic8Task>();
        for (String[] stringArray : list) {
            treeMap.put(Integer.parseInt(stringArray[0]),
                    Magic8Task.stringArrayToMagic8Task(stringArray));
        }
        return treeMap;
    }
}
