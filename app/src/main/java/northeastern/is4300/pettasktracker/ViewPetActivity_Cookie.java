package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import northeastern.is4300.pettasktracker.data.PetRepository;

public class ViewPetActivity_Cookie extends AppCompatActivity {

    private PetRepository petRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_cookie);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load pet info from previous screen
        Bundle b = getIntent().getExtras();
        if (b != null) {
            int petIndex = b.getInt("PET_INDEX");

            petRepository = new PetRepository(this);
            petRepository.open();
            HashMap<String, String> targetPet = petRepository.getPetList().get(petIndex);

            String petName = targetPet.get("name");
            String petType = targetPet.get("type");

            TextView nameText = (TextView) findViewById(R.id.textView);
            nameText.setText(petName);
        }

         /* Set up add task button */
        Button addTaskButton = (Button) findViewById(R.id.button_pet_add_task_cookie);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ViewPetActivity_Cookie.this, AddTaskActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
