package northeastern.is4300.pettasktracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import northeastern.is4300.pettasktracker.data.Pet;
import northeastern.is4300.pettasktracker.data.PetRepository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class PetDatabaseTest {

    private PetRepository petRepository;

    @Before
    public void setUp(){
        petRepository = new PetRepository(InstrumentationRegistry.getTargetContext());
        petRepository.open();
    }

    @After
    public void finish() {
        petRepository.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(petRepository);
    }

    @Test
    public void testShouldAddPet() throws Exception {
        petRepository.insertAndSetId(new Pet("Fluffy", "Cat"));
        List<Pet> pets = petRepository.getPetListAsPets();

        assertThat(pets.size(), is(1));
        assertTrue(pets.get(0).getName().equals("Fluffy"));
        assertTrue(pets.get(0).getType().equals("Cat"));
    }

    @Test
    public void testDeleteAll() {
        petRepository.deleteAll();
        List<Pet> rate = petRepository.getPetListAsPets();
        assertThat(rate.size(), is(0));
    }

    @Test
    public void testDeleteOnlyOne() {
        petRepository.insertAndSetId(new Pet("Fluffy", "Cat"));
        List<Pet> pets = petRepository.getPetListAsPets();

        assertThat(pets.size(), is(1));

        petRepository.delete(pets.get(0).getId());
        pets = petRepository.getPetListAsPets();

        assertThat(pets.size(), is(0));
    }

    @Test
    public void testAddAndDelete() {
        petRepository.deleteAll();
        petRepository.insertAndSetId(new Pet("Fluffy", "Cat"));
        petRepository.insertAndSetId(new Pet("Bruno", "Dog"));
        petRepository.insertAndSetId(new Pet("Pusheen", "Cat"));

        List<Pet> pets = petRepository.getPetListAsPets();
        assertThat(pets.size(), is(3));

        petRepository.delete(pets.get(0).getId());
        petRepository.delete(pets.get(1).getId());

        pets = petRepository.getPetListAsPets();
        assertThat(pets.size(), is(1));
    }

    @Test
    public void testGetByName() {
        petRepository.insertAndSetId(new Pet("Fluffy", "Cat"));
        petRepository.insertAndSetId(new Pet("Bruno", "Dog"));
        petRepository.insertAndSetId(new Pet("Pusheen", "Cat"));

        Pet pet = petRepository.getPetByName("Fluffy");

       assertEquals(pet.getType(), "Cat");
    }

}
