package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import northeastern.is4300.pettasktracker.adapters.PetCursorAdapter;
import northeastern.is4300.pettasktracker.data.PetRepository;

public class PetsFragment extends Fragment {

    private TextView message1;
    private PetRepository petRepository;

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pets, container, false);

        petRepository = new PetRepository(this.getContext());
        petRepository.open();

        Cursor petsCursor = petRepository.getPetsCursor();
        // Find ListView to populate
        ListView lvItems = (ListView) v.findViewById(R.id.pets_list_view);
        // Setup cursor adapter using cursor from last step
        PetCursorAdapter petsAdapter = new PetCursorAdapter(this.getContext(), petsCursor);
        // Attach cursor adapter to the ListView
        lvItems.setAdapter(petsAdapter);

        /* Set up Add Pet button */
        Button addPetButton = (Button) v.findViewById(R.id.button_add_pet);
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddPetActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }



}
