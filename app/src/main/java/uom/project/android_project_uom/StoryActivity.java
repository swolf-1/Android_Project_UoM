package uom.project.android_project_uom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileNotFoundException;

public class StoryActivity extends AppCompatActivity {

    Button postBTN,selectBTN;
    CheckBox fb_checkBox,ig_checkBox,twitter_checkBox;
    ImageView theImage;

    Uri targetUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);


        postBTN = findViewById(R.id.post_button);
        selectBTN = findViewById(R.id.select_photo_button);

        fb_checkBox = findViewById(R.id.fb_checkBox);
        ig_checkBox = findViewById(R.id.ig_checkBox);
        twitter_checkBox = findViewById(R.id.twitter_checkBox);

        theImage = findViewById(R.id.storyImage);


        selectBTN.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public void facebookPostStory(){

    }

    public void instagramPostStory(){

    }

    public void twitterPostStory(){

    }
}