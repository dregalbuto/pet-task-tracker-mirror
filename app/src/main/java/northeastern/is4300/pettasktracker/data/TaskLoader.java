package northeastern.is4300.pettasktracker.data;

import android.content.Context;

/**
 *
 */
public class TaskLoader {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private PetRepository petRepository;

    public TaskLoader(Context context) {
        taskRepository = new TaskRepository(context);
        taskRepository.open();
        userRepository = new UserRepository(context);
        userRepository.open();
        petRepository = new PetRepository(context);
        petRepository.open();
    }

    public void addSomeTasks() {
        User martha = userRepository.getUserByName("Martha");
        User stuart = userRepository.getUserByName("Stuart");
        User tim = userRepository.getUserByName("Tim");
        Pet cookie = petRepository.getPetByName("Cookie");
        Pet rudy = petRepository.getPetByName("Rudy");

        taskRepository.insertAndSetId(new Task(Task.TASK_TYPE.Food,
                "12:00 PM", "Daily", cookie.getId(), martha.getId()));
        taskRepository.insertAndSetId(new Task(Task.TASK_TYPE.Walk,
                "4:00 PM", "Daily", rudy.getId(), tim.getId()));
        taskRepository.insertAndSetId(new Task(Task.TASK_TYPE.Medication,
                "6:00 PM", "Daily", cookie.getId(), stuart.getId()));

    }
}
