package uom.project.android_project_uom;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CreatPost extends AppCompatActivity {

    CheckBox fbBox,igBox,twitBox;
    Button postBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_post);

        fbBox = findViewById(R.id.facebookCheckBox);
        igBox = findViewById(R.id.instagramCheckBox);
        twitBox = findViewById(R.id.twitterCheckBox);

        postBTN = findViewById(R.id.postBtn);

        postBTN.setOnClickListener(v -> {
            if (fbBox.isChecked())
            {

                Log.d("demo","Post on fb");
            }

            if (igBox.isChecked())
            {
                Log.d("demo","Post on ig");
            }

            if (twitBox.isChecked())
            {
                Log.d("demo","Post on twitter");
            }

        });

    }


}