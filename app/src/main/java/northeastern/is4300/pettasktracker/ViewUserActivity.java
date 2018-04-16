package northeastern.is4300.pettasktracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.adapters.TaskArrayAdapter;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskClient;
import northeastern.is4300.pettasktracker.data.User;
import northeastern.is4300.pettasktracker.data.UserClient;

public class ViewUserActivity extends AppCompatActivity {

    private ArrayList<Task> tasksArrayList;
    private TaskArrayAdapter taskArrayAdapter;

    private static class UserDetails {
        public User user;
        public TextView userName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final UserDetails userDetails = new UserDetails();
        userDetails.userName = (TextView) findViewById(R.id.userName);

        // Load user info from previous screen
        Bundle b = getIntent().getExtras();
        if (b != null) {
            long userId = b.getLong("USER_ID");
            UserClient userClient = new UserClient();
            final Context myContext = this;
            userClient.getUsers("/" + userId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if(response != null) {
                        userDetails.user = User.fromJson(response);
                        userDetails.userName.setText(userDetails.user.getName());
                        final ListView listViewTasks = (ListView) findViewById(R.id.user_task_list);

                        tasksArrayList = new ArrayList<Task>();
                        taskArrayAdapter = new TaskArrayAdapter(myContext, tasksArrayList);
                        listViewTasks.setAdapter(taskArrayAdapter);
                        final User finalUser = userDetails.user;

                        fetchTasks(finalUser);
                    }

                }
            });
        }
    }

    private void fetchTasks(final User user) {
        TaskClient taskClient = new TaskClient();
        taskClient.getTasks("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response != null) {
                    tasksArrayList = Task.fromJson(response);
                    taskArrayAdapter.clear();
                    for (Task task : tasksArrayList) {
                        if (task.getUser().getId() == user.getId()) {
                            taskArrayAdapter.add(task);
                        }
                    }
                    taskArrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
