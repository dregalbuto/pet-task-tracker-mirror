package northeastern.is4300.pettasktracker.object;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class Data extends Application {

    private HashMap<Integer, Pet> pets;
    private HashMap<Integer, User> users;
    private HashMap<Integer, Task> tasks;

    private static Data singleton;

    public Data() {
        singleton.pets = new HashMap<>();
        singleton.users = new HashMap<>();
        singleton.tasks = new HashMap<>();
    }

    public static Data getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }

    public void addPet(Pet p) {
        singleton.pets.put(p.hashCode(), p);
    }

    public void addPet(String name, String type) {
        Pet p = new Pet(name, type);
        singleton.pets.put(p.hashCode(), p);
    }

    public void addUser(User u) {

        singleton.users.put(u.hashCode(), u);
    }

    public void addTask(Task t) {

        singleton.tasks.put(t.hashCode(), t);
    }

    public boolean hasPet(String name) {
        return singleton.pets.containsKey(name.hashCode());
    }

    public Pet getPet(String name) {
        return singleton.pets.get(name.hashCode());
    }

    public boolean hasUser(String name) {
        return singleton.users.containsKey(name.hashCode());
    }

    public User getUser(String name) {
        return singleton.users.get(name.hashCode());
    }

    public boolean hasTask(String name) {
        return singleton.tasks.containsKey(name.hashCode());
    }

    public Task getTask(String name) {
        return singleton.tasks.get(name.hashCode());
    }

    /** getters **/
    public ArrayList<Pet> getPets() {
        ArrayList<Pet> result = new ArrayList<>();
        for (int i : singleton.pets.keySet()) {
            result.add(singleton.pets.get(i));
        }
        return result;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> result = new ArrayList<>();
        for (int i : singleton.users.keySet()) {
            result.add(singleton.users.get(i));
        }
        return result;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> result = new ArrayList<>();
        for (int i : singleton.tasks.keySet()) {
            result.add(singleton.tasks.get(i));
        }
        return result;
    }

}
