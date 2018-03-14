package northeastern.is4300.pettasktracker.data;

/**
 */

public class Task {
    private String type;
    private String taskTime;
    private String repeat;
    private Pet pet;
    private User user;

    public Task(String type, Pet pet, User user, String taskTime, String repeat){
        this.pet = pet;
        this.taskTime = taskTime;
        this.user = user;
        this.type = type;
        this.repeat = repeat;
    }

    public String getType() {
        return type;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public String getRepeat() {
        return repeat;
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
