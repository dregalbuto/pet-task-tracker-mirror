package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import northeastern.is4300.pettasktracker.data.Data;

public class AddPetActivity extends AppCompatActivity {

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

                /** Hacky way to save a pet **/
                EditText petNameBox = findViewById(R.id.edit_pet_name);
                String petName = petNameBox.getText().toString();

                Spinner petTypeBox = findViewById(R.id.spinner_pet_type);
                String petType = petTypeBox.getSelectedItem().toString();

                Data d = (Data) getApplication();
                d.addPet(petName, petType);

                confirmationButton.setText("Success!");

                Intent myIntent = new Intent(AddPetActivity.this, MainActivity.class);
                startActivity(myIntent);

            }
        });
    }

}
