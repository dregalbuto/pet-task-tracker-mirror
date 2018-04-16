package northeastern.is4300.pettasktracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.adapters.TaskArrayAdapter;
import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetClient;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskClient;

public class ViewPetActivity extends AppCompatActivity {

    private ArrayList<Task> tasksArrayList;
    private TaskArrayAdapter taskArrayAdapter;

    private static class PetDetails {
        public Pet pet;
        TextView petName;
        ImageView petIcon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final PetDetails petDetails = new PetDetails();
        petDetails.petName = (TextView) findViewById(R.id.textView);
        petDetails.petIcon = (ImageView) findViewById(R.id.petProfileIcon);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            long petId = b.getLong("PET_ID");

            final Context myContext = this;

            PetClient petClient = new PetClient();
            petClient.getPets("/" + petId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if(response != null) {
                        petDetails.pet = Pet.fromJson(response);
                        petDetails.petName.setText(petDetails.pet.getName());
                        if (petDetails.pet.getType().equals("Dog")) {
                            petDetails.petIcon.setImageDrawable(getResources()
                                    .getDrawable(R.drawable.dog_icon_100));
                        }
                        final ListView listViewTasks = (ListView) findViewById(R.id.pet_task_list);

                        tasksArrayList = new ArrayList<Task>();
                        taskArrayAdapter = new TaskArrayAdapter(myContext, tasksArrayList);
                        listViewTasks.setAdapter(taskArrayAdapter);
                        final Pet finalPet = petDetails.pet;

                        fetchTasks(finalPet);
                    }

                }
            });
        }

         /* Set up add task button */
        Button addTaskButton = (Button) findViewById(R.id.button_pet_add_task);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ViewPetActivity.this, AddEditTaskActivity.class);
                // TODO save this pet's name in Intent
                startActivity(myIntent);
            }
        });
    }

    private void fetchTasks(final Pet pet) {
        TaskClient taskClient = new TaskClient();
        taskClient.getTasks("", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                if(response != null) {
                    tasksArrayList = Task.fromJson(response);
                    taskArrayAdapter.clear();
                    for (Task task : tasksArrayList) {
                        if (task.getPet().getId() == pet.getId()) {
                            taskArrayAdapter.add(task);
                        }
                    }
                    taskArrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
