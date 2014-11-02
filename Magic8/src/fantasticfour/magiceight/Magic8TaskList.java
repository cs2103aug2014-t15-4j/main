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
    private ArrayList<Magic8Task> bufferedTaskList;
    private HashMap<String, HashSet<Integer>> tagToTaskIds;

    private int opIdx;
    private ArrayList<Integer> ids;
    private ArrayList<TreeMap<Integer, Magic8Task>> taskLists;

    public Magic8TaskList(String fileName) throws IOException, ParseException {
        storage = new Magic8Storage(fileName);
        id = storage.getId();
        taskList = storage.getTaskList();
        bufferedTaskList = new ArrayList<Magic8Task>();
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

        return new Magic8Task(task);
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
    public Magic8Task removeTask(Magic8Task task) throws IOException {
        assert task != null;

        // Remove task from task list
        int taskId = task.getId();
        Magic8Task storedTask = taskList.remove(taskId);
        if (storedTask != null) {
            unindexTask(task);
            writeToFile();

            backupTaskList();

            return new Magic8Task(storedTask);
        }

        return null;
    }

    @Override
    public ArrayList<Magic8Task> removeTasks(ArrayList<Magic8Task> tasks)
            throws IOException {
        bufferedTaskList.clear();

        if (!tasks.isEmpty()) {
            for (Magic8Task task : tasks) {
                Magic8Task storedTask = taskList.remove(task.getId());

                if (storedTask == null) {
                    taskList = copyTaskList(taskLists.get(opIdx));
                    indexTaskList();

                    return null;
                } else {
                    bufferedTaskList.add(storedTask);
                }
            }

            writeToFile();

            backupTaskList();

            return bufferedTaskList;
        }

        return null;
    }

    @Override
    public ArrayList<Magic8Task> removeTasksWithTag(String tag)
            throws IOException {
        if (tagToTaskIds.containsKey(tag)) {
            for (Integer taskId : tagToTaskIds.get(tag)) {
                Magic8Task task = taskList.remove(taskId);
                unindexTask(task);
                bufferedTaskList.add(task);
            }
            writeToFile();

            backupTaskList();

            return bufferedTaskList;
        }

        return null;
    }

    @Override
    public ArrayList<Magic8Task> clearTasks() throws IOException {
        if (!taskList.isEmpty()) {
            // Remove all tasks from task list
            getAllTasks();
            taskList.clear();
            tagToTaskIds.clear();
            writeToFile();

            backupTaskList();

            return bufferedTaskList;
        }

        return null;
    }

    @Override
    public boolean undo() throws IOException {
        if (opIdx == 0) {
            return false;
        }

        opIdx--;
        id = ids.get(opIdx);
        taskList = copyTaskList(taskLists.get(opIdx));
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
        taskList = copyTaskList(taskLists.get(opIdx));
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

            if (taskIdsWithTag.size() == 0) {
                tagToTaskIds.remove(tag);
            }
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
    public ArrayList<Magic8Task> getAllTasks() {
        bufferedTaskList.clear();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = new Magic8Task(entry.getValue());
            bufferedTaskList.add(task);
        }

        return bufferedTaskList;
    }

    @Override
    public ArrayList<Magic8Task> getTasksWithWord(String word, boolean isTag) {
        bufferedTaskList.clear();

        if (isTag) {
            for (Integer taskId : tagToTaskIds.get(word)) {
                Magic8Task task = new Magic8Task(taskList.get(taskId));
                bufferedTaskList.add(task);
            }
        } else {
            for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
                Magic8Task task = new Magic8Task(entry.getValue());
                if (task.getDesc().contains(word)) {
                    bufferedTaskList.add(task);
                }
            }
        }

        return bufferedTaskList;
    }
}
