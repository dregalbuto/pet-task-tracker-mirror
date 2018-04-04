package northeastern.is4300.pettasktracker.data;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PetClient {
    private static final String API_BASE_URL =
            "http://pet-task-tracker.us-east-2.elasticbeanstalk.com/api/pets";
    private AsyncHttpClient client;

    public PetClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getPets(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
