package uom.project.android_project_uom;

import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("nuol21SMZBdqPn9TOFhAoTxQG")
                    .setOAuthConsumerSecret("tXeYIizXrE0VmV30BwVf6ChyU2ceCpB6ojSD65Hg468E1vcASZ")
                    .setOAuthAccessToken("1323212607143219200-232hw2BbTW7q76CrGN0lNrBUsyi1f7")
                    .setOAuthAccessTokenSecret("i7TplqP1l0poJdlH8tKzeqSsiszt159K6bKGGUDktWILA");

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            Trends trends = twitter.getPlaceTrends(1);

            int count = 0;

            for (Trend trend : trends.getTrends()) {
                if (count < 10) {
                    System.out.println(trend.getName());
                    count++;
                }
            }
        }catch (TwitterException e){
            System.out.println("----------------------");
            System.out.println(e.getMessage());
            System.out.println("----------------------");
        }

    }


}