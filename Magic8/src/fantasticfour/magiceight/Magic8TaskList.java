package fantasticfour.magiceight;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Magic8TaskList implements Magic8TaskListInterface {
    private Magic8Storage storage;
    private int id;
    private TreeMap<Integer, Magic8Task> taskList;

    private int tempId;
    private TreeMap<Integer, Magic8Task> tempTaskList;
    private ArrayDeque<Integer> idPast;
    private ArrayDeque<Integer> idFuture;
    private ArrayDeque<TreeMap<Integer, Magic8Task>> taskListPast;
    private ArrayDeque<TreeMap<Integer, Magic8Task>> taskListFuture;

    public Magic8TaskList(String fileName) throws IOException, ParseException {
        storage = new Magic8Storage(fileName);
        id = storage.getId();
        taskList = storage.getTaskList();
        idPast = new ArrayDeque<>();
        idFuture = new ArrayDeque<>();
        taskListPast = new ArrayDeque<>();
        taskListFuture = new ArrayDeque<>();
    }

    @Override
    public boolean addTask(Magic8Task task) throws IOException {
        assert task != null;

        task = new Magic8Task(task);
        task.setId(id++);

        updateTimeline();
        taskList.put(task.getId(), task);
        writeToFile();

        return true;
    }

    @Override
    public boolean removeTask(Magic8Task task) throws IOException {
        assert task != null;

        if (taskList.containsKey(task.getId())) {
            updateTimeline();
            taskList.remove(task.getId());
            writeToFile();

            return true;
        }

        return false;
    }

    @Override
    public boolean updateTask(Magic8Task task) throws IOException {
        assert task != null;

        if (taskList.containsKey(task.getId())) {
            updateTimeline();
            taskList.put(task.getId(), task);
            writeToFile();

            return true;
        }

        return false;
    }

    @Override
    public ArrayList<Magic8Task> removeTasks(ArrayList<Magic8Task> tasks)
            throws IOException {
        assert tasks != null;

        backup();

        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Magic8Task task : tasks) {
            assert task != null;

            if (!taskList.containsKey(task.getId())) {
                restore();

                return new ArrayList<>();
            }

            result.add(new Magic8Task(taskList.remove(task.getId())));
        }

        updateTimeline(true);
        writeToFile();

        return result;
    }

    @Override
    public ArrayList<Magic8Task> removeTasksWithTag(String tag)
            throws IOException {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            if (entry.getValue().getTags().contains(tag)) {
                result.add(new Magic8Task(taskList.remove(entry.getKey())));
            }
        }

        updateTimeline();
        writeToFile();

        return result;
    }

    @Override
    public ArrayList<Magic8Task> clearTasks() throws IOException {
        ArrayList<Magic8Task> result = new ArrayList<>();

        if (!taskList.isEmpty()) {
            for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
                result.add(new Magic8Task(entry.getValue()));
            }

            taskList.clear();

            updateTimeline();
            writeToFile();
        }

        return result;
    }

    @Override
    public boolean undo() throws IOException {
        if (idPast.isEmpty()) {
            return false;
        }

        idFuture.push(id);
        taskListFuture.push(taskList);

        id = idPast.pop();
        taskList = taskListPast.pop();

        writeToFile();

        return true;
    }

    @Override
    public boolean redo() throws IOException {
        if (idFuture.isEmpty()) {
            return false;
        }

        idPast.push(id);
        taskListPast.push(taskList);

        id = idFuture.pop();
        taskList = taskListFuture.pop();

        writeToFile();

        return true;
    }

    private TreeMap<Integer, Magic8Task> copyTaskList(
            TreeMap<Integer, Magic8Task> tl) {
        TreeMap<Integer, Magic8Task> copy = new TreeMap<>();

        for (Map.Entry<Integer, Magic8Task> entry : tl.entrySet()) {
            copy.put(entry.getKey(), new Magic8Task(entry.getValue()));
        }

        return copy;
    }

    private void backup() {
        tempId = id;
        tempTaskList = copyTaskList(taskList);
    }

    private void restore() {
        id = tempId;
        taskList = copyTaskList(tempTaskList);
    }

    private void updateTimeline(boolean useTemp) {
        if (useTemp) {
            idPast.push(tempId);
            taskListPast.push(tempTaskList);
        } else {
            idPast.push(id);
            taskListPast.push(copyTaskList(taskList));
        }

        idFuture.clear();
        taskListFuture.clear();
    }

    private void updateTimeline() {
        updateTimeline(false);
    }

    private void writeToFile() throws IOException {
        storage.writeToFile(id, taskList);
    }

    @Override
    public ArrayList<Magic8Task> getTimedTasks() {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getEndTime() != null) {
                result.add(new Magic8Task(task));
            }
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getTimedTasks(boolean isDone) {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getEndTime() != null) {
                if (isDone == task.isDone()) {
                    result.add(new Magic8Task(task));
                }
            }
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getUntimedTasks() {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getEndTime() == null) {
                result.add(new Magic8Task(task));
            }
        }

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getUntimedTasks(boolean isDone) {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getEndTime() == null) {
                if (isDone == task.isDone()) {
                    result.add(new Magic8Task(task));
                }
            }
        }

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getAllTasks() {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            result.add(new Magic8Task(task));
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getAllTasks(boolean isDone) {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (isDone == task.isDone()) {
                result.add(new Magic8Task(task));
            }
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getTasksWithWord(String word) {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getDesc().contains(word)) {
                result.add(new Magic8Task(task));
            }
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getTasksWithWord(String word, boolean isDone) {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getDesc().contains(word)) {
                if (isDone == task.isDone()) {
                    result.add(new Magic8Task(task));
                }
            }
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getTasksWithTag(String tag) {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getTags().contains(tag)) {
                result.add(new Magic8Task(task));
            }
        }

        Collections.sort(result);

        return result;
    }

    @Override
    public ArrayList<Magic8Task> getTasksWithTag(String tag, boolean isDone) {
        ArrayList<Magic8Task> result = new ArrayList<>();

        for (Map.Entry<Integer, Magic8Task> entry : taskList.entrySet()) {
            Magic8Task task = entry.getValue();

            if (task.getTags().contains(tag)) {
                if (isDone == task.isDone()) {
                    result.add(new Magic8Task(task));
                }
            }
        }

        Collections.sort(result);

        return result;
    }
}
