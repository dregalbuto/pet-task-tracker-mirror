package northeastern.is4300.pettasktracker.data;


import java.util.ArrayList;

/**
 *
 */
public class User {

    private long id;
    private String name;
    private int isAdmin;
    private ArrayList<Task> tasks;

    public User(String name, int isAdmin, ArrayList<Task> tasks) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.tasks = tasks;
    }

    public User(String name, int isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.tasks = new ArrayList<>();
    }

    public User() {
        this.id = 0;
        this.name = new String();
        this.isAdmin = 0;
        this.tasks = null;
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

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name.equals(user.name) && (isAdmin == user.isAdmin);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
