package northeastern.is4300.pettasktracker;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EditTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        /* Set up drop-down menus (spinners) */
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_task_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.array_task_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_task_user);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.array_task_user, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_task_pet);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.array_task_pet, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        Spinner spinner4 = (Spinner) findViewById(R.id.spinner_task_time);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.array_task_time, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        Spinner spinner5 = (Spinner) findViewById(R.id.spinner_task_repeat);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.array_task_repeat, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);

        /* Set up confirmation button */
        final Button confirmationButton = (Button) findViewById(R.id.button_task_confirm);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                confirmationButton.setText("Success!");

                /*
                Intent myIntent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(myIntent);
                */
            }
        });
    }
}
