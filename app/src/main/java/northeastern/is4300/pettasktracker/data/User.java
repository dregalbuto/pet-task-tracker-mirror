package northeastern.is4300.pettasktracker.data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 */
public class User {

    private long id;
    private String name;
    private String email;
    private String password;
    private int isAdmin;

    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isAdmin = user.getIsAdmin();
    }

    public User(String name, int isAdmin, ArrayList<Task> tasks) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public User(String name, int isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public User() {
        this.id = -1;
        this.isAdmin = 0;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();
        try {
            user.id = jsonObject.has("id") ? jsonObject.getLong("id") : 0;
            user.name = jsonObject.has("name") ? jsonObject.getString("name") : "";
            user.email = jsonObject.has("email") ? jsonObject.getString("email") : "";
            user.password = jsonObject.has("password") ? jsonObject.getString("password") : "";
            user.isAdmin = jsonObject.has("isAdmin") ? jsonObject.getInt("isAdmin") : -1;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<User> fromJson(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = null;
            try {
                userJson = jsonArray.getJSONObject(i);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            User user = User.fromJson(userJson);
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public static JSONObject toJson(User user) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", user.getId());
            object.put("name", user.getName());
            object.put("email", user.getEmail());
            object.put("password", user.getPassword());
            object.put("isAdmin", user.getIsAdmin());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
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
