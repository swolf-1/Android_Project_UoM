package uom.project.android_project_uom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ProgramAdapter extends ArrayAdapter<String>
{
    Context contex;
    int img;
    ArrayList<String> post;
    ArrayList<String> photoUrls;

    public ProgramAdapter( Context context,ArrayList<String> post, int img,ArrayList<String> photoUrls)
    {
        super(context,R.layout.single_post,R.id.postText,post);
        this.contex = context;
        this.post = post;
        this.img = img;
        this.photoUrls = photoUrls;
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent)
    {
        View singlePost = convertView;
        ProgramViewHolder holder;

        if(singlePost == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singlePost = layoutInflater.inflate(R.layout.single_post,parent,false);
            holder = new ProgramViewHolder(singlePost);
            singlePost.setTag(holder);
        }
        else
        {
            holder = (ProgramViewHolder) singlePost.getTag();
        }

        if(photoUrls.get(position).startsWith("https://scontent.cdninstagram.com/"))
        {
            holder.image.setImageResource(R.drawable.insta);
        }
        else
        {
            holder.image.setImageResource(R.drawable.twitter);

        }

        holder.post.setText(post.get(position));
        Picasso.get().load(photoUrls.get(position)).into(holder.photo);

        return singlePost;
    }
}
