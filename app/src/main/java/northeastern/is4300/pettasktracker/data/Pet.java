package northeastern.is4300.pettasktracker.data;

import android.media.Image;

import java.util.ArrayList;

/**
 * Class for a Pet
 */
public class Pet {

    private long id;
    private String name;
    private String type;
    private Image avatar;
    private ArrayList<Task> tasks;


    public Pet(String name, String type) {
        this.name = name;
        this.type = type;
        this.avatar = null;
    }

    public Pet() {
        this.id = 0;
        this.name = new String();
        this.type = new String();
        this.avatar = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (!name.equals(pet.name)) return false;
        return (!type.equals(pet.type));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}

