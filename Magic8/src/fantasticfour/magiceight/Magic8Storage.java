package fantasticfour.magiceight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

class Magic8Storage implements Magic8StorageInterface {
    private static final String STRING_EMPTY = "";
    private static final String STRING_PARSED_EMPTY = "@empty";
    private static final String STRING_PARSED_NULL = "@null";
    private static final String FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";
    private static final int INDEX_ID = 0;
    private static final int INDEX_DESC = 1;
    private static final int INDEX_IS_DONE = 2;
    private static final int INDEX_START_TIME = 3;
    private static final int INDEX_END_TIME = 4;
    private static final int INDEX_TAGS = 5;
    private static final int NUM_FIELDS = 6;
    private static final String MSG_INSUFFICIENT_ARGUMENTS = "Error insufficient arguments";
    
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
                    stringArrayToMagic8Task(stringArray));
        }
        return treeMap;
    }

    private ArrayList<String[]> convertMapToArrayList(
            Map<Integer, Magic8Task> map) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        for (Map.Entry<Integer, Magic8Task> entry : map.entrySet()) {
            list.add(magic8TaskToStringArray(entry.getValue()));
        }
        return list;
    }
    
    public static String[] magic8TaskToStringArray(Magic8Task task) {
        String[] stringArray = new String[NUM_FIELDS];
        stringArray[INDEX_ID] = Integer.toString(task.getId());
        stringArray[INDEX_DESC] = task.getDesc();
        
        stringArray[INDEX_IS_DONE] = Boolean.toString(task.isDone());
        DateFormat df = new SimpleDateFormat(FORMAT_DATE);

        if (task.getStartTime() == null) {
            stringArray[INDEX_START_TIME] = STRING_PARSED_NULL;
        } else {
            stringArray[INDEX_START_TIME] = df.format(task.getStartTime().getTime());
        }

        if (task.getEndTime() == null) {
            stringArray[INDEX_END_TIME] = STRING_PARSED_NULL;
        } else {
            stringArray[INDEX_END_TIME] = df.format(task.getEndTime().getTime());
        }

        if (task.getTags().size() == 0) {
            stringArray[INDEX_TAGS] = STRING_PARSED_EMPTY;
        } else {
            stringArray[INDEX_TAGS] = STRING_EMPTY;
            for (String tag : task.getTags()) {
                stringArray[INDEX_TAGS] += " " + tag;
            }
            stringArray[INDEX_TAGS] = stringArray[INDEX_TAGS].trim();
        }

        return stringArray;
    }
    
    public static Magic8Task stringArrayToMagic8Task(String[] stringArray)
            throws IllegalArgumentException, ParseException {

        DateFormat df = new SimpleDateFormat(FORMAT_DATE);
        
        if (stringArray.length >= NUM_FIELDS) {
            int id = Integer.parseInt(stringArray[INDEX_ID]);
            String desc = stringArray[INDEX_DESC];
            boolean isDone = Boolean.parseBoolean(stringArray[INDEX_IS_DONE]);
            Calendar startTime = null;
            Calendar endTime = null;
            HashSet<String> tags;

            if (!stringArray[INDEX_START_TIME].equals(STRING_PARSED_NULL)) {
                startTime = Calendar.getInstance();
                startTime.setTime(df.parse(stringArray[INDEX_START_TIME]));
            }

            if (!stringArray[INDEX_END_TIME].equals(STRING_PARSED_NULL)) {
                endTime = Calendar.getInstance();
                endTime.setTime(df.parse(stringArray[INDEX_END_TIME]));
            }

            if (stringArray[INDEX_TAGS].equals(STRING_PARSED_NULL)) {
                tags = null;
            } else if (stringArray[INDEX_TAGS].equals(STRING_PARSED_EMPTY)) {
                tags = new HashSet<String>();
            } else {
                tags = new HashSet<String>();
                String[] tagStrings = stringArray[INDEX_TAGS].split("\\s+");
                for (String tagString : tagStrings) {
                    tags.add(tagString);
                }
            }

            Magic8Task task = new Magic8Task(id, desc, startTime, endTime, tags);
            return task;
        } else {
            throw new IllegalArgumentException(MSG_INSUFFICIENT_ARGUMENTS);
        }
    }

}