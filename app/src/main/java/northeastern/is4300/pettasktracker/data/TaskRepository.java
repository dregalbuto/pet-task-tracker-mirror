package northeastern.is4300.pettasktracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class TaskRepository {
    private DatabaseHelper dbHelper;

    static final String TABLE_TASKS = "tasks";
    private static final String KEY_ID = "id";
    static final String KEY_TASK_TYPE = "type";
    static final String KEY_TASK_TIME = "time";
    static final String KEY_TASK_REPEAT = "repeat";

    public TaskRepository(Context c) {
        this.dbHelper = new DatabaseHelper(c);
    }

    public int insert(Task task) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TYPE, task.getType());
        values.put(KEY_TASK_TIME, task.getTaskTime());
        values.put(KEY_TASK_REPEAT, task.getRepeat());
        return (int) database.insert(TABLE_TASKS, null, values);
    }

    public void delete(int taskId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_TASKS, KEY_ID
                + "= ?", new String[] { String.valueOf(taskId) });
    }

    public ArrayList<HashMap<String, String>> getTaskList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_TASK_TYPE + "," +
                KEY_TASK_TIME + "," +
                KEY_TASK_REPEAT +
                " FROM " + TABLE_TASKS;

        ArrayList<HashMap<String, String>> taskList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> task = new HashMap<String, String>();
                task.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
                task.put("type", cursor.getString(cursor.getColumnIndex(KEY_TASK_TYPE)));
                task.put("time", cursor.getString(cursor.getColumnIndex(KEY_TASK_TIME)));
                task.put("repeat", cursor.getString(cursor.getColumnIndex(KEY_TASK_REPEAT)));
                taskList.add(task);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return taskList;
    }
}
