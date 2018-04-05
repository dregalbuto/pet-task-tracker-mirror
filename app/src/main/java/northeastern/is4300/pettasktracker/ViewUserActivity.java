package northeastern.is4300.pettasktracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import northeastern.is4300.pettasktracker.data.User;
import northeastern.is4300.pettasktracker.data.UserClient;

public class ViewUserActivity extends AppCompatActivity {

    private UserClient userClient;

    private static class UserDetails {
        public User user;
        public TextView userName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final UserDetails userDetails = new UserDetails();
        userDetails.userName = (TextView) findViewById(R.id.textView);

        // Load user info from previous screen
        Bundle b = getIntent().getExtras();
        if (b != null) {
            long userId = b.getLong("USER_ID");
            userClient = new UserClient();
            userClient.getUsers("/" + userId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if(response != null) {
                        userDetails.user = User.fromJson(response);
                        userDetails.userName.setText(userDetails.user.getName());
                    }

                }
            });
        }
    }
}
