package northeastern.is4300.pettasktracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.HashMap;

import northeastern.is4300.pettasktracker.data.UserRepository;

public class ViewUserActivity extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load user info from previous screen
        Bundle b = getIntent().getExtras();
        if (b != null) {
            int userIndex = b.getInt("USER_INDEX");

            userRepository = new UserRepository(this);
            userRepository.open();
            HashMap<String, String> targetUser = userRepository.getUserList().get(userIndex);

            String userName = targetUser.get("name");
            int userIsAdmin = Integer.parseInt(targetUser.get("isAdmin"));

            TextView nameText = (TextView) findViewById(R.id.textView);
            nameText.setText(userName);

        }
    }
}
