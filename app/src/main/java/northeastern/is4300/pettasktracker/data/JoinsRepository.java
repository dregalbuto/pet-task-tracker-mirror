package northeastern.is4300.pettasktracker.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 */
public class JoinsRepository {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public JoinsRepository(Context c) {
        this.dbHelper = new DatabaseHelper(c);
    }

    public void open() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Pet getPetByTask(Task task) {
        long taskId = task.getId();
        Pet p = new Pet("NOT FOUND", "NOT FOUND");

        String selectQuery = "SELECT "
                + PetRepository.TABLE_PETS + "." + PetRepository.KEY_ID
                + ", "
                + PetRepository.TABLE_PETS + "." + PetRepository.KEY_PET_NAME
                + ", "
                + PetRepository.TABLE_PETS + "." + PetRepository.KEY_PET_TYPE
                + " FROM " + PetRepository.TABLE_PETS
                + " JOIN " + TaskRepository.TABLE_TASKS
                + " ON " + TaskRepository.TABLE_TASKS + "." + TaskRepository.KEY_PET_ID
                + " = " + PetRepository.TABLE_PETS + "." + PetRepository.KEY_ID
                + " WHERE " + TaskRepository.TABLE_TASKS + "." + TaskRepository.KEY_ID
                + " = " + taskId + ";";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            p = new Pet(name, type);
            p.setId(id);
        }

        return p;
    }

}
