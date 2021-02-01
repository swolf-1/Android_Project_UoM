package uom.project.android_project_uom;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder
{
    ImageView image;
    TextView post;
    ImageView photo;

    ProgramViewHolder(View v)
    {
        image = v.findViewById(R.id.icon);
        post = v.findViewById(R.id.postText);
        photo = v.findViewById(R.id.photo);

    }
}
