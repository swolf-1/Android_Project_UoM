package uom.project.android_project_uom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.FileNotFoundException;

public class StoryActivity extends AppCompatActivity {

    Button postBTN,selectBTN;
    CheckBox fb_checkBox,ig_checkBox,twitter_checkBox;
    ImageView theImage;

    Uri targetUri;
    Bitmap bitmap;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        postBTN = findViewById(R.id.post_button);
        selectBTN = findViewById(R.id.select_photo_button);

        fb_checkBox = findViewById(R.id.fb_checkBox);
        ig_checkBox = findViewById(R.id.ig_checkBox);
        twitter_checkBox = findViewById(R.id.twitter_checkBox);

        theImage = findViewById(R.id.storyImage);

        callbackManager = CallbackManager.Factory.create();


        selectBTN.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        });

        postBTN.setOnClickListener(v -> {
            if (theImage.getDrawable() == null)
            {
                Toast.makeText(getApplicationContext(), "No photo selected", Toast.LENGTH_LONG).show();
            }
            else
            {

                if (fb_checkBox.isChecked())
                {
                    facebookPostStory(bitmap);

                }

                if (ig_checkBox.isChecked())
                {
                    Intent intent = instagramPostStory();
                    startActivity(intent);
                }

                if (twitter_checkBox.isChecked())
                {

                }
            }
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
                theImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void facebookPostStory(Bitmap bm){

        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(bm)
                .build();

        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();

        ShareDialog dialog = new ShareDialog(this);
        dialog.show(sharePhotoContent);

    }

    private Intent instagramPostStory(){

        Intent postPhoto = new Intent("com.instagram.share.ADD_TO_STORY");
        postPhoto.setType("image/*");
        postPhoto.setPackage("com.instagram.android");
        postPhoto.setDataAndType(targetUri,"image/*");
        postPhoto.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        postPhoto.putExtra(Intent.EXTRA_STREAM, targetUri);

        return  postPhoto;

    }

    public void twitterPostStory(){

    }
}