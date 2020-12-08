package uom.project.android_project_uom;

import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton twitterTrendsBtn = findViewById(R.id.twitterTrendsBtn);
        twitterTrendsBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(this, Trends_Activity.class);

        if (intent != null)
        {
            startActivity(intent);
        }

    }
}