package northeastern.is4300.pettasktracker.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for a Task
 */
public class Task {
    private long id;
    private String taskType;
    private String taskRepeat;
    private Time taskTime;
    //private Timestamp taskCreated;
    private int isCompleted;
    private Pet pet;
    private User user;

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
        this.id = -1;
     //   this.taskCreated = new Timestamp(System.currentTimeMillis()/1000);
    }

    public Task(TASK_TYPE type, String repeat){
        this();
        this.taskType = type.name();
        this.taskRepeat = repeat;
    }

    public Pet getPet() {
        return this.pet;
    }

    public void setPet(Pet p) {
        this.pet = p;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Time getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Time taskTime) {
        this.taskTime = taskTime;
    }

    public void setTaskTime(String taskTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date parsedDate = dateFormat.parse(taskTime);
        this.taskTime = new Time(parsedDate.getTime());;
    }

    public String getTaskRepeat() {
        return taskRepeat;
    }

    public void setTaskRepeat(String taskRepeat) {
        this.taskRepeat = taskRepeat;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }

    /*
    public Timestamp getTaskCreated() {
        return taskCreated;
    }


    public void setTaskCreated(Timestamp taskCreated) {
        this.taskCreated = taskCreated;
    }

    */
    public static Task fromJson(JSONObject jsonObject) {
        Task task = new Task();
        try {
            task.id = jsonObject.has("id") ? jsonObject.getLong("id") : 0;
            task.taskType = jsonObject.has("taskType") ? jsonObject.getString("taskType") : "";
            task.taskRepeat = jsonObject.has("taskRepeat") ? jsonObject.getString("taskRepeat") : "";
            if (jsonObject.has("taskTime")) {
                try {
                    task.setTaskTime(jsonObject.getString("taskTime"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
           /*
            if (jsonObject.has("taskCreated")) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date parsedDate = dateFormat.parse(jsonObject.getString("taskCreated"));
                    task.taskCreated = new java.sql.Timestamp(parsedDate.getTime());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
            */
            task.isCompleted = jsonObject.has("isCompleted") ? jsonObject.getInt("isCompleted") : -1;
            task.user = jsonObject.has("user") ? User.fromJson(jsonObject.getJSONObject("user")) : null;
            task.pet = jsonObject.has("pet") ? Pet.fromJson(jsonObject.getJSONObject("pet")) : null;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return task;
    }

    public static JSONObject toJson(Task task) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", task.getId());
            object.put("taskType", task.getTaskType());
            object.put("taskRepeat", task.getTaskRepeat());
            object.put("taskTime", task.getTaskTime());
            //object.put("taskCreated", task.getTaskCreated());
            object.put("isCompleted", task.getIsCompleted());
            object.put("user", User.toJson(task.getUser()));
            object.put("pet", Pet.toJson(task.getPet()));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static ArrayList<Task> fromJson(JSONArray jsonArray) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject taskJson = null;
            try {
                taskJson = jsonArray.getJSONObject(i);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Task task = Task.fromJson(taskJson);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
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
