package northeastern.is4300.pettasktracker.object;

/**
 *
 */

public class User {
    private String name;
    private Boolean isAdmin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        return isAdmin.equals(user.isAdmin);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
