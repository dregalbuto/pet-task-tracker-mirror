package northeastern.is4300.pettasktracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "petTaskTracker";

    private static final String KEY_ID = "_id";

    private static final String CREATE_TABLE_PETS = "CREATE TABLE "
            + PetRepository.TABLE_PETS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PetRepository.KEY_PET_NAME + " TEXT, "
            + PetRepository.KEY_PET_TYPE + " TEXT)";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + UserRepository.TABLE_USERS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserRepository.KEY_USER_NAME + " TEXT, "
            + UserRepository.KEY_USER_ISADMIN + " INTEGER)";

    private static final String CREATE_TABLE_TASKS = "CREATE TABLE "
            + TaskRepository.TABLE_TASKS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TaskRepository.KEY_TASK_TYPE + " TEXT, "
            + TaskRepository.KEY_TASK_TIME + " TEXT, "
            + TaskRepository.KEY_TASK_REPEAT + " TEXT, "
            + TaskRepository.KEY_PET_ID + " INTEGER, "
            + TaskRepository.KEY_USER_ID + " INTEGER)";

    private static final void loadPets(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(PetRepository.KEY_PET_NAME, "Cookie");
        values.put(PetRepository.KEY_PET_TYPE, "Cat");
        db.insert(PetRepository.TABLE_PETS, null, values);

        values = new ContentValues();
        values.put(PetRepository.KEY_PET_NAME, "Rudy");
        values.put(PetRepository.KEY_PET_TYPE, "Dog");
        db.insert(PetRepository.TABLE_PETS, null, values);
    }

    private static final void loadUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(UserRepository.KEY_USER_NAME, "Martha");
        values.put(UserRepository.KEY_USER_ISADMIN, 1);
        db.insert(UserRepository.TABLE_USERS, null, values);

        values = new ContentValues();
        values.put(UserRepository.KEY_USER_NAME, "Stuart");
        values.put(UserRepository.KEY_USER_ISADMIN, 1);
        db.insert(UserRepository.TABLE_USERS, null, values);

        values = new ContentValues();
        values.put(UserRepository.KEY_USER_NAME, "Tim");
        values.put(UserRepository.KEY_USER_ISADMIN, 0);
        db.insert(UserRepository.TABLE_USERS, null, values);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PETS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TASKS);

        loadPets(db);
        loadUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            Log.w(LOG,
                    "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TaskRepository.TABLE_TASKS);
            onCreate(db);
        }
    }
}
