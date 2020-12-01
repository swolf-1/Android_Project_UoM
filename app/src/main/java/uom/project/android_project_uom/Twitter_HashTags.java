package uom.project.android_project_uom;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Twitter_HashTags extends AppCompatActivity {

    public static final String  TWITTER_API_KEY = BuildConfig.TwitterApiKey;
    public static final String TWITTER_API_SECRET_KEY  = BuildConfig.TwitterApiSecretKey;
    public static final String  TWITTER_BEARER_TOKEN = BuildConfig.TwitterBearerToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter__hash_tags);
    }
}