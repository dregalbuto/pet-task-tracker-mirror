package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import northeastern.is4300.pettasktracker.adapters.PetCursorAdapter;
import northeastern.is4300.pettasktracker.data.PetRepository;

public class PetsFragment extends Fragment {

    private PetRepository petRepository;

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pets, container, false);

        petRepository = new PetRepository(this.getContext());
        petRepository.open();

        if (petRepository.getPetList().size() == 0) {
            petRepository.loadSomePets();
        }

        Cursor petsCursor = petRepository.getPetsCursor();

        final ListView listView = (ListView) v.findViewById(R.id.pets_list_view);

        final PetCursorAdapter petsAdapter = new PetCursorAdapter(getActivity(), petsCursor);
        listView.setAdapter(petsAdapter);
        petsAdapter.changeCursor(petsCursor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewPetActivity.class);
                intent.putExtra("PET_INDEX", position);
                startActivity(intent);
            }
        });

        /* Set up Add Pet button */
        // TODO button consistency
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
