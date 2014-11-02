package fantasticfour.magiceight;

import java.io.IOException;
import java.util.ArrayList;

interface Magic8TaskListInterface {
    /**
     * Assigns a task id, adds the task to the task list and returns it.
     *
     * @param task
     *            Task to be added.
     * @return Task with assigned task id.
     * @throws IOException
     */
    public Magic8Task addTask(Magic8Task task) throws IOException;

    /**
     * Replaces the stored task with the same task id with the specified task
     *
     * @param task
     *            Task to replace the stored task with.
     * @return true if stored task was updated. false otherwise.
     * @throws IOException
     */
    public boolean updateTask(Magic8Task task) throws IOException;

    /**
     * Removes the task with the same task id of the specified task from the
     * task list, and returns it.
     *
     * @param task
     *            Task with task id to be removed.
     * @return Task with the same id as the specified task.
     * @throws IOException
     */
    public Magic8Task removeTask(Magic8Task task) throws IOException;

    /**
     * Removes all tasks with the specified tag from the task list.
     *
     * @param tag
     *            Tag to remove tasks with.
     * @return true if tasks were removed. false if there are no tasks with the
     *         specified tag.
     * @throws IOException
     */
    public boolean removeTasksWithTag(String tag) throws IOException;

    /**
     * Removes all tasks from the task list.
     *
     * @return an ArrayList of Magic8Tasks that were removed from the task list.
     * @throws IOException
     */
    public ArrayList<Magic8Task> clearTasks() throws IOException;

    /**
     * Undoes the last operation.
     *
     * @return true if undo succeeds. false if there was no operation to undo.
     * @throws IOException
     */
    public boolean undo() throws IOException;

    /**
     * Redoes the last operation.
     *
     * @return true if redo succeeds. false if there was no operation to redo.
     * @throws IOException
     */
    public boolean redo() throws IOException;

    /**
     * Returns all tasks in the task list ordered by the task id.
     *
     * @return all tasks in the task list ordered by the task id.
     */
    public ArrayList<Magic8Task> getAllTasks();

    /**
     * Returns all tasks in the task list with the specified word or tag,
     * ordered by the task id.
     *
     * @param word
     *            Word or tag to retrieve tasks with.
     * @param isTag
     *            true if word is a tag.
     * @return all tasks in the task list with the specified tag, ordered task
     *         id.
     */
    public ArrayList<Magic8Task> getTasksWithWord(String word, boolean isTag);
}
