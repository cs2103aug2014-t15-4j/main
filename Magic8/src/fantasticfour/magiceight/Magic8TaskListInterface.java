package fantasticfour.magiceight;

import java.io.IOException;
import java.util.ArrayList;

public interface Magic8TaskListInterface {
    /**
     * Assigns a task id to the task and adds it to the task list.
     *
     * @param task
     *            Task whose presence in the task list is to be ensured
     * @return true if the task list changed as a result of the call
     * @throws IOException
     */
    public boolean addTask(Magic8Task task) throws IOException;

    /**
     * Removes the task with the specified task's id from the task list.
     *
     * @param task
     *            Task with task id to be removed from the task list, if present
     * @return true if the task list changed as a result of the call
     * @throws IOException
     */
    public boolean removeTask(Magic8Task task) throws IOException;

    /**
     * Replaces the task with the specified task's id in the task list with the
     * specified task.
     *
     * @param task
     *            Task to replace the task with the same id
     * @return true if the task list changed as a result of the call
     * @throws IOException
     */
    public boolean updateTask(Magic8Task task) throws IOException;

    /**
     * Removes all tasks with the specified tasks' ids from the task list. If a
     * specified task id cannot be found in the task list, none of the tasks are
     * removed.
     *
     * @param tasks
     *            Tasks with the task ids to be removed from the task list
     * @return an ArrayList containing the tasks that were removed from the task
     *         list
     * @throws IOException
     */
    public ArrayList<Magic8Task> removeTasks(ArrayList<Magic8Task> tasks)
            throws IOException;

    /**
     * Removes all tasks with the specified tag from the task list.
     *
     * @param tag
     *            Tag to remove tasks with
     * @return an ArrayList containing the tasks that were removed from the task
     *         list
     * @throws IOException
     */
    public ArrayList<Magic8Task> removeTasksWithTag(String tag)
            throws IOException;

    /**
     * Removes all tasks from the task list.
     *
     * @return an ArrayList containing the tasks that were removed
     * @throws IOException
     */
    public ArrayList<Magic8Task> clearTasks() throws IOException;

    /**
     * Undoes the last operation.
     *
     * @return true if the task list changed as a result of the call
     * @throws IOException
     */
    public boolean undo() throws IOException;

    /**
     * Redoes the last operation.
     *
     * @return true if the task list changed as a result of the call
     * @throws IOException
     */
    public boolean redo() throws IOException;

    /**
     * Returns all tasks in the task list with end times.
     *
     * @return an ArrayList containing all the tasks in the task list with end
     *         times
     */
    public ArrayList<Magic8Task> getTimedTasks();

    /**
     * Returns either done or undone tasks in the task list with end times.
     *
     * @param isDone
     *            indicates if done or undone tasks are to be returned
     * @return an ArrayList containing either done or undone tasks in the task
     *         list with end times
     */
    public ArrayList<Magic8Task> getTimedTasks(boolean isDone);

    /**
     * Returns all tasks in the task list without end times.
     *
     * @return an ArrayList containing all the tasks in the task list without
     *         end times
     */
    public ArrayList<Magic8Task> getUntimedTasks();

    /**
     * Returns either done or undone tasks in the task list without end times.
     *
     * @param isDone
     *            indicates if done or undone tasks are to be returned
     * @return an ArrayList containing either done or undone tasks in the task
     *         list without end times
     */
    public ArrayList<Magic8Task> getUntimedTasks(boolean isDone);

    /**
     * Returns all tasks in the task list.
     *
     * @return an ArrayList containing all the tasks in the task list
     */
    public ArrayList<Magic8Task> getAllTasks();

    /**
     * Returns either done or undone tasks in the task list.
     *
     * @param isDone
     *            indicates if only done or undone tasks are to be returned
     * @return an ArrayList containing either done or undone tasks in the task
     *         list
     */
    public ArrayList<Magic8Task> getAllTasks(boolean isDone);

    /**
     * Returns all tasks in the task list.
     *
     * @param word
     *            Word to retrieve tasks with
     * @return an ArrayList containing all the tasks in the task list with the
     *         specified word
     */
    public ArrayList<Magic8Task> getTasksWithWord(String word);

    /**
     * Returns either done or undone tasks in the task list.
     *
     * @param word
     *            Word to retrieve tasks with
     * @param isDone
     *            indicates if only done or undone tasks are to be returned
     * @return an ArrayList containing either done or undone tasks in the task
     *         list with the specified word
     */
    public ArrayList<Magic8Task> getTasksWithWord(String word, boolean isDone);

    /**
     * Returns all tasks in the task list.
     *
     * @param tag
     *            Tag to retrieve tasks with
     * @return an ArrayList containing all the tasks in the task list with the
     *         specified tag
     */
    public ArrayList<Magic8Task> getTasksWithTag(String tag);

    /**
     * Returns either done or undone tasks in the task list.
     *
     * @param word
     *            Word or tag to retrieve tasks with
     * @param isDone
     *            indicates if only done or undone tasks are to be returned
     * @return an ArrayList containing either done or undone tasks in the task
     *         list with the specified tag
     */
    public ArrayList<Magic8Task> getTasksWithTag(String tag, boolean isDone);
}
