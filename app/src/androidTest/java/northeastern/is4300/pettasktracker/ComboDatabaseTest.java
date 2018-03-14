package northeastern.is4300.pettasktracker;

import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetRepository;
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

    @Before
    public void setUp(){
        userRepository = new UserRepository(InstrumentationRegistry.getTargetContext());
        userRepository.open();

        petRepository = new PetRepository(InstrumentationRegistry.getTargetContext());
        petRepository.open();
    }

    @After
    public void finish() {
        userRepository.deleteAll();
        petRepository.deleteAll();
        userRepository.close();
        petRepository.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(userRepository);
        assertNotNull(petRepository);
    }

    @Test
    public void testShouldAddUser() throws Exception {
        userRepository.insert(new User("Diana", 1));
        petRepository.insert(new Pet("Fluffy", "Cat"));
        List<User> users = userRepository.getUserListAsUsers();
        List<Pet> pets = petRepository.getPetListAsPets();

        assertThat(users.size(), is(1));
        assertTrue(users.get(0).getName().equals("Diana"));
        assertEquals(users.get(0).getIsAdmin(), 1);

        assertThat(pets.size(), is(1));
        assertTrue(pets.get(0).getName().equals("Fluffy"));
        assertTrue(pets.get(0).getType().equals("Cat"));

    }

}
