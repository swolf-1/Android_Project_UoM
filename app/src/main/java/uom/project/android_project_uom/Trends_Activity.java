package uom.project.android_project_uom;

import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;

public class Trends_Activity extends AppCompatActivity
{
    private Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<String> Hashtag_Twitter_Trends ;
        ListView listView= findViewById(R.id.trendsList);


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("nuol21SMZBdqPn9TOFhAoTxQG")
                .setOAuthConsumerSecret("tXeYIizXrE0VmV30BwVf6ChyU2ceCpB6ojSD65Hg468E1vcASZ")
                .setOAuthAccessToken("1323212607143219200-232hw2BbTW7q76CrGN0lNrBUsyi1f7")
                .setOAuthAccessTokenSecret("i7TplqP1l0poJdlH8tKzeqSsiszt159K6bKGGUDktWILA");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        Hashtag_Twitter_Trends = Twitter_Trends();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,Hashtag_Twitter_Trends);
        listView.setAdapter(adapter);

        for (String htt: Hashtag_Twitter_Trends)
        {
            System.out.println(htt);
        }






    }

    public ArrayList<String> Twitter_Trends()
    {
        ArrayList <String> trendsList = new ArrayList<>();
        try
        {
            Trends trends = twitter.getPlaceTrends(1);

            for (Trend trend : trends.getTrends())
            {
                if (trend.getName().startsWith("#")) {
                    trendsList.add(trend.getName());
                }
            }
        }catch (TwitterException e)
        {
            System.out.println(e.getMessage());
        }
        return trendsList;
    }
}