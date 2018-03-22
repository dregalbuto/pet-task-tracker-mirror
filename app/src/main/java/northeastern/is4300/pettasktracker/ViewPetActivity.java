package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import northeastern.is4300.pettasktracker.data.PetRepository;

public class ViewPetActivity extends AppCompatActivity {

    private PetRepository petRepository;
    String petName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);
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
            this.petName = petName;
            String petType = targetPet.get("type");


            TextView nameText = (TextView) findViewById(R.id.textView);
            nameText.setText(petName);

            ImageView pet_icon = (ImageView) findViewById(R.id.petProfileIcon);
            if (petType.equals("Dog")) {
                pet_icon.setImageDrawable(getResources().getDrawable(R.drawable.dog_icon_100));
            }
        }

        // TODO display taskList

         /* Set up add task button */
        Button addTaskButton = (Button) findViewById(R.id.button_pet_add_task);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ViewPetActivity.this, AddEditTaskActivity.class);
                myIntent.putExtra(GlobalVariables.KEY_PET_NAME, petName);
                startActivity(myIntent);
            }
        });
    }
}
