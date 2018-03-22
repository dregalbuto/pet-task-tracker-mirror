package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import northeastern.is4300.pettasktracker.adapters.UserCursorAdapter;
import northeastern.is4300.pettasktracker.data.UserRepository;

public class UsersFragment extends Fragment {

    private UserRepository userRepository;

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

        userRepository = new UserRepository(this.getContext());
        userRepository.open();

        if (userRepository.getUserList().size() == 0) {
            userRepository.loadSomeUsers();
        }

        Cursor usersCursor = userRepository.getUsersCursor();

        final ListView listView = (ListView) v.findViewById(R.id.home_tasks_listview);

        final UserCursorAdapter usersAdapter = new UserCursorAdapter(getActivity(), usersCursor);
        listView.setAdapter(usersAdapter);
        usersAdapter.changeCursor(usersCursor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewUserActivity.class);
                intent.putExtra("USER_INDEX", position);
                startActivity(intent);
            }
        });

        return v;
    }

}
