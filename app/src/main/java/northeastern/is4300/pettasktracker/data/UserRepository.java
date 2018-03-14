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
public class UserRepository {
    private DatabaseHelper dbHelper;

    static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    static final String KEY_USER_NAME = "name";
    static final String KEY_USER_ISADMIN = "is_admin";

    public UserRepository(Context c) {
        this.dbHelper = new DatabaseHelper(c);
    }

    public int insert(User user) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_ISADMIN, user.getIsAdmin());
        return (int) database.insert(TABLE_USERS, null, values);
    }

    public void delete(int userId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_USERS, KEY_ID
                + "= ?", new String[] { String.valueOf(userId) });
    }

    public ArrayList<HashMap<String, String>> getUserList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_USER_NAME + "," +
                KEY_USER_ISADMIN +
                " FROM " + TABLE_USERS;

        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
                user.put("name", cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                user.put("isAdmin", cursor.getString(cursor.getColumnIndex(KEY_USER_ISADMIN)));
                userList.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }
}
