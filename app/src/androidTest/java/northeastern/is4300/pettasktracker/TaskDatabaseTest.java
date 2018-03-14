package northeastern.is4300.pettasktracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import northeastern.is4300.pettasktracker.data.Task;
import northeastern.is4300.pettasktracker.data.TaskRepository;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class TaskDatabaseTest {

    private TaskRepository taskRepository;

    @Before
    public void setUp(){
        taskRepository = new TaskRepository(InstrumentationRegistry.getTargetContext());
        taskRepository.open();
    }

    @After
    public void finish() {
        taskRepository.deleteAll();
        taskRepository.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(taskRepository);
    }

    @Test
    public void testShouldAddTask() throws Exception {
        taskRepository.insert(new Task("Walk", "12:00", "Daily"));
        List<Task> tasks = taskRepository.getTaskListAsTasks();

        assertThat(tasks.size(), is(1));
        assertTrue(tasks.get(0).getType().equals("Walk"));
        assertTrue(tasks.get(0).getTaskTime().equals("12:00"));
        assertTrue(tasks.get(0).getRepeat().equals("Daily"));
    }

    @Test
    public void testDeleteAll() {
        taskRepository.deleteAll();
        List<Task> tasks = taskRepository.getTaskListAsTasks();
        assertThat(tasks.size(), is(0));
    }

    @Test
    public void testDeleteOnlyOne() {
        taskRepository.insert(new Task("Walk", "12:00", "Daily"));
        List<Task> tasks = taskRepository.getTaskListAsTasks();

        assertThat(tasks.size(), is(1));

        taskRepository.delete(tasks.get(0).getId());
        tasks = taskRepository.getTaskListAsTasks();

        assertThat(tasks.size(), is(0));
    }

    @Test
    public void testAddAndDelete() {
        taskRepository.deleteAll();
        taskRepository.insert(new Task("Walk", "12:00", "Daily"));
        taskRepository.insert(new Task("Food", "15:00", "Weekly"));
        taskRepository.insert(new Task("Medication", "03:00", "Daily"));

        List<Task> tasks = taskRepository.getTaskListAsTasks();
        assertThat(tasks.size(), is(3));

        taskRepository.delete((int)tasks.get(0).getId());
        taskRepository.delete((int)tasks.get(1).getId());

        tasks = taskRepository.getTaskListAsTasks();
        assertThat(tasks.size(), is(1));
    }

}
