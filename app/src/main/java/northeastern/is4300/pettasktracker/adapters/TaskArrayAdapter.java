package northeastern.is4300.pettasktracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import northeastern.is4300.pettasktracker.AddEditTaskActivity;
import northeastern.is4300.pettasktracker.GlobalVariables;
import northeastern.is4300.pettasktracker.R;
import northeastern.is4300.pettasktracker.data.MySingleton;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.User;

/**
 *
 */

public class TaskArrayAdapter extends ArrayAdapter<Task> {

    private static ArrayList<User> usersArrayList;

    private static class ViewHolder {
        TextView taskTitle;
        TextView taskTime;
        final Button taskUserButton;

        ViewHolder(Button taskUserButton) {
            this.taskUserButton = taskUserButton;
        }
    }

    public TaskArrayAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        final Task task = getItem(position);
        TaskArrayAdapter.ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_task, parent, false);
            viewHolder = new ViewHolder((Button) view.findViewById(R.id.taskUserButton));
            viewHolder.taskTitle = (TextView) view.findViewById(R.id.taskTitle);
            viewHolder.taskTime = (TextView) view.findViewById(R.id.taskTime);
            view.setTag(viewHolder);
        } else {
            viewHolder = (TaskArrayAdapter.ViewHolder) view.getTag();
        }

        final View myFinalView = view;
        String taskTitleString = makeTaskTitle(task.getPet().getName(), task.getTaskType());
        viewHolder.taskTitle.setText(taskTitleString);
        viewHolder.taskTime.setText(task.getTaskTime().toString());
        viewHolder.taskUserButton.setText(task.getUser().getName());

        final ViewHolder finalViewHolder = viewHolder;
        final PopupMenu myMenu = new PopupMenu(myFinalView.getContext(), viewHolder.taskUserButton);
        setup(task, finalViewHolder, myMenu, myFinalView);

        ImageButton pencilButton = (ImageButton) view.findViewById(R.id.pencilButton);
        pencilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(myFinalView.getContext(), AddEditTaskActivity.class);
                myIntent.putExtra(GlobalVariables.KEY_TASK_ID, task.getId());
                myFinalView.getContext().startActivity(myIntent);
            }
        });

        CheckBox checkBox = view.findViewById(R.id.taskCheckBox);
        if (task.getIsCompleted() == 1) {
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                task.setIsCompleted((task.getIsCompleted()+1)%2);
                updateTask(task, myFinalView);
            }
        });

        return view;
    }

    private void setup(final Task task, final ViewHolder h, final PopupMenu myMenu,
                       final View myFinalView) {
        h.taskUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = MySingleton
                        .getInstance(view.getContext()).getRequestQueue();
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/users/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    usersArrayList = User.fromJson(new JSONArray(response));
                                    if (myMenu.getMenu().size() == 0) {
                                        for (User u : usersArrayList) {
                                            if (!u.getName().equals(h.taskUserButton.getText())) {
                                                myMenu.getMenu().add(u.getName());
                                            }
                                        }
                                    }
                                    setupMenuItem(task, myMenu, myFinalView);
                                    myMenu.show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                requestQueue.add(stringRequest);
            }
         });
    }

    private void setupMenuItem(final Task task, final PopupMenu myMenu,
                               final View view) {
            myMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    RequestQueue requestQueue = MySingleton
                            .getInstance(view.getContext()).getRequestQueue();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,
                            "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/users/name/"
                                    + menuItem.toString(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        task.setUser(User.fromJson(new JSONObject(response)));
                                        updateTask(task, view);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    requestQueue.add(stringRequest);

                    return true;
                }
            });
        }

    private void updateTask(Task task, View view) {
        System.out.println("updateTask: " + Task.toJson(task));
        JsonObjectRequest req = new JsonObjectRequest(
                "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/tasks",
                Task.toJson(task),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        MySingleton.getInstance(view.getContext()).addToRequestQueue(req);
    }

    private static String makeTaskTitle(String petName, String taskType) {
        String taskTitle = "";

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
