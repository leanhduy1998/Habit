package thegamers.duyle.gamers.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import thegamers.duyle.gamers.models.Feed;
import thegamers.duyle.gamers.R;

/**
 * Created by Duy Le on 11/28/2016.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {
    private ImageView mainImage;
    private TextView titleTextView;

    public FeedViewHolder(View itemView) {
        super(itemView);
        this.mainImage = (ImageView)itemView.findViewById(R.id.main_image);
        this.titleTextView = (TextView)itemView.findViewById(R.id.main_text);
    }
    public void updateUI(Feed feed) {

        String path = feed.getHabitPath();

        Picasso.with(itemView.getContext()).load(R.drawable.flightplanmusic).resize(50,50).into(mainImage);
        ////Picasso.with(itemView.getContext()).load(new File(path)).resize(50,50).into(mainImage);
        ////titleTextView.setText(feed.getHabitName());
        titleTextView.setText("OKOK");
    }
}
