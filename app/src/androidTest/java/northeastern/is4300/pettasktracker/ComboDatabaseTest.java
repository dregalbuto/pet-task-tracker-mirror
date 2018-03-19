package northeastern.is4300.pettasktracker;

import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import northeastern.is4300.pettasktracker.data.JoinsRepository;
import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetRepository;
import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskRepository;
import northeastern.is4300.pettasktracker.data.User;
import northeastern.is4300.pettasktracker.data.UserRepository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */

public class ComboDatabaseTest {

    private UserRepository userRepository;
    private PetRepository petRepository;
    private TaskRepository taskRepository;
    private JoinsRepository joinsRepository;

    @Before
    public void setUp(){
        userRepository = new UserRepository(InstrumentationRegistry.getTargetContext());
        userRepository.open();
        userRepository.deleteAll();

        petRepository = new PetRepository(InstrumentationRegistry.getTargetContext());
        petRepository.open();
        petRepository.deleteAll();

        taskRepository = new TaskRepository(InstrumentationRegistry.getTargetContext());
        taskRepository.open();
        taskRepository.deleteAll();

        joinsRepository = new JoinsRepository(InstrumentationRegistry.getTargetContext());
        joinsRepository.open();
    }

    @After
    public void finish() {
        userRepository.deleteAll();
        userRepository.close();
        petRepository.deleteAll();
        petRepository.close();
        taskRepository.deleteAll();
        taskRepository.close();
        joinsRepository.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(userRepository);
        assertNotNull(petRepository);
    }

    @Test
    public void testAddToBoth() {
        userRepository.insertAndSetId(new User("Diana", 1));
        petRepository.insertAndSetId(new Pet("Fluffy", "Cat"));
        List<User> users = userRepository.getUserListAsUsers();
        ArrayList<Pet> pets = petRepository.cursorToPetList(petRepository.getPetsCursor());

        assertThat(users.size(), is(1));
        assertTrue(users.get(0).getName().equals("Diana"));
        assertEquals(users.get(0).getIsAdmin(), 1);

        assertThat(pets.size(), is(1));
        assertTrue(pets.get(0).getName().equals("Fluffy"));
        assertTrue(pets.get(0).getType().equals("Cat"));

    }

    @Test
    public void testAddTaskGetUserAndPet() {
        Pet pet = new Pet("Fluffy", "Cat");
        petRepository.insertAndSetId(pet);

        User user = new User("Diana", 1);
        userRepository.insertAndSetId(user);

        Task task = new Task("Walk", "12:00", "Daily");
        task.setUserId(user.getId());
        task.setPetId(pet.getId());
        taskRepository.insertAndSetId(task);

        Pet newPet = joinsRepository.getPetByTask(task);

        assertNotNull(newPet);
        assertEquals(newPet.getName(), "Fluffy");

        User newUser2 = userRepository.getUserByName("Diana");
        assertNotNull(newUser2);

        User newUser = joinsRepository.getUserByTask(task);


        assertNotNull(newUser);
        assertEquals(newUser.getName(), "Diana");

    }

}
