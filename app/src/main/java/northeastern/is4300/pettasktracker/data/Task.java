package northeastern.is4300.pettasktracker.data;

/**
 * Class for a Task
 */

public class Task {
    private long id;
    private String type;
    private String taskTime;
    private String repeat;
    private long petId;
    private long userId;

    public enum TASK_TYPE  {
        Food,
        Walk,
        Medication
    }

    static public TASK_TYPE getTypeEnum(String taskType) {
        if (taskType.equals("Food")) {
            return TASK_TYPE.Food;
        }
        else if (taskType.equals("Walk")) {
            return TASK_TYPE.Walk;
        }
        else {
            return TASK_TYPE.Medication;
        }
    }

    public Task() {
        this.id = 0;
        this.type = new String();
        this.taskTime = new String();
        this.repeat = new String();
        this.petId = -1;
        this.userId = -1;
    }

    public Task(TASK_TYPE type, String taskTime, String repeat){
        this.taskTime = taskTime;
        this.type = type.name();
        this.repeat = repeat;
    }

    public Task(TASK_TYPE type, String taskTime, String repeat, long petId, long userId){
        this.taskTime = taskTime;
        this.type = type.name();
        this.repeat = repeat;
        this.petId = petId;
        this.userId = userId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public long getPetId() {
        return petId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return taskTime != null ? taskTime.equals(task.taskTime) : task.taskTime == null;
    }

    @Override
    public int hashCode() {
        return taskTime != null ? taskTime.hashCode() : 0;
    }
}
