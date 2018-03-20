package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import northeastern.is4300.pettasktracker.R;

/**
 *
 */
public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor cursor) {

        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView pet_name = (TextView) view.findViewById(R.id.petName);
        // Extract properties from cursor
        String name = cursor.getString(1);
        // Populate fields with extracted properties
        pet_name.setText(name);

        ImageView pet_icon = (ImageView) view.findViewById(R.id.petIcon);
        String petType = cursor.getString(2);
        if (petType.equals("Dog")) {
            pet_icon.setImageDrawable(view.getResources().getDrawable(R.drawable.dog_icon_100));
        }

    }
}
