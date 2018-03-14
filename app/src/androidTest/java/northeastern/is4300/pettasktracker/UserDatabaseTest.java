package northeastern.is4300.pettasktracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
@RunWith(AndroidJUnit4.class)
public class UserDatabaseTest {

    private UserRepository userRepository;

    @Before
    public void setUp(){
        userRepository = new UserRepository(InstrumentationRegistry.getTargetContext());
        userRepository.open();
        userRepository.deleteAll();
    }

    @After
    public void finish() {
        userRepository.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(userRepository);
    }

    @Test
    public void testShouldAddUser() throws Exception {
        userRepository.insert(new User("Diana", 1));
        List<User> users = userRepository.getUserListAsUsers();

        assertThat(users.size(), is(1));
        assertTrue(users.get(0).getName().equals("Diana"));
        assertEquals(users.get(0).getIsAdmin(), 1);
    }

    @Test
    public void testDeleteAll() {
        userRepository.deleteAll();
        List<User> users = userRepository.getUserListAsUsers();
        assertThat(users.size(), is(0));
    }

    @Test
    public void testDeleteOnlyOne() {
        userRepository.insert(new User("Diana", 1));
        List<User> users = userRepository.getUserListAsUsers();

        assertThat(users.size(), is(1));

        userRepository.delete(users.get(0).getId());
        users = userRepository.getUserListAsUsers();

        assertThat(users.size(), is(0));
    }

    @Test
    public void testAddAndDelete() {
        userRepository.deleteAll();
        userRepository.insert(new User("Fluffy", 1));
        userRepository.insert(new User("Bruno", 0));
        userRepository.insert(new User("Pusheen", 0));

        List<User> users = userRepository.getUserListAsUsers();
        assertThat(users.size(), is(3));

        userRepository.delete((int)users.get(0).getId());
        userRepository.delete((int)users.get(1).getId());

        users = userRepository.getUserListAsUsers();
        assertThat(users.size(), is(1));
    }

}
