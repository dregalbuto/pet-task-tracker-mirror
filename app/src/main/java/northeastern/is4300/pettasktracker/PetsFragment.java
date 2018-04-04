package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.adapters.PetArrayAdapter;
import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetClient;

public class PetsFragment extends Fragment {

    private PetClient client;
    private PetArrayAdapter petArrayAdapter;

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pets, container, false);

        final ListView listViewPets = (ListView) v.findViewById(R.id.pets_list_view);
        ArrayList<Pet> pets = new ArrayList<Pet>();
        petArrayAdapter = new PetArrayAdapter(this.getContext(), pets);
        listViewPets.setAdapter(petArrayAdapter);
        fetchPets();

        listViewPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewPetActivity.class);
                intent.putExtra("PET_INDEX", position);
                startActivity(intent);
            }
        });

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

    private void fetchPets() {
        client = new PetClient();
        client.getPets("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if(response != null) {
                    final ArrayList<Pet> pets = Pet.fromJson(response);
                    petArrayAdapter.clear();
                    for (Pet pet : pets) {
                        petArrayAdapter.add(pet); // add book through the adapter
                    }
                    petArrayAdapter.notifyDataSetChanged();
                }

            }
        });
    }

}
