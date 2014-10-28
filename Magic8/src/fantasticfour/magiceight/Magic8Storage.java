package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

class Magic8Storage implements Magic8StorageInterface {
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
        } else if (fileIsEmpty()){
            this.id = 1;
        } else {
            this.readFromFile();
        }
    }
    
    private boolean fileIsEmpty() throws IOException {
        boolean empty = false;
        BufferedReader br = new BufferedReader(new FileReader(fileName));     
        if (br.readLine() == null) {
            empty = true;
        }
        br.close();
        return empty;
    }
    
    private void readFromFile() throws IOException, ParseException {
        CSVReader csvr = new CSVReader(new BufferedReader(
                new InputStreamReader(new FileInputStream(this.file))));

        this.id = Integer.parseInt(csvr.readNext()[0]);
        this.taskList = convertListToTreeMap(csvr.readAll());
        csvr.close();
    }

    public void writeToFile(int id, Map<Integer, Magic8Task> taskList)
            throws IOException {
        this.id = id;
        CSVWriter csvw = new CSVWriter(new BufferedWriter(new FileWriter(file)));
        String[] firstLine = { Integer.toString(id) };
        ArrayList<String[]> list = convertMapToArrayList(taskList);

        csvw.writeNext(firstLine);
        csvw.writeAll(list);
        csvw.close();
    }

    public int getId() {
        return this.id;
    }

    public TreeMap<Integer, Magic8Task> getTaskList() {
        return this.taskList;
    }

    private TreeMap<Integer, Magic8Task> convertListToTreeMap(List<String[]> list)
            throws IllegalArgumentException, ParseException {
        TreeMap<Integer, Magic8Task> treeMap = new TreeMap<Integer, Magic8Task>();
        for (String[] stringArray : list) {
            treeMap.put(Integer.parseInt(stringArray[0]),
                    Magic8Task.stringArrayToMagic8Task(stringArray));
        }
        return treeMap;
    }

    private ArrayList<String[]> convertMapToArrayList(
            Map<Integer, Magic8Task> map) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        for (Map.Entry<Integer, Magic8Task> entry : map.entrySet()) {
            list.add(entry.getValue().toStringArray());
        }
        return list;
    }
}