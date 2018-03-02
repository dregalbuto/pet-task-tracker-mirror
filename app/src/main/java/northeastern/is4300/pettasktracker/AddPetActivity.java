package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

            confirmationButton.setText("Success!");
                /*
                Intent myIntent = new Intent(AddPetActivity.this, MainActivity.class);
                startActivity(myIntent);
                */
            }
        });
    }
}
