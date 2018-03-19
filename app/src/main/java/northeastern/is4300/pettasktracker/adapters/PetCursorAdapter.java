package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ResourceCursorAdapter;

import northeastern.is4300.pettasktracker.R;

/**
 *
 */

public class PetCursorAdapter extends ResourceCursorAdapter {

    public PetCursorAdapter(Context context, int layout, Cursor cursor) {

        super(context, layout, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        Button pet_body = (Button) view.findViewById(R.id.pet_list_view_button);
        // Extract properties from cursor
        String name = cursor.getString(1);
        // Populate fields with extracted properties
        pet_body.setText(name);

    }
}
