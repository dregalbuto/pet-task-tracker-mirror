package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PetsFragment extends Fragment implements View.OnClickListener {

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

        /* Set up Add Pet button */
        Button addPetButton = (Button) v.findViewById(R.id.button_add_pet);
        message1 = v.findViewById(R.id.message_users);
        addPetButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add_pet:
                Intent intent = new Intent(getActivity(), AddPetActivity.class);
                startActivity(intent);
                break;
        }
    }

}
