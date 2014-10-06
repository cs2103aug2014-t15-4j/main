package fantasticfour.magiceight;

interface Magic8TaskListInterface {
    /**
     * Updates the task id,
     * adds the task to the task list,
     * and returns it.
     * 
     * @param task  Task to be added.
     * @return      Task with updated id.
     */
    public Magic8Task addTask(Magic8Task task);

    /**
     * Removes the task with the id of the specified task from the task list,
     * and returns it
     * 
     * @precondition    Specified task was not changed after being taken from task list.
     * @param task      Task to be removed.
     * @return          Task with the same id as the specified task.
     */
    public Magic8Task removeTask(Magic8Task task);

    /**
     * Updates the task with the id of the specified task in the task list.
     * 
     * @param task  Task to replace the stored task with.
     * @return      true    if task was updated.
     *              false   otherwise.
     */
    public boolean updateTask(Magic8Task task);
    
    /**
     * Removes all tasks from the task list.
     * 
     * @return  true    if tasks were removed.
     *          false   if task list was empty.
     */
    public boolean clearTasks();
}
