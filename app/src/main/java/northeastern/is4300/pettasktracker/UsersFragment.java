package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UsersFragment extends Fragment {

    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users, container, false);

        // TODO implement Users listView

        /* Set up View User button */
        // TODO button consistency
        Button viewUserButton = (Button) v.findViewById(R.id.user_button_martha);
        viewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewUserActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
