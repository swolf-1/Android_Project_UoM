package uom.project.android_project_uom;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button twitterTrendsBtn = findViewById(R.id.twitterTrendsBtn);
        twitterTrendsBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TrendsActivity.class)));

        Button cratePostBTN = findViewById(R.id.CreatePostbtn);
        cratePostBTN.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreatPost.class)));

        Button storyBTN = findViewById(R.id.CreateStroyButton);
        storyBTN.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StoryActivity.class)));


    }
}