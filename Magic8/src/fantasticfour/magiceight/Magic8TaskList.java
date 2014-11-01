package fantasticfour.magiceight;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class Magic8TaskList implements Magic8TaskListInterface {
    private Magic8Storage storage;
    private int id;
    private TreeMap<Integer, Magic8Task> taskList;
    private TreeMap<Integer, Magic8Task> bufferedTaskList;
    private HashMap<String, HashSet<Integer>> tagToTaskIds;

    private int opIdx;
    private ArrayList<Integer> ids;
    private ArrayList<TreeMap<Integer, Magic8Task>> taskLists;

    public Magic8TaskList(String fileName) throws IOException, ParseException {
        storage = new Magic8Storage(fileName);
        id = storage.getId();
        taskList = storage.getTaskList();
        bufferedTaskList = new TreeMap<Integer, Magic8Task>();
        tagToTaskIds = new HashMap<String, HashSet<Integer>>();

        opIdx = 0;
        ids = new ArrayList<Integer>();
        ids.add(id);
        taskLists = new ArrayList<TreeMap<Integer, Magic8Task>>();
        taskLists.add(copyTaskList(taskList));

        indexTaskList();
    }

    @Override
    public Magic8Task addTask(Magic8Task task) throws IOException {
        assert task != null;

        task = new Magic8Task(task);

        // Assign task id
        int taskId = id++;
        task.setId(taskId);

        // Add task to task list
        taskList.put(taskId, task);
        indexTask(task);
        writeToFile();

        backupTaskList();

        return task;
    }

    @Override
    public Magic8Task removeTask(Magic8Task task) throws IOException {
        assert task != null;

        // Remove task from task list
        int taskId = task.getId();
        Magic8Task storedTask = taskList.remove(taskId);
        if (storedTask != null) {
            unindexTask(task);
        }
        writeToFile();

        backupTaskList();

        return new Magic8Task(storedTask);
    }

    @Override
    public boolean updateTask(Magic8Task task) throws IOException {
        assert task != null;

        task = new Magic8Task(task);

        boolean result = false;

        int taskId = task.getId();

        // Remove task from task list
        Magic8Task storedTask = taskList.remove(taskId);
        if (storedTask != null) {
            unindexTask(storedTask);

            // Add task to task list
            taskList.put(taskId, task);
            indexTask(task);
            writeToFile();

            backupTaskList();

            result = true;
        }

        return result;
    }

    @Override
    public boolean clearTasks() throws IOException {
        boolean result = false;

        if (!taskList.isEmpty()) {
            // Remove all tasks from task list
            taskList.clear();
            writeToFile();

            backupTaskList();

            result = true;
        }

        return result;
    }

    @Override
    public boolean undo() throws IOException {
        if (opIdx == 0) {
            return false;
        }

        opIdx--;
        id = ids.get(opIdx);
        taskList = taskLists.get(opIdx);
        writeToFile();

        indexTaskList();

        return true;
    }

    @Override
    public boolean redo() throws IOException {
        if (opIdx == ids.size() - 1) {
            return false;
        }

        opIdx++;
        id = ids.get(opIdx);
        taskList = taskLists.get(opIdx);
        writeToFile();

        indexTaskList();

        return true;
    }

    private void indexTask(Magic8Task task) {
        assert task != null;

        int taskId = task.getId();

        for (String tag : task.getTags()) {
            HashSet<Integer> taskIdsWithTag = tagToTaskIds.get(tag);
            if (taskIdsWithTag == null) {
                taskIdsWithTag = new HashSet<Integer>();
                tagToTaskIds.put(tag, taskIdsWithTag);
            }
            taskIdsWithTag.add(taskId);
        }
    }

    private void unindexTask(Magic8Task task) {
        assert task != null;

        int taskId = task.getId();

        for (String tag : task.getTags()) {
            HashSet<Integer> taskIdsWithTag = tagToTaskIds.get(tag);
            taskIdsWithTag.remove(taskId);
        }
    }

    private TreeMap<Integer, Magic8Task> copyTaskList(
            TreeMap<Integer, Magic8Task> tl) {
        TreeMap<Integer, Magic8Task> copy = new TreeMap<Integer, Magic8Task>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            copy.put(entry.getKey(), new Magic8Task(entry.getValue()));
        }

        return copy;
    }

    private void indexTaskList() {
        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            indexTask(entry.getValue());
        }
    }

    private void backupTaskList() {
        opIdx++;

        int size = ids.size();
        ids.subList(opIdx, size).clear();
        taskLists.subList(opIdx, size).clear();

        ids.add(id);
        taskLists.add(copyTaskList(taskList));
    }

    private void writeToFile() throws IOException {
        storage.writeToFile(id, taskList);
    }

    @Override
    public TreeMap<Integer, Magic8Task> getAllTasks() {
        bufferedTaskList.clear();

        bufferedTaskList = copyTaskList(taskList);

        return bufferedTaskList;
    }

    @Override
    public TreeMap<Integer, Magic8Task> getTasksWithTag(String tag) {
        bufferedTaskList.clear();

        for (Integer taskId : tagToTaskIds.get(tag)) {
            Magic8Task task = new Magic8Task(taskList.get(taskId));
            bufferedTaskList.put(task.getId(), task);
        }

        return bufferedTaskList;
    }

    @Override
    public TreeMap<Integer, Magic8Task> getTasksWithWord(String word) {
        bufferedTaskList.clear();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();
            if (task.getDesc().contains(word)) {
                bufferedTaskList.put(task.getId(), new Magic8Task(task));
            }
        }

        return bufferedTaskList;
    }

    @Override
    public boolean removeTasksWithTag(String tag) throws IOException {
        if (tagToTaskIds.containsKey(tag)) {
            for (Integer taskId : tagToTaskIds.get(tag)) {
                Magic8Task task = taskList.remove(taskId);
                if (task != null) {
                    unindexTask(task);
                }
            }
            writeToFile();

            backupTaskList();

            return true;
        }

        return false;
    }
}
