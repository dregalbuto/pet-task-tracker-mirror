package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import northeastern.is4300.pettasktracker.R;
import northeastern.is4300.pettasktracker.data.Pet;

/**
 *
 */
public class PetArrayAdapter extends ArrayAdapter<Pet> {

    private static class ViewHolder {
        TextView petName;
        ImageView petIcon;
    }

    public PetArrayAdapter(Context context, ArrayList<Pet> pets) {
        super(context, 0, pets);

    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        final Pet pet = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_pet, parent, false);
            viewHolder.petName = (TextView) view.findViewById(R.id.petName);
            viewHolder.petIcon = (ImageView) view.findViewById(R.id.petIcon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        // set values
        viewHolder.petName.setText(pet.getName());
        if (pet.getType().equals("Dog")) {
            viewHolder.petIcon.setImageDrawable(view.getResources()
                    .getDrawable(R.drawable.dog_icon_100));
        }
        return view;
    }
}
