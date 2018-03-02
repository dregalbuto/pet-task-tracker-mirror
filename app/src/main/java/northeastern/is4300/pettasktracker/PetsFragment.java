package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PetsFragment extends Fragment {

    private TextView message1;

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

        /* Set up View Pet button */
        Button viewPetButton = (Button) v.findViewById(R.id.pet_button_1);
        viewPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewPetActivity_Cookie.class);
                startActivity(intent);
            }
        });

        /* Set up View Pet button */
        Button viewPetButton2 = (Button) v.findViewById(R.id.pet_button_2);
        viewPetButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewPetActivity_Rudy.class);
                startActivity(intent);
            }
        });

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
