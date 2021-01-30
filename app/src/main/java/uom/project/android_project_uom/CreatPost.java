package uom.project.android_project_uom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import org.json.JSONException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.io.File;
import java.io.FileNotFoundException;

public class CreatPost extends AppCompatActivity {


    CheckBox fbBox, igBox, twitBox;
    Button postBTN;
    ShareButton shareBTN;
    CallbackManager callbackManager;
    TextView postTXT,usersNameTXT,loggedAsTXT;
    ImageView targetImage;
    ImageButton selectImageBTN,log_outBTN;
    Bitmap bitmap;
    Uri targetUri;
    LoginButton loginButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_post);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fbBox = findViewById(R.id.facebookCheckBox);
        igBox = findViewById(R.id.instagramCheckBox);
        twitBox = findViewById(R.id.twitterCheckBox);
        postBTN = findViewById(R.id.postBtn);
        postTXT = findViewById(R.id.postText);
        shareBTN = findViewById(R.id.shareButton);
        selectImageBTN = findViewById(R.id.postImg);
        targetImage = findViewById(R.id.imageView);
        usersNameTXT = findViewById(R.id.usersName);
        log_outBTN = findViewById(R.id.logOutBTN);
        loggedAsTXT = findViewById(R.id.loggedAsTXT);
        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();


        if (isFacebookLoggedIn()){
            setUsersNameOnTop();
        }
        else{
            removeUsersName();
        }

        log_outBTN.setOnClickListener(v -> {
            LoginManager.getInstance().logOut();
            removeUsersName();

            Toast.makeText(getApplicationContext(),"You are successfully logged out from facebook.", Toast.LENGTH_LONG).show();

        });


        postBTN.setOnClickListener(v -> {

                if (fbBox.isChecked())
                {
                        sharePhoto(bitmap);

                }

                if (igBox.isChecked())
                {
                    Intent intent = shareInstagram();
                    startActivity(intent);
                }

                if (twitBox.isChecked())
                {
                    postOnTwitter();
                }

        });

        selectImageBTN.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && data.getData()!= null) {
            //For the selected photo from gallery
            targetUri = data.getData();

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (isFacebookLoggedIn()){
            setUsersNameOnTop();

        }
        else{
            removeUsersName();
        }

    }


    public boolean isFacebookLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }


    /*
    public void facebookLogin(){

        LoginManager.getInstance().logInWithReadPermissions(CreatPost.this, Collections.singletonList("user_gender, user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

     */

    public void setUsersNameOnTop(){

        loggedAsTXT.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                (object, response) -> {

                    try {
                        String name = object.getString("name");
                        usersNameTXT.setText(name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "name");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
        log_outBTN.setVisibility(View.VISIBLE);



    }

    public void removeUsersName(){
        usersNameTXT.setText("You are NOT logged in yet.");
        loggedAsTXT.setVisibility(View.INVISIBLE);
        log_outBTN.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);
    }



    public void sharePhoto(Bitmap bm){

      


        SharePhoto sharePhoto = new SharePhoto.Builder()
        .setBitmap(bm)
        .build();

        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();

        ShareDialog dialog = new ShareDialog(this);
        dialog.show(sharePhotoContent);
        



    }

    private Intent shareInstagram() {


        Intent postPhoto = new Intent("com.instagram.share.ADD_TO_FEED");
        postPhoto.setType("image/*");
        postPhoto.setPackage("com.instagram.android");
        postPhoto.setDataAndType(targetUri,"image/*");
        postPhoto.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        postPhoto.putExtra(Intent.EXTRA_STREAM, targetUri);

        return  postPhoto;
    }


    private void postOnTwitter(){

        File file = new File(targetUri.getPath());


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("nuol21SMZBdqPn9TOFhAoTxQG")
                .setOAuthConsumerSecret("tXeYIizXrE0VmV30BwVf6ChyU2ceCpB6ojSD65Hg468E1vcASZ")
                .setOAuthAccessToken("1323212607143219200-232hw2BbTW7q76CrGN0lNrBUsyi1f7")
                .setOAuthAccessTokenSecret("i7TplqP1l0poJdlH8tKzeqSsiszt159K6bKGGUDktWILA");


        TwitterFactory factory = new TwitterFactory(cb.build());
        Twitter twitter = factory.getInstance();


        try {
            StatusUpdate status = new StatusUpdate(postTXT.getText().toString());
            status.setMedia(file);
            twitter.updateStatus(status);
            Toast.makeText(getApplicationContext(),"You successfully posted on twitter", Toast.LENGTH_LONG).show();
        } catch (TwitterException e) {
            e.printStackTrace();
        }




    }


}





