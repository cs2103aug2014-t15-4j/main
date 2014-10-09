package fantasticfour.magiceight;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

class Magic8TaskList implements Magic8TaskListInterface {
    private TreeMap<Integer, Magic8Task> taskList;
    private TreeMap<Integer, Magic8Task> bufferedTaskList;
    private int id;
    private HashMap<String, HashSet<Integer>> tagToTaskIdsMap;
    private Magic8Storage storage;

    public Magic8TaskList(String fileName) throws ParseException, IOException {
        storage = new Magic8Storage(fileName);
        taskList = storage.getTaskList();
        id = storage.getId();
        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            indexTask(entry.getValue());
        }
    }

    public Magic8Task addTask(Magic8Task task) throws IOException {
        int taskId = this.id++;
        task.setId(taskId);
        this.taskList.put(taskId, task);
        indexTask(task);
        storage.writeToFile(taskId, taskList);
        return task;
    }

    public Magic8Task removeTask(Magic8Task task) throws IOException {
        int taskId = task.getId();
        Magic8Task storedTask = this.taskList.remove(taskId);

        if (storedTask != null) {
            unindexTask(task);
        }
        storage.writeToFile(taskId, taskList);
        return storedTask;
    }

    public boolean updateTask(Magic8Task task) throws IOException {
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

        if (!this.taskList.isEmpty()) {
            this.taskList.clear();
            result = true;
        }
        storage.writeToFile(id, taskList);
        return result;
    }

    private boolean indexTask(Magic8Task task) {
        boolean result = false;

        unindexTask(task);

        int taskId = task.getId();
        for (String tag : task.getTags()) {
            HashSet<Integer> taskIdsWithTag = tagToTaskIdsMap.get(tag);
            taskIdsWithTag.add(taskId);
        }

        result = true;

        return result;
    }

    private boolean unindexTask(Magic8Task task) {
        boolean result = false;

        int taskId = task.getId();
        Magic8Task storedTask = taskList.get(taskId);

        if (storedTask != null) {
            for (String tag : storedTask.getTags()) {
                HashSet<Integer> taskIdsWithTag = tagToTaskIdsMap.get(tag);
                taskIdsWithTag.remove(taskId);
            }

            result = true;
        }

        return result;
    }

    public TreeMap<Integer, Magic8Task> getAllTasks() {
        this.bufferedTaskList.clear();
        this.bufferedTaskList.putAll(taskList);

        return this.bufferedTaskList;
    }

    public TreeMap<Integer, Magic8Task> getTasksWithTag(String tag) {
        this.bufferedTaskList.clear();

        HashSet<Integer> taskIdsWithTag = tagToTaskIdsMap.get(tag);
        for (Integer taskId : taskIdsWithTag) {
            bufferedTaskList.put(taskId, taskList.get(taskId));
        }

        return this.bufferedTaskList;
    }
}
