package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.AddEditTaskActivity;
import northeastern.is4300.pettasktracker.GlobalVariables;
import northeastern.is4300.pettasktracker.R;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskClient;
import northeastern.is4300.pettasktracker.data.User;
import northeastern.is4300.pettasktracker.data.UserClient;

/**
 *
 */

public class TaskArrayAdapter extends ArrayAdapter<Task> {

    private static ArrayList<User> usersArrayList;

    private static class ViewHolder {
        public TextView taskTitle;
        public TextView taskTime;
        public final Button taskUserButton;

        public ViewHolder(Button taskUserButton) {
            this.taskUserButton = taskUserButton;
        }
    }

    public TaskArrayAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Task task = getItem(position);
        TaskArrayAdapter.ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_task, parent, false);
            viewHolder = new ViewHolder((Button) view.findViewById(R.id.taskUserButton));
            viewHolder.taskTitle = (TextView) view.findViewById(R.id.taskTitle);
            viewHolder.taskTime = (TextView) view.findViewById(R.id.taskTime);
            view.setTag(viewHolder);
        } else {
            viewHolder = (TaskArrayAdapter.ViewHolder) view.getTag();
        }

        final View myView = view;
        String taskTitleString = makeTaskTitle(task.getPet().getName(), task.getTaskType());
        viewHolder.taskTitle.setText(taskTitleString);
        viewHolder.taskTime.setText(task.getTaskTime().toString());
        viewHolder.taskUserButton.setText(task.getUser().getName());

        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.taskUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(myView.getContext(), view);

                final UserClient userClient = new UserClient();
                userClient.getUsers("/", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        if(response != null) {
                            usersArrayList = User.fromJson(response);
                            for (User u : usersArrayList) {
                                if (!u.getName().equals(finalViewHolder.taskUserButton.getText())) {
                                    menu.getMenu().add(u.getName());
                                }
                            }
                        }

                    }
                });
                menu.show();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        userClient.getUsers("/name/" + menuItem.toString(), new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                if(response != null) {
                                    task.setUser(User.fromJson(response));
                                }

                            }
                        });
                        final TaskClient taskClient = new TaskClient();
                        taskClient.updateTask(myView.getContext(),
                                Long.toString(task.getId()),
                                task,
                                new JsonHttpResponseHandler());
                        notifyDataSetChanged();
                        return true;
                    }
                });
            }
        });

        ImageButton pencilButton = (ImageButton) view.findViewById(R.id.pencilButton);
        pencilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(myView.getContext(), AddEditTaskActivity.class);
                myIntent.putExtra(GlobalVariables.KEY_TASK_ID, task.getId());
                myView.getContext().startActivity(myIntent);
            }
        });

        return view;
    }

    public static String makeTaskTitle(String petName, String taskType) {
        String taskTitle = new String();

        switch(taskType) {
            case "Walk":
                taskTitle = taskTitle.concat("Walk " + petName);
                break;
            case "Food":
                taskTitle = taskTitle.concat("Feed " + petName);
                break;
            case "Medication":
                taskTitle = taskTitle.concat("Give " + petName + " medicine");
                break;
        }
        return taskTitle;
    }
}
