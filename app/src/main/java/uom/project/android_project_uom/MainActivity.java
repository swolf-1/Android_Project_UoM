package uom.project.android_project_uom;

import android.content.Intent;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton twitterTrendsBtn = findViewById(R.id.twitterTrendsBtn);
        twitterTrendsBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,TrendsActivity.class)));

        ImageButton cratePostBTN = findViewById(R.id.CreatePostbtn);
        cratePostBTN.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreatPost.class)));
        

    }

}