package northeastern.is4300.pettasktracker.object;

import java.util.Date;

/**
 */

public class Task {
    private final Pet pet;
    private Date taskTime;

    public Task(Pet pet, Date taskTime){
        this.pet = pet;
        this.taskTime = taskTime;
    }

    public Pet getPet() {
        return this.pet;
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
