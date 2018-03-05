package northeastern.is4300.pettasktracker.object;

import android.media.Image;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */

public class Pet {
    private String name;
    private String type;
    private Image avatar;
    private ArrayList<Task> tasks;

    public Pet(String name, String type) {
        this.name = name;
        this.type = type;
        this.avatar = null;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
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

