package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.data.MySingleton;
import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetClient;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskClient;
import northeastern.is4300.pettasktracker.data.User;
import northeastern.is4300.pettasktracker.data.UserClient;

public class AddEditTaskActivity extends AppCompatActivity {

    private TaskClient taskClient;
    private PetClient petClient;
    private UserClient userClient;
    private long taskId;
    private Task sourceTask;
    private static ArrayList<String> users;
    private static ArrayList<String> pets;
    private Pet taskPet;
    private User taskUser;

    Spinner userSpinner;
    Spinner petSpinner;

    private ArrayAdapter<CharSequence> makeAdapter(int textArrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                textArrayResId,
                R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            taskId = b.getLong(GlobalVariables.KEY_TASK_ID);
            if (taskId > 0) {
                getTask(taskId);
            }
        } else {
            taskId = -1;
        }

        this.users = new ArrayList<>();
        this.pets = new ArrayList<>();

        /* Set up drop-down menus (spinners) */
        userSpinner = (Spinner)findViewById(R.id.spinner_task_user);
        loadUserSpinnerData();

        /* Set up drop-down menus (spinners) */
        petSpinner = (Spinner)findViewById(R.id.spinner_task_pet);
        loadPetSpinnerData();

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner_task_type);
        ArrayAdapter<CharSequence> adapter1 = makeAdapter(R.array.array_task_type);
        spinner1.setAdapter(adapter1);
        if (sourceTask != null) {
            spinner1.setSelection(adapter1.getPosition(sourceTask.getTaskType()));
        }

        ArrayAdapter<CharSequence> adapter4 = makeAdapter(R.array.array_task_time);
        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner_task_time);
        spinner4.setAdapter(adapter4);
        if (sourceTask != null) {
            spinner4.setSelection(adapter4.getPosition(sourceTask.getTaskTime().toString()));
        }

        final Spinner spinner5 = (Spinner) findViewById(R.id.spinner_task_repeat);
        ArrayAdapter<CharSequence> adapter5 = makeAdapter(R.array.array_task_repeat);
        spinner5.setAdapter(adapter5);
        if (sourceTask != null) {
            spinner5.setSelection(adapter5.getPosition(sourceTask.getTaskRepeat()));
        }

        /* Set up confirmation button */
        final Button confirmationButton = (Button) findViewById(R.id.button_task_confirm);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String taskType = spinner1.getSelectedItem().toString();
                final String petName = petSpinner.getSelectedItem().toString();
                final String userName = userSpinner.getSelectedItem().toString();
                String time = spinner4.getSelectedItem().toString();
                String repeat = spinner5.getSelectedItem().toString();

                if (sourceTask == null) {
                    sourceTask = new Task();
                }

                sourceTask.setTaskRepeat(repeat);
                sourceTask.setTaskType(taskType);

                try {
                    sourceTask.setTaskTime(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println("petName: " + petName);
                System.out.println("userName: " + userName);
                getPetQueue(petName, userName);

            }
        });


    }

    private void loadUserSpinnerData() {
        RequestQueue requestQueue = MySingleton.getInstance(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/users",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonObject = new JSONArray(response);
                    ArrayList<User> usersList = User.fromJson(jsonObject);
                    for (User u : usersList) {
                        AddEditTaskActivity.users.add(u.getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEditTaskActivity.this,
                            R.layout.item_spinner,
                            AddEditTaskActivity.users);
                    userSpinner.setAdapter(adapter);
                    if (sourceTask != null) {
                        userSpinner.setSelection(adapter.getPosition(sourceTask.getUser().getName()));
                    }
                }catch (JSONException e){
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

    private void loadPetSpinnerData() {
        RequestQueue requestQueue = MySingleton.getInstance(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/pets",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonObject = new JSONArray(response);
                            ArrayList<Pet> petsList = Pet.fromJson(jsonObject);
                            for (Pet p : petsList) {
                                AddEditTaskActivity.pets.add(p.getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEditTaskActivity.this,
                                    R.layout.item_spinner,
                                    AddEditTaskActivity.pets);
                            petSpinner.setAdapter(adapter);
                            if (sourceTask != null) {
                                petSpinner.setSelection(adapter.getPosition(sourceTask.getPet().getName()));
                            }
                        }catch (JSONException e){
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

    private void getTask(long taskId) {
        taskClient = new TaskClient();
        taskClient.getTasks("/" + Long.toString(taskId), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(response != null) {
                    sourceTask = Task.fromJson(response);
                }
            }
        });
    }

    private void getPetQueue(final String petName, final String userName) {
        RequestQueue requestQueue = MySingleton.getInstance(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/pets/name/" + petName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("petResponse: " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            taskPet = new Pet(Pet.fromJson(jsonObject));
                            System.out.println("taskPet: " + taskPet.getName());
                            sourceTask.setPet(taskPet);
                            System.out.println("sourceTask Pet: " + sourceTask.getPet().getName());
                            getUserQueue(userName);
                        }catch (JSONException e){
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

    private void getUserQueue(final String userName) {
        RequestQueue requestQueue = MySingleton.getInstance(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/users/name/" + userName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("userResponse: " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            taskUser = new User(User.fromJson(jsonObject));
                            System.out.println("taskUser: " + taskUser.getName());
                            sourceTask.setUser(taskUser);
                            System.out.println("sourceTask User: " + sourceTask.getUser().getName());
                            if (taskId > 0) {
                                updateTask();
                            }
                            else {
                                addTask();
                            }

                        }catch (JSONException e){
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

    private void addTask() {
        System.out.println("addTask: " + Task.toJson(sourceTask));
        JsonObjectRequest req = new JsonObjectRequest(
                "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/tasks",
                Task.toJson(sourceTask),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        finishAndReload();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(req);
    }

    private void updateTask() {
        System.out.println("updateTask: " + Task.toJson(sourceTask));
        JsonObjectRequest req = new JsonObjectRequest(
                "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/tasks",
                Task.toJson(sourceTask),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("updateTask response: " + response.toString());
                        finishAndReload();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(req);
    }

    public void finishAndReload() {
        //confirmationButton.setText("Success!");
        Intent myIntent = new Intent(AddEditTaskActivity.this, MainActivity.class);
        startActivity(myIntent);
    }

}
