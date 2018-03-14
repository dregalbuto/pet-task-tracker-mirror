package northeastern.is4300.pettasktracker.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 *
 */

@Entity
public class User {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name="display_name")
    private String name;
    @ColumnInfo(name="is_admin")
    private boolean isAdmin;
    private ArrayList<Task> tasks;

    public User(String name, boolean isAdmin, ArrayList<Task> tasks) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.tasks = tasks;
    }

    public User(String name, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name.equals(user.name) && (isAdmin && user.isAdmin);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
