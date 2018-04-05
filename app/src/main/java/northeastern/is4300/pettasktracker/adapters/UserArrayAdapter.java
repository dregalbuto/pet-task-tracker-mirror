package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import northeastern.is4300.pettasktracker.R;
import northeastern.is4300.pettasktracker.data.User;

/**
 *
 */
public class UserArrayAdapter extends ArrayAdapter<User> {

    private static class ViewHolder {
        TextView userName;
    }

    public UserArrayAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        final User user = getItem(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_user, parent, false);
            viewHolder.userName = (TextView) view.findViewById(R.id.userName);
            viewHolder.userName.setText(user.getName());
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }

}
