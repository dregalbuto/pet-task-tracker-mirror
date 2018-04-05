package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.adapters.UserArrayAdapter;
import northeastern.is4300.pettasktracker.data.User;
import northeastern.is4300.pettasktracker.data.UserClient;

public class UsersFragment extends Fragment {

    private UserClient client;
    private ArrayList<User> userArrayList;
    private UserArrayAdapter userArrayAdapter;

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

        userArrayList = new ArrayList<>();
        final ListView listView = (ListView) v.findViewById(R.id.users_listview);
        userArrayAdapter = new UserArrayAdapter(this.getContext(), userArrayList);
        listView.setAdapter(userArrayAdapter);
        fetchUsers();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewUserActivity.class);
                intent.putExtra("USER_ID", userArrayList.get(position).getId());
                startActivity(intent);
            }
        });

        return v;
    }

    private void fetchUsers() {
        client = new UserClient();
        client.getUsers("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response != null) {
                    userArrayList = User.fromJson(response);
                    userArrayAdapter.clear();
                    for (User user : userArrayList) {
                        userArrayAdapter.add(user);
                    }
                    userArrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
