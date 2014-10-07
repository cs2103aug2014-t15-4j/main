package fantasticfour.magiceight;

import java.io.IOException;
import java.util.TreeMap;

interface Magic8TaskListInterface {
    /**
     * Updates the task id,
     * adds the task to the task list,
     * and returns it.
     * 
     * @param task  Task to be added.
     * @return      Task with updated id.
     * @throws IOException 
     */
    public Magic8Task addTask(Magic8Task task) throws IOException;

    /**
     * Removes the task with the id of the specified task from the task list,
     * and returns it
     * 
     * @precondition    Specified task was not changed after being taken from task list.
     * @param task      Task to be removed.
     * @return          Task with the same id as the specified task.
     * @throws IOException 
     */
    public Magic8Task removeTask(Magic8Task task) throws IOException;

    /**
     * Updates the task with the id of the specified task in the task list.
     * 
     * @param task  Task to replace the stored task with.
     * @return      true    if task was updated.
     *              false   otherwise.
     * @throws IOException 
     */
    public boolean updateTask(Magic8Task task) throws IOException;
    
    /**
     * Removes all tasks from the task list.
     * 
     * @return  true    if tasks were removed.
     *          false   if task list was empty.
     * @throws IOException 
     */
    public boolean clearTasks() throws IOException;
    
    /**
     * Returns all tasks in the task list ordered by the task id.
     * 
     * @return	all tasks in the task list ordered by the task id.
     */
    public TreeMap<Integer, Magic8Task> getAllTasks();
    
    /**
     * Returns all tasks in the task list with the specified tag, ordered by the task id.
     * 
     * @param tag	Tag to retrieve tasks with.
     * @return		all tasks in the task list with the specified tag, ordered by the task id.
     */
    public TreeMap<Integer, Magic8Task> getTasksWithTag(String tag);
}
