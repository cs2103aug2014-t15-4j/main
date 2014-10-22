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

        int taskId = id++;
        task.setId(taskId);
        taskList.put(taskId, task);
        indexTask(task);
        storage.writeToFile(taskId, taskList);
        return task;
    }

    public Magic8Task removeTask(Magic8Task task) throws IOException {
        assert (task != null);

        int taskId = task.getId();
        Magic8Task storedTask = taskList.remove(taskId);

        if (storedTask != null) {
            unindexTask(task);
        }
        storage.writeToFile(taskId, taskList);
        return storedTask;
    }

    public boolean updateTask(Magic8Task task) throws IOException {
        assert (task != null);

        boolean result = false;

        Magic8Task storedTask = removeTask(task);
        if (storedTask != null) {
            addTask(task);
            result = true;
        }

        return result;
    }

    public boolean clearTasks() throws IOException {
        boolean result = false;

        if (!taskList.isEmpty()) {
            taskList.clear();
            result = true;
        }
        storage.writeToFile(id, taskList);
        return result;
    }

    private boolean indexTask(Magic8Task task) {
        assert (task != null);

        boolean result = false;

        unindexTask(task);

        int taskId = task.getId();
        for (String tag : task.getTags()) {
            HashSet<Integer> taskIdsWithTag = tagToTaskIds.get(tag);
            taskIdsWithTag.add(taskId);
        }

        result = true;

        return result;
    }

    private boolean unindexTask(Magic8Task task) {
        assert (task != null);

        boolean result = false;

        int taskId = task.getId();
        Magic8Task storedTask = taskList.get(taskId);

        if (storedTask != null) {
            for (String tag : storedTask.getTags()) {
                HashSet<Integer> taskIdsWithTag = tagToTaskIds.get(tag);
                taskIdsWithTag.remove(taskId);
            }

            result = true;
        }

        return result;
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
