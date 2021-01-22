package uom.project.android_project_uom;

import android.os.StrictMode;

import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity
{
    private Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        ArrayList<String> listWithPosts ;
        ListView listView = findViewById(R.id.postsList);
        Bundle bundle = getIntent().getExtras();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("nuol21SMZBdqPn9TOFhAoTxQG")
                .setOAuthConsumerSecret("tXeYIizXrE0VmV30BwVf6ChyU2ceCpB6ojSD65Hg468E1vcASZ")
                .setOAuthAccessToken("1323212607143219200-232hw2BbTW7q76CrGN0lNrBUsyi1f7")
                .setOAuthAccessTokenSecret("i7TplqP1l0poJdlH8tKzeqSsiszt159K6bKGGUDktWILA");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        listWithPosts = twitterPosts(bundle.getString("HashtagForSearch"));

        int twitterPic = R.drawable.twitter;
        ProgramAdapter programAdapter = new ProgramAdapter(this,listWithPosts, twitterPic);
        listView.setAdapter(programAdapter);

        //final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,listWithPosts);





    }

    public ArrayList<String> twitterPosts(String item)
    {
        ArrayList<String> statusList = new ArrayList<>();

        Query query = new Query(item);
        query.setCount(100);
        QueryResult result;

        try 
        {
            result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            for (Status tweet: tweets)
            {
                statusList.add(tweet.getText());
            }
        } 
        catch (TwitterException e) 
        {
            e.printStackTrace();
        }
        return statusList;
    }
}