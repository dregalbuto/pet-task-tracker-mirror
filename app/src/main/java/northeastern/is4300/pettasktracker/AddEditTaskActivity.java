package northeastern.is4300.pettasktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import northeastern.is4300.pettasktracker.data.PetRepository;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskRepository;
import northeastern.is4300.pettasktracker.data.UserRepository;

public class AddEditTaskActivity extends AppCompatActivity {

    private TaskRepository taskRepository;
    private PetRepository petRepository;
    private UserRepository userRepository;
    private long taskId;

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

        Bundle b = getIntent().getExtras();
        if (b != null) {
            taskId = b.getLong(GlobalVariables.KEY_TASK_ID);
        } else {
            taskId = -1;
        }

        Task sourceTask = taskRepository.getTaskById(taskId);

        /* Set up drop-down menus (spinners) */
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_task_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.array_task_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        if (sourceTask != null) {
            spinner1.setSelection(adapter1.getPosition(sourceTask.getType()));
        }

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_task_user);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, userRepository.getUserNamesList());
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        if (sourceTask != null) {
            spinner2.setSelection(adapter2.getPosition(userRepository.getUserById(sourceTask.getUserId()).getName()));
        }

        Spinner spinner3 = (Spinner) findViewById(R.id.spinner_task_pet);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, petRepository.getPetNamesList());
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        if (sourceTask != null) {
            spinner3.setSelection(adapter3.getPosition(petRepository.getPetById(sourceTask.getPetId()).getName()));
        }

        Spinner spinner4 = (Spinner) findViewById(R.id.spinner_task_time);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.array_task_time, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        if (sourceTask != null) {
            spinner4.setSelection(adapter4.getPosition(sourceTask.getTaskTime()));
        }

        Spinner spinner5 = (Spinner) findViewById(R.id.spinner_task_repeat);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.array_task_repeat, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        if (sourceTask != null) {
            spinner5.setSelection(adapter5.getPosition(sourceTask.getRepeat()));
        }

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

                if (taskId > 0) {
                    taskRepository.updateTask(taskId, task);
                }
                else {
                    taskRepository.insertAndSetId(task);
                }

                confirmationButton.setText("Success!");

                Intent myIntent = new Intent(AddEditTaskActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
