package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import northeastern.is4300.pettasktracker.R;

/**
 *
 */
public class UserCursorAdapter extends CursorAdapter {

    public UserCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView user_name = (TextView) view.findViewById(R.id.userName);
        // Extract properties from cursor
        String name = cursor.getString(1);
        // Populate fields with extracted properties
        user_name.setText(name);
    }
}
