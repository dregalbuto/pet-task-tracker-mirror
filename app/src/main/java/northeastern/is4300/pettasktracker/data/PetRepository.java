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

public class PetRepository {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    static final String TABLE_PETS = "pets";
    private static final String KEY_ID = "id";
    static final String KEY_PET_NAME = "name";
    static final String KEY_PET_TYPE = "type";

    public PetRepository(Context c) {
        this.dbHelper = new DatabaseHelper(c);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(Pet pet) {
        ContentValues values = new ContentValues();
        values.put(KEY_PET_NAME, pet.getName());
        values.put(KEY_PET_TYPE, pet.getType());
        long id = database.insert(TABLE_PETS, null, values);
        pet.setId(id);
        return id;
    }

    public void delete(long petId) {
        database.delete(TABLE_PETS, KEY_ID
                + "= ?", new String[] { String.valueOf(petId) });
    }

    public void deleteAll() {
        database.delete(TABLE_PETS, null, null);
    }

    public Pet getPetByName(String name) {
        Pet p = null;
        String selectQuery = "SELECT * FROM " + TABLE_PETS
                + " WHERE " + KEY_PET_NAME
                + "= \"" + name + "\"";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            String type = cursor.getString(2);
            p = new Pet(name, type);
            p.setId(id);

        }

        return p;
    }

    public Pet getPetById(long petId) {
        Pet p = null;
        String selectQuery = "SELECT * FROM " + TABLE_PETS
                + " WHERE " + KEY_ID
                + "= " + petId + ";";
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

    public ArrayList<HashMap<String, String>> getPetList() {
        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_PET_NAME + "," +
                KEY_PET_TYPE +
                " FROM " + TABLE_PETS;

        ArrayList<HashMap<String, String>> petList = new ArrayList<>();

        Cursor cursor = database.rawQuery(selectQuery, null);

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

    private Pet cursorToPet(Cursor cursor) {
        Pet pet = new Pet();
        pet.setId(cursor.getLong(0));
        pet.setName(cursor.getString(1));
        pet.setType(cursor.getString(2));
        return pet;
    }

    public ArrayList<Pet> getPetListAsPets() {
        String selectQuery =  "SELECT  " +
                KEY_ID + "," +
                KEY_PET_NAME + "," +
                KEY_PET_TYPE +
                " FROM " + TABLE_PETS;

        ArrayList<Pet> petList = new ArrayList<>();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pet p = cursorToPet(cursor);
                petList.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return petList;
    }

}
