package uom.project.android_project_uom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.*;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import java.io.FileNotFoundException;
import java.util.Arrays;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

public class CreatPost extends AppCompatActivity {


    CheckBox fbBox, igBox, twitBox;
    Button postBTN;
    ShareButton shareBTN;
    CallbackManager callbackManager;
    TextView postTXT,usersNameTXT,loggedAsTXT;
    ImageView targetImage;
    ImageButton selectImageBTN,log_outBTN;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_post);

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

            Toast.makeText(getApplicationContext(),"You are successfully logged out.", Toast.LENGTH_LONG).show();

        });


        postBTN.setOnClickListener(v -> {

            if (fbBox.isChecked()){

                facebookLogin();

                sharePhoto(bitmap);


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
            Uri targetUri = data.getData();
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


    public void facebookLogin(){

        LoginManager.getInstance().logInWithReadPermissions(CreatPost.this,Arrays.asList("user_gender, user_friends"));
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

    public void setUsersNameOnTop(){

        loggedAsTXT.setVisibility(View.VISIBLE);
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

        /*

        SharePhoto photo = new SharePhoto.Builder().setBitmap(bm).build();
        SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
        ShareDialog dialog = new ShareDialog(this);
        dialog.show(content);

         */


    }
}





