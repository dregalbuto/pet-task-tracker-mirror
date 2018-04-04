package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.adapters.TaskCursorAdapter;
import northeastern.is4300.pettasktracker.data.JoinsRepository;
import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetClient;
import northeastern.is4300.pettasktracker.data.PetRepository;

public class ViewPetActivity extends AppCompatActivity {

    private PetClient client;
    private PetRepository petRepository;
    private JoinsRepository joinsRepository;

    private static class PetDetails {
        public Pet pet;
        public TextView petName;
        public ImageView petIcon;
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
        petDetails.pet = new Pet();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            long petId = b.getLong("PET_ID");

            client = new PetClient();
            client.getPets("/" + petId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if(response != null) {
                        petDetails.pet = Pet.fromJson(response);
                        petDetails.petName.setText(petDetails.pet.getName());
                        if (petDetails.pet.getType().equals("Dog")) {
                            petDetails.petIcon.setImageDrawable(getResources()
                                    .getDrawable(R.drawable.dog_icon_100));
                        }
                    }

                }
            });
            petRepository = new PetRepository(this);
            petRepository.open();
            joinsRepository = new JoinsRepository(this);
            joinsRepository.open();
            Cursor taskCursor = joinsRepository
                    .getFilteredTasksCursor(petRepository.getPetByName("Rudy"));

            final ListView listView = (ListView) findViewById(R.id.pet_task_list);

            final TaskCursorAdapter tasksAdapter = new TaskCursorAdapter(this, taskCursor);
            listView.setAdapter(tasksAdapter);
            tasksAdapter.changeCursor(taskCursor);
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
}
