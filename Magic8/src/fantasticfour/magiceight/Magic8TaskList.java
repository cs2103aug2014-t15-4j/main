package fantasticfour.magiceight;

import java.util.HashMap;
import java.util.HashSet;

class Magic8TaskList implements Magic8TaskListInterface {
    private HashMap<Integer, Magic8Task> taskList;
    private int id;
    private HashMap<String, HashSet<Integer>> tagToTaskIdsMap;

    public Magic8TaskList(HashMap<Integer, Magic8Task> taskList, int id) {
        this.taskList = taskList;
        this.id = id;
    }

    public Magic8Task addTask(Magic8Task task) {
        int taskId = this.id++;
        task.setId(taskId);
        this.taskList.put(taskId, task);
        indexTask(task);

        return task;
    }

    public Magic8Task removeTask(Magic8Task task) {
        int taskId = task.getId();
        Magic8Task storedTask = this.taskList.remove(taskId);

        if (storedTask != null) {
            unindexTask(task);
        }

        return storedTask;
    }

    public boolean updateTask(Magic8Task task) {
        boolean result = false;

        Magic8Task storedTask = removeTask(task);
        if (storedTask != null) {
            addTask(task);
            result = true;
        }

        return result;
    }

    public boolean clearTasks() {
        boolean result = false;

        if (!this.taskList.isEmpty()) {
            this.taskList.clear();
            result = true;
        }

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
}
