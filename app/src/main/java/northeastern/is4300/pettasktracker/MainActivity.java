package northeastern.is4300.pettasktracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    static String last_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(last_fragment != null) {
            switch (last_fragment) {
                case GlobalVariables.VAL_FRAG_PETS:
                    BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigation);
                    nav.setSelectedItemId(R.id.navigation_pets);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, PetsFragment.newInstance());
                    transaction.commit();
                    break;
                case GlobalVariables.VAL_FRAG_USERS:
                    BottomNavigationView nav2 = (BottomNavigationView) findViewById(R.id.navigation);
                    nav2.setSelectedItemId(R.id.navigation_users);
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.frame_layout, UsersFragment.newInstance());
                    transaction2.commit();
                    break;
                case GlobalVariables.VAL_FRAG_TIMELINE:
                    BottomNavigationView nav3 = (BottomNavigationView) findViewById(R.id.navigation);
                    nav3.setSelectedItemId(R.id.navigation_home);
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.frame_layout, TimelineFragment.newInstance());
                    transaction3.commit();
                    break;
            }
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, TimelineFragment.newInstance());
            transaction.commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                selectedFragment = TimelineFragment.newInstance();
                                last_fragment = GlobalVariables.VAL_FRAG_TIMELINE;
                                break;
                            case R.id.navigation_pets:
                                selectedFragment = PetsFragment.newInstance();
                                last_fragment = GlobalVariables.VAL_FRAG_PETS;
                                break;
                            case R.id.navigation_users:
                                selectedFragment = UsersFragment.newInstance();
                                last_fragment = GlobalVariables.VAL_FRAG_USERS;
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
    }

}
