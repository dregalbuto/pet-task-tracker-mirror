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

public class PetRepository {

    private DatabaseHelper dbHelper;

    static final String TABLE_PETS = "pets";
    private static final String KEY_ID = "id";
    static final String KEY_PET_NAME = "name";
    static final String KEY_PET_TYPE = "type";

    public PetRepository(Context c) {
        this.dbHelper = new DatabaseHelper(c);
    }

    public int insert(Pet pet) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PET_NAME, pet.getName());
        values.put(KEY_PET_TYPE, pet.getType());
        return (int) database.insert(TABLE_PETS, null, values);
    }

    public void delete(int petId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_PETS, KEY_ID
                + "= ?", new String[] { String.valueOf(petId) });
    }

    public ArrayList<HashMap<String, String>> getPetList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_PET_NAME + "," +
                KEY_PET_TYPE +
                " FROM " + TABLE_PETS;

        ArrayList<HashMap<String, String>> petList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> pet = new HashMap<String, String>();
                pet.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
                pet.put("name", cursor.getString(cursor.getColumnIndex(KEY_PET_NAME)));
                pet.put("type", cursor.getString(cursor.getColumnIndex(KEY_PET_TYPE)));
                petList.add(pet);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return petList;
    }

}
