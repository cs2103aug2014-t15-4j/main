package fantasticfour.magiceight;

import java.util.HashMap;

class Magic8TaskList implements Magic8TaskListInterface {
    private HashMap<Integer, Magic8Task> taskList;
    private int id;

    public Magic8TaskList(HashMap<Integer, Magic8Task> taskList, int id) {
        this.taskList = taskList;
        this.id = id;
    }

    public Magic8Task addTask(Magic8Task task) {
        int taskId = this.id++;
        task.setId(taskId);
        this.taskList.put(taskId, task);

        return task;
    }

    public Magic8Task removeTask(Magic8Task task) {
        int taskId = task.getId();
        return this.taskList.remove(taskId);
    }

    public boolean updateTask(Magic8Task task) {
        boolean result = false;

        int taskId = task.getId();
        if (this.taskList.containsKey(taskId)) {
            this.taskList.put(taskId, task);
            result = true;
        }

        return result;
    }

    public boolean clearTasks() {
        boolean result = false;

        if (!this.taskList.isEmpty()) {
            this.taskList = new HashMap<Integer, Magic8Task>();
            result = true;
        }

        return result;
    }
}
