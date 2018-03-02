package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewPetActivity_Cookie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_cookie);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
