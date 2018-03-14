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
public class UserRepository {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    static final String KEY_USER_NAME = "name";
    static final String KEY_USER_ISADMIN = "is_admin";

    public UserRepository(Context c) {
        this.dbHelper = new DatabaseHelper(c);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_ISADMIN, user.getIsAdmin());
        long id = database.insert(TABLE_USERS, null, values);
        user.setId(id);
        return id;
    }

    public void delete(long userId) {
        database.delete(TABLE_USERS, KEY_ID
                + "= ?", new String[] { String.valueOf(userId) });
    }

    public void deleteAll() {
        database.delete(TABLE_USERS, null, null);
    }

    public ArrayList<HashMap<String, String>> getUserList() {
        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_USER_NAME + "," +
                KEY_USER_ISADMIN +
                " FROM " + TABLE_USERS;

        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        Cursor cursor = database.rawQuery(selectQuery, null);

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

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setIsAdmin(cursor.getInt(2));
        return user;
    }

    public ArrayList<User> getUserListAsUsers() {
        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_USER_NAME + "," +
                KEY_USER_ISADMIN +
                " FROM " + TABLE_USERS;

        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User u = cursorToUser(cursor);
                userList.add(u);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }
}
