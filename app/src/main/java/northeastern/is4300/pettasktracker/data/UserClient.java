package northeastern.is4300.pettasktracker.data;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 *
 */

public class UserClient {
    private static final String API_BASE_URL =
            "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/users";
    private AsyncHttpClient client;

    public UserClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getUsers(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void addUser(Context context, final String query, User user, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("");
            JSONObject jsonParams = User.toJson(user);
            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(context,url + URLEncoder.encode(query, "utf-8"),
                    entity, "application/json",
                    handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
