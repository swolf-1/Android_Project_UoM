package uom.project.android_project_uom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Share;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class CreatPost extends AppCompatActivity {

    CheckBox fbBox, igBox, twitBox;
    Button postBTN;
    ShareButton shareBTN;
    CallbackManager callbackManager;
    TextView postTXT;
    ImageView imageView, targetImage;
    ImageButton selectImageBTN;

    FacebookSdk facebook = new FacebookSdk();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_post);

        //Initialize sdk
        FacebookSdk.sdkInitialize(CreatPost.this);

        fbBox = findViewById(R.id.facebookCheckBox);
        igBox = findViewById(R.id.instagramCheckBox);
        twitBox = findViewById(R.id.twitterCheckBox);
        postBTN = findViewById(R.id.postBtn);
        postTXT = findViewById(R.id.postText);
        shareBTN = findViewById(R.id.shareButton);
        imageView = findViewById(R.id.imageView6);
        imageView.setImageResource(R.drawable.facebook);
        selectImageBTN = findViewById(R.id.postImg);
        targetImage = findViewById(R.id.imageView);

        callbackManager = CallbackManager.Factory.create();

        postBTN.setOnClickListener(v -> {

            if (fbBox.isChecked())
            {
                facebookActivity();
            }

        });

        selectImageBTN.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);






        /*
        Uri targetUri = data.getData();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (bitmap != null) {


            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();

            SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                    .addPhoto(sharePhoto)
                    .build();

            shareBTN.setShareContent(sharePhotoContent);
        }

         */



    }


    public void facebookActivity(){

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

        postOnFacebook();


    }


    public void postOnFacebook(){

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.d("demo",response.toString());

                        try {
                            String id = object.getJSONObject("id").toString();
                            System.out.println("id:"+id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle bundle = new Bundle();
        bundle.putString("fields","id");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }
}





