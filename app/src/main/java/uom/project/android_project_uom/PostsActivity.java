package uom.project.android_project_uom;

import android.content.Intent;
import android.os.StrictMode;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity {

    private Twitter twitter;
    private RequestQueue mQueue;
    ListView listView;


    private static final String TWITTER_API_KEY = BuildConfig.TwitterApiKey;
    private static final String TWITTER_API_SECRET = BuildConfig.TwitterApiSecretKey;
    private static final String TWITTER_ACCESS_TOKEN = BuildConfig.TwitterAccessToken;
    private static final String TWITTER_ACCESS_TOKEN_SECRET = BuildConfig.TwitterAccessTokenSecret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        ArrayList<String> listWithPosts,urlsList;

        listView = findViewById(R.id.postsList);
        Bundle bundle = getIntent().getExtras();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_API_KEY)
                .setOAuthConsumerSecret(TWITTER_API_SECRET)
                .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        listWithPosts = twitterPosts(bundle.getString("HashtagForSearch"));
        urlsList = twitterPosts_photo(bundle.getString("HashtagForSearch"));

        String hashtag = bundle.getString("HashtagForSearch");
        hashtag = hashtag.replace("#","");

        mQueue = Volley.newRequestQueue(this);

        jsonParse(hashtag,listWithPosts,urlsList);


    }

    private void jsonParse(String hashtag,ArrayList<String> text,ArrayList<String> urls){

        String url = "https://graph.facebook.com/v9.0/ig_hashtag_search?user_id=17841401397896478&q=" + hashtag + "&access_token=EAAyZBLbC1xKoBAB7QXn4HRZBwQoT0aQtaI1mm7CUwZAdU3okgFDi0FMoDPlDavwDKfF7DqD80dffL1ch9iCxLOabbit5yS7HDnnZAIpLtZAF7LZCFtF1NsOQet4LQqMiEJaWI9Rul6n623haF2EiMhJTvvGiyEYC5Yz7Ymf8jEd0lYZALkm5bOpPHCaZBIaSZA2aHVYxkAFRhI8z9bf6eZA1sfPMjAUL81xpcZD";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i=0; i < jsonArray.length(); i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String id = object.getString("id");

                                jsonParsePosts(id,text,urls);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        mQueue.add(request);

    }

    private void jsonParsePosts(String id,ArrayList<String> text,ArrayList<String> urls){

        String url = "https://graph.facebook.com/v9.0/" + id + "/recent_media?user_id=17841401397896478&fields=caption,id,media_type,media_url,comments_count,like_count&access_token=EAAyZBLbC1xKoBAB7QXn4HRZBwQoT0aQtaI1mm7CUwZAdU3okgFDi0FMoDPlDavwDKfF7DqD80dffL1ch9iCxLOabbit5yS7HDnnZAIpLtZAF7LZCFtF1NsOQet4LQqMiEJaWI9Rul6n623haF2EiMhJTvvGiyEYC5Yz7Ymf8jEd0lYZALkm5bOpPHCaZBIaSZA2aHVYxkAFRhI8z9bf6eZA1sfPMjAUL81xpcZD";




        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for(int i=0; i < jsonArray.length(); i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);

                                if (object.getString("media_type").equals("IMAGE")){


                                    text.add(object.getString("caption"));
                                    urls.add(object.getString("media_url"));



                                    int igPIC = R.drawable.insta;
                                    ProgramAdapter programAdapter = new ProgramAdapter(PostsActivity.this, text, igPIC,urls);
                                    listView.setAdapter(programAdapter);


                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        mQueue.add(request);


    }

    public ArrayList<String> twitterPosts(String item) {
        ArrayList<String> statusList = new ArrayList<>();

        Query query = new Query(item);
        query.setCount(25);
        QueryResult result;

        try {
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();


            for (Status tweet : tweets) {
                statusList.add(tweet.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return statusList;
    }

    public ArrayList<String> twitterPosts_photo(String item) {
        ArrayList<String> urlsLists = new ArrayList<>();

        Query query = new Query(item);
        query.setCount(100);
        QueryResult result;

        try {
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            for (Status tweet : tweets) {
                MediaEntity[] media = tweet.getMediaEntities();
                for (MediaEntity m: media)
                {
                    urlsLists.add(m.getMediaURL());
                }
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return urlsLists;
    }



}