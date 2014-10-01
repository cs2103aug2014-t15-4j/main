package fantasticfour.magiceight;

public interface Magic8TagIndexInterface {
	public int getId(String tag);

	public String getTag(int tagId);

	public Magic8Task addMagic8Task(Magic8Task task);

	public void removeMagic8Task(Magic8Task task);
}
