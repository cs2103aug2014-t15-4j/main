package fantasticfour.magiceight;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;

public class Magic8Task implements Magic8TaskInterface {
    private static final String MSG_NEGATIVE_ID = "id cannot be negative";
    private static final String MSG_ZERO_ID = "id cannot be zero";
    private static final String MSG_NULL_DESCRIPTION = "description cannot be null";
    private static final String MSG_EMPTY_DESCRIPTION = "description cannot be empty";
    private static final String MSG_NULL_TAG = "tag cannot be null";
    private static final String MSG_EMPTY_TAG = "tag cannot be empty";
    private static final String MSG_NON_ALPHANUMERIC_TAG = "tag must be alphanumeric";

    private int id;
    private String desc;
    private Date deadline;
    private HashSet<String> tags;

    public Magic8Task(int id, String desc, Date deadline, HashSet<String> tags)
            throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(MSG_NEGATIVE_ID);
        }

        this.id = id;
        setDesc(desc);
        setDeadline(deadline);
        setTags(tags);
    }

    public Magic8Task(String desc, Date deadline, HashSet<String> tags)
            throws IllegalArgumentException {
        this(0, desc, deadline, tags);
    }

    public Magic8Task(Magic8Task task) {
        this(task.getId(), task.getDesc(), new Date(task.getDeadline()
                .getTime()), new HashSet<String>(task.getTags()));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException(MSG_NEGATIVE_ID);
        }
        if (id == 0) {
            throw new IllegalArgumentException(MSG_ZERO_ID);
        }

        this.id = id;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) throws IllegalArgumentException {
        if (desc == null) {
            throw new IllegalArgumentException(MSG_NULL_DESCRIPTION);
        }
        if (desc.isEmpty()) {
            throw new IllegalArgumentException(MSG_EMPTY_DESCRIPTION);
        }

        this.desc = desc;
    }

    @Override
    public Date getDeadline() {
        if (deadline == null) {
            return deadline;
        }

        return new Date(deadline.getTime());
    }

    @Override
    public void setDeadline(Date deadline) {
        if (deadline == null) {
            this.deadline = deadline;
        } else {
            this.deadline = new Date(deadline.getTime());
        }
    }

    @Override
    public HashSet<String> getTags() {
        return new HashSet<String>(tags);
    }

    @Override
    public void setTags(HashSet<String> tags) throws IllegalArgumentException {
        if (tags == null) {
            this.tags = new HashSet<String>();
        } else {
            for (String tag : tags) {
                validateTag(tag);
            }

            this.tags = new HashSet<String>(tags);
        }
    }

    @Override
    public void addTag(String tag) throws IllegalArgumentException {
        validateTag(tag);

        tags.add(tag);
    }

    @Override
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    @Override
    public String[] toStringArray() {
        String[] stringArray = new String[4];
        stringArray[0] = Integer.toString(id);
        stringArray[1] = desc;

        if (deadline == null) {
            stringArray[2] = "@null";
        } else {
            stringArray[2] = deadline.toString();
        }

        if (tags == null) {
            stringArray[3] = "@null";
        } else if (tags.size() == 0) {
            stringArray[3] = "@empty";
        } else {
            stringArray[3] = "";
            for (String tag : tags) {
                stringArray[3] += " " + tag;
            }
        }

        return stringArray;
    }

    public static Magic8Task stringArrayToMagic8Task(String[] stringArray)
            throws IllegalArgumentException, ParseException {
        if (stringArray.length >= 4) {
            int id = Integer.parseInt(stringArray[0]);
            String desc = stringArray[1];
            Date deadline;
            HashSet<String> tags;

            if (stringArray[2].equals("@null")) {
                deadline = null;
            } else {
                deadline = DateFormat.getDateInstance().parse(stringArray[2]);
            }

            if (stringArray[3].equals("@null")) {
                tags = null;
            } else if (stringArray[3].equals("@empty")) {
                tags = new HashSet<String>();
            } else {
                tags = new HashSet<String>();
                String[] tagStrings = stringArray[3].split("\\s+");
                for (String tagString : tagStrings) {
                    tags.add(tagString);
                }
            }

            Magic8Task task = new Magic8Task(id, desc, deadline, tags);
            return task;
        } else {
            throw new IllegalArgumentException("Error insufficient arguments");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Magic8Task) {
            Magic8Task magic8Task = (Magic8Task) obj;
            boolean result = true;
            result = result && magic8Task.getId() == id;
            result = result && magic8Task.getDesc().equals(desc);
            result = result
                    && (magic8Task.getDeadline() == null && deadline == null || magic8Task
                            .getDeadline().equals(deadline));
            result = result && magic8Task.getTags().size() == tags.size();
            for (String tag : tags) {
                if (!magic8Task.getTags().contains(tag)) {
                    result = false;
                    break;
                }
            }
            return result;
        } else {
            return false;
        }

    }

    private static void validateTag(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException(MSG_NULL_TAG);
        }
        if (tag.isEmpty()) {
            throw new IllegalArgumentException(MSG_EMPTY_TAG);
        }
        if (!tag.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException(MSG_NON_ALPHANUMERIC_TAG);
        }
    }
}
