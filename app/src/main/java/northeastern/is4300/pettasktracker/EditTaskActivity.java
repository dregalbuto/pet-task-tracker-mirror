package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import northeastern.is4300.pettasktracker.data.PetRepository;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskRepository;
import northeastern.is4300.pettasktracker.data.UserRepository;

public class EditTaskActivity extends AppCompatActivity {

    private TaskRepository taskRepository;
    private PetRepository petRepository;
    private UserRepository userRepository;

    private void initRepositories() {
        taskRepository = new TaskRepository(this);
        taskRepository.open();
        petRepository = new PetRepository(this);
        petRepository.open();
        userRepository = new UserRepository(this);
        userRepository.open();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initRepositories();

        /* Set up drop-down menus (spinners) */
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_task_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.array_task_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        // TODO populate from petList
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_task_user);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.array_task_user, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        // TODO populate from userList
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

                Spinner spinner1 = (Spinner) findViewById(R.id.spinner_task_type);
                String taskType = spinner1.getSelectedItem().toString();
                Spinner spinner3 = (Spinner) findViewById(R.id.spinner_task_pet);
                String pet = spinner3.getSelectedItem().toString();
                Spinner spinner2 = (Spinner) findViewById(R.id.spinner_task_user);
                String user = spinner2.getSelectedItem().toString();
                Spinner spinner4 = (Spinner) findViewById(R.id.spinner_task_time);
                String time = spinner4.getSelectedItem().toString();
                Spinner spinner5 = (Spinner) findViewById(R.id.spinner_task_repeat);
                String repeat = spinner5.getSelectedItem().toString();

                Task task = new Task(Task.getTypeEnum(taskType), time, repeat);

                long petId = petRepository.getPetByName(pet).getId();
                task.setPetId(petId);

                long userId = userRepository.getUserByName(user).getId();
                task.setUserId(userId);

                taskRepository.insertAndSetId(task);

                confirmationButton.setText("Success!");

                Intent myIntent = new Intent(EditTaskActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
