package northeastern.is4300.pettasktracker;

import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import northeastern.is4300.pettasktracker.adapters.PetArrayAdapter;

/**
 *
 */
public class PetsFragmentTest extends ActivityInstrumentationTestCase2<FragmentContainerActivity> {

    private PetsFragment petsFragment;

    public PetsFragmentTest() {
        super(FragmentContainerActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();

        petsFragment = new PetsFragment();
        getActivity().addFragment(petsFragment, PetsFragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
    }

    //@Test
    public void test() {
        final ListView petsListView = (ListView) petsFragment.getView().findViewById(R.id.pets_list_view);
        assertNotNull(petsListView);

        PetRepository petRepository = new PetRepository(petsFragment.getContext());
        petRepository.open();
        assertNotNull(petRepository);

        Cursor petsCursor = petRepository.getPetsCursor();
        assertNotNull(petsCursor);

        // Setup cursor adapter using cursor from last step
        final PetArrayAdapter petsAdapter = new PetArrayAdapter(petsFragment.getContext(), petsCursor);

        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Attach cursor adapter to the ListView
                petsListView.setAdapter(petsAdapter);

            }
        });
    }


}
