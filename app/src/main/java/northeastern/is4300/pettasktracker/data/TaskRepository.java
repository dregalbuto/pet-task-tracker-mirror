package northeastern.is4300.pettasktracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class TaskRepository {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    static final String KEY_TASK_TYPE = "type";
    static final String KEY_TASK_TIME = "time";
    static final String KEY_TASK_REPEAT = "repeat";
    static final String KEY_PET_ID = "pet_id";
    static final String KEY_USER_ID = "user_id";

    public TaskRepository(Context c) {
        this.dbHelper = new DatabaseHelper(c);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public int insert(Task task) {
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TYPE, task.getType());
        values.put(KEY_TASK_TIME, task.getTaskTime());
        values.put(KEY_TASK_REPEAT, task.getRepeat());
        values.put(KEY_PET_ID, task.getPetId());
        values.put(KEY_USER_ID, task.getUserId());
        return (int) database.insert(TABLE_TASKS, null, values);
    }

    public void delete(long taskId) {
        database.delete(TABLE_TASKS, KEY_ID
                + "= ?", new String[] { String.valueOf(taskId) });
    }

    public void deleteAll() {
        database.delete(TABLE_TASKS, null, null);
    }

    public ArrayList<HashMap<String, String>> getTaskList() {
        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_TASK_TYPE + "," +
                KEY_TASK_TIME + "," +
                KEY_TASK_REPEAT + "," +
                KEY_PET_ID + "," +
                KEY_USER_ID +
                " FROM " + TABLE_TASKS;

        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> task = new HashMap<String, String>();
                task.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
                task.put("type", cursor.getString(cursor.getColumnIndex(KEY_TASK_TYPE)));
                task.put("time", cursor.getString(cursor.getColumnIndex(KEY_TASK_TIME)));
                task.put("repeat", cursor.getString(cursor.getColumnIndex(KEY_TASK_REPEAT)));
                task.put("petId", cursor.getString(cursor.getColumnIndex(KEY_PET_ID)));
                task.put("userId", cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                taskList.add(task);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return taskList;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setType(cursor.getString(1));
        task.setTaskTime(cursor.getString(2));
        task.setRepeat(cursor.getString(3));
        task.setPetId(cursor.getLong(4));
        task.setUserId(cursor.getLong(5));
        return task;
    }

    public ArrayList<Task> getTaskListAsTasks() {
        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_TASK_TYPE + "," +
                KEY_TASK_TIME + "," +
                KEY_TASK_REPEAT + "," +
                KEY_PET_ID + "," +
                KEY_USER_ID +
                " FROM " + TABLE_TASKS;

        ArrayList<Task> taskList = new ArrayList<>();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = cursorToTask(cursor);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return taskList;
    }
}
