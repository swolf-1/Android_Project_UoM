package uom.project.android_project_uom;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity
{

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();

        loginButton.setPermissions(Arrays.asList("user_gender, user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Demo", "Succ");
            }

            @Override
            public void onCancel() {
                Log.d("Demo", "can");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Demo", "Error");

            }
        });



        ImageButton twitterTrendsBtn = findViewById(R.id.twitterTrendsBtn);
        twitterTrendsBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,TrendsActivity.class)));

        ImageButton cratePostBTN = findViewById(R.id.CreatePostbtn);
        cratePostBTN.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreatPost.class)));
        

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);



        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response){
                        Log.d("Demo",object.toString());

                        try {
                            String id = object.getString("id");

                            //new  request
                            /* make the API call */
                            new GraphRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    "/oembed_page?url=https%3A%2F%2Fwww.facebook.com%2FCDC&access_token=6a297178d0a42cda6a1ee440c41929ee",
                                    null,
                                    HttpMethod.GET,
                                    response1 -> Log.d("Demo", response1.toString())
                            ).executeAsync();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                });

        Bundle bundle = new Bundle();
        bundle.putString("fields","gender, name, id, first_name, last_name");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();

    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null)
            {
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}