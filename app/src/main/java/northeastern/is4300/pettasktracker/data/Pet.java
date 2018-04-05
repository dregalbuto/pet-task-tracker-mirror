package northeastern.is4300.pettasktracker.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class for a Pet
 */
public class Pet {

    private long id;
    private String name;
    private String type;

    public Pet(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Pet() {
        this.id = -1;
    }

    public Pet(Pet p) {
        this.id = p.getId();
        this.name = p.getName();
        this.type = p.getType();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Pet fromJson(JSONObject jsonObject) {
        Pet pet = new Pet();
        try {
            pet.id = jsonObject.has("id") ? jsonObject.getLong("id") : 0;
            pet.name = jsonObject.has("name") ? jsonObject.getString("name") : "";
            pet.type = jsonObject.has("type") ? jsonObject.getString("type") : "";
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return pet;
    }

    public static ArrayList<Pet> fromJson(JSONArray jsonArray) {
        ArrayList<Pet> pets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject petJson = null;
            try {
                petJson = jsonArray.getJSONObject(i);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Pet pet = Pet.fromJson(petJson);
            if (pet != null) {
                pets.add(pet);
            }
        }
        return pets;
    }

    public static JSONObject toJson(Pet pet) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", pet.getId());
            object.put("name", pet.getName());
            object.put("type", pet.getType());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (!name.equals(pet.name)) return false;
        return (!type.equals(pet.type));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}

