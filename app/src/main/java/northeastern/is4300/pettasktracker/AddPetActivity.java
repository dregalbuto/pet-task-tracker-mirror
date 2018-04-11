package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;

import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetClient;

public class AddPetActivity extends AppCompatActivity {

    private PetClient petClient;

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack("MainActivity", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_pet);

        /* Set up drop-down menu (spinner) */
        Spinner spinner = (Spinner) findViewById(R.id.spinner_pet_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_pet_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /* Set up confirmation button */
        final Button confirmationButton = (Button) findViewById(R.id.button_pet_confirm);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /** Save a pet **/
                EditText petNameBox = findViewById(R.id.petNameEdit);
                String petName = petNameBox.getText().toString();

                Spinner petTypeBox = findViewById(R.id.spinner_pet_type);
                String petType = petTypeBox.getSelectedItem().toString();

                Pet pet = new Pet(petName, petType);
                postPet(pet);

                Intent myIntent = new Intent(AddPetActivity.this, MainActivity.class);
                startActivity(myIntent);

            }
        });
    }

    private void postPet(Pet pet) {
        petClient = new PetClient();
        petClient.addPet(this,"", pet, new JsonHttpResponseHandler());
    }

}
