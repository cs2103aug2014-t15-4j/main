package fantasticfour.magiceight;

public interface Magic8Interface {
    Magic8Task addTask(Magic8Task task);

    Magic8Task removeTask(int id);

    Magic8Task updateTask(Magic8Task task);

    void displayAll();
}
