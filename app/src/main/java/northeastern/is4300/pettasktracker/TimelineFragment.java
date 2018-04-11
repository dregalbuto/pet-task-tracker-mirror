package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.adapters.TaskArrayAdapter;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskClient;

public class TimelineFragment extends Fragment {

    private TaskClient taskClient;
    private ArrayList<Task> tasksArrayList;
    private TaskArrayAdapter taskArrayAdapter;

    public static TimelineFragment newInstance() {
        TimelineFragment fragment = new TimelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);

        taskClient = new TaskClient();
        final ListView listView = (ListView) v.findViewById(R.id.users_listview);

        tasksArrayList = new ArrayList<>();
        taskArrayAdapter = new TaskArrayAdapter(getActivity(), tasksArrayList);
        listView.setAdapter(taskArrayAdapter);
        fetchTasks();

        Button addTaskButton = (Button) v.findViewById(R.id.button_home_add_task);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), AddEditTaskActivity.class);
                myIntent.putExtra(GlobalVariables.KEY_TASK_ID, (long) -1);
                startActivity(myIntent);
            }
        });

        final SwipeRefreshLayout mySwipeRefreshLayout = v.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchTasks();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


        return v;
    }

    private void fetchTasks() {
        taskClient = new TaskClient();
        taskClient.getTasks("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response != null) {
                    tasksArrayList = Task.fromJson(response);
                    taskArrayAdapter.clear();
                    for (Task task : tasksArrayList) {
                            taskArrayAdapter.add(task);
                    }
                    taskArrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
