package uom.project.android_project_uom;

import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.ArrayList;

public class TrendsActivity extends AppCompatActivity
{
    private Twitter twitter;
    private RequestQueue mQueue;
    private ListView listView;

    private static final String TWITTER_API_KEY = BuildConfig.TwitterApiKey;
    private static final String TWITTER_API_SECRET = BuildConfig.TwitterApiSecretKey;
    private static final String TWITTER_ACCESS_TOKEN = BuildConfig.TwitterAccessToken;
    private static final String TWITTER_ACCESS_TOKEN_SECRET = BuildConfig.TwitterAccessTokenSecret;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        ArrayList<String> Hashtag_Twitter_Trends ;
        listView = findViewById(R.id.trendsList);
        SearchView searchView = findViewById(R.id.searchView);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_API_KEY)
                .setOAuthConsumerSecret(TWITTER_API_SECRET)
                .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        Hashtag_Twitter_Trends = Twitter_Trends();

        mQueue = Volley.newRequestQueue(this);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,Hashtag_Twitter_Trends);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
                                            {
                                                @Override
                                                public boolean onQueryTextSubmit(String query)
                                                {

                                                    return false;
                                                }
                                                @Override
                                                public boolean onQueryTextChange(String newText)
                                                {
                                                    if(newText.isEmpty())
                                                    {
                                                        listView.setAdapter(adapter);
                                                    }
                                                    else
                                                    {
                                                        jsonParse(newText);
                                                    }


                                                    return false;
                                                }
                                            });

        listView.setOnItemClickListener((parent, view, i, id) -> {
            Intent intent = new Intent(TrendsActivity.this, PostsActivity.class);
            intent.putExtra("HashtagForSearch",listView.getItemAtPosition(i).toString());
            startActivity(intent);



        });

    }

    public ArrayList<String> Twitter_Trends()
    {
        ArrayList <String> trendsList = new ArrayList<>();

        try
        {
            Trends trends = twitter.getPlaceTrends(1);

            for (Trend trend : trends.getTrends())
            {
                if (trend.getName().startsWith("#"))
                {
                    trendsList.add(trend.getName());
                }
            }
        }
        catch (TwitterException e)
        {
            System.out.println(e.getMessage());
        }
        return trendsList;
    }


    private void jsonParse(String search)
    {

        String url = "http://suggestqueries.google.com/complete/search?client=firefox&ds=yt&q=%23"+search;
        ArrayList<String> searchArray = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {

                    try {

                        JSONArray array = response.getJSONArray(1);

                        searchArray.add("#"+search);

                        for (int i = 0; i < array.length(); i++)
                        {
                            searchArray.add(array.get(i).toString());

                        }
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,searchArray);
                        listView.setAdapter(adapter2);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
                , Throwable::printStackTrace);

        mQueue.add(request);

    }

}