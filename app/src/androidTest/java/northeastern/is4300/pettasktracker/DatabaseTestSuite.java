package northeastern.is4300.pettasktracker;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PetDatabaseTest.class,
        TaskDatabaseTest.class,
        UserDatabaseTest.class })

public class DatabaseTestSuite {
}
