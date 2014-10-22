package fantasticfour.magiceight;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

class Magic8TaskList implements Magic8TaskListInterface {
    private Magic8Storage storage;
    private int id;
    private TreeMap<Integer, Magic8Task> taskList;
    private TreeMap<Integer, Magic8Task> bufferedTaskList;
    private HashMap<String, HashSet<Integer>> tagToTaskIds;

    public Magic8TaskList(String fileName) throws IOException, ParseException {
        storage = new Magic8Storage(fileName);
        id = storage.getId();
        taskList = storage.getTaskList();
        bufferedTaskList = new TreeMap<Integer, Magic8Task>();
        tagToTaskIds = new HashMap<String, HashSet<Integer>>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            indexTask(entry.getValue());
        }
    }

    public Magic8Task addTask(Magic8Task task) throws IOException {
        assert (task != null);

        // Assign task id
        int taskId = id++;
        task.setId(taskId);

        taskList.put(taskId, task);
        indexTask(task);
        writeToFile();

        return task;
    }

    public Magic8Task removeTask(Magic8Task task) throws IOException {
        assert (task != null);

        int taskId = task.getId();

        Magic8Task storedTask = taskList.remove(taskId);
        if (storedTask != null) {
            unindexTask(task);
        }
        writeToFile();

        return storedTask;
    }

    public boolean updateTask(Magic8Task task) throws IOException {
        assert (task != null);

        boolean result = false;

        int taskId = task.getId();

        Magic8Task storedTask = taskList.remove(taskId);
        if (storedTask != null) {
            unindexTask(storedTask);

            taskList.put(taskId, task);
            indexTask(task);
            writeToFile();

            result = true;
        }

        return result;
    }

    public boolean clearTasks() throws IOException {
        boolean result = false;

        if (!taskList.isEmpty()) {
            taskList.clear();
            writeToFile();

            result = true;
        }

        return result;
    }

    private void indexTask(Magic8Task task) {
        assert (task != null);

        int taskId = task.getId();

        for (String tag : task.getTags()) {
            HashSet<Integer> taskIdsWithTag = tagToTaskIds.get(tag);
            taskIdsWithTag.add(taskId);
        }
    }

    private void unindexTask(Magic8Task task) {
        assert (task != null);

        int taskId = task.getId();

        for (String tag : task.getTags()) {
            HashSet<Integer> taskIdsWithTag = tagToTaskIds.get(tag);
            taskIdsWithTag.remove(taskId);
        }
    }

    private void writeToFile() throws IOException {
        storage.writeToFile(id, taskList);
    }

    public TreeMap<Integer, Magic8Task> getAllTasks() {
        bufferedTaskList.clear();
        bufferedTaskList.putAll(taskList);

        return bufferedTaskList;
    }

    public TreeMap<Integer, Magic8Task> getTasksWithTag(String tag) {
        bufferedTaskList.clear();

        HashSet<Integer> taskIdsWithTag = tagToTaskIds.get(tag);
        for (Integer taskId : taskIdsWithTag) {
            bufferedTaskList.put(taskId, taskList.get(taskId));
        }

        return bufferedTaskList;
    }
}
