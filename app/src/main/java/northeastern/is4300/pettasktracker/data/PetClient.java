package northeastern.is4300.pettasktracker.data;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.entity.StringEntity;

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

    public void addPet(Context context, final String query, Pet pet, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("");
            JSONObject jsonParams = Pet.toJson(pet);
            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(context,url + URLEncoder.encode(query, "utf-8"),
                    entity, "application/json",
                    handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
