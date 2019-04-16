package com.example.recipe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.recipe.R;
import com.example.recipe.RecipeDetails;
import com.example.recipe.Retrofit.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RecipeVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Step> steps;
    private Context context;
    private HashMap<Boolean,Integer> map;

    public RecipeVideoAdapter(Context context) {
        this.context = context;
        map = new HashMap<>();
        map.put(true,R.drawable.ic_play_circle_filled_black_24dp);
        map.put(false,R.drawable.ic_pause_circle_filled_black_24dp);
    }


    public void update(ArrayList<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_video_grid, viewGroup, false);
        return new RecipeVideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        RecipeVideoViewHolder recipeVideoViewHolder = (RecipeVideoViewHolder) viewHolder;
        recipeVideoViewHolder.setImageView(steps.get(i).getVideoURL());
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;
        return steps.size();
    }

    class RecipeVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private ProgressBar progressBar;
        private ImageView play_pause,black_layer;
        private boolean isPlay;

        public RecipeVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.media_video);
            progressBar = itemView.findViewById(R.id.progress);
            play_pause = itemView.findViewById(R.id.play_pause);
            black_layer = itemView.findViewById(R.id.black_layout);
            play_pause.setOnClickListener(this);
        }

        //extract the thumbnail and set
        public void setImageView(String url) {

            if (!TextUtils.isEmpty(url)) {

                try {

                    Bitmap bitmap = null;
                    MediaMetadataRetriever mediaMetadataRetriever
                            = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());

                    String duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

                    Long millisecond = Long.parseLong(duration) * 1000;

                    Bitmap bitmap1 = mediaMetadataRetriever.getFrameAtTime(ThreadLocalRandom.current().nextLong(millisecond));

                    this.imageView.setImageBitmap(bitmap1);

                } catch (Exception e) {

                    //this.imageView.setImageResource(R.drawable.ic_error_black_24dp);

                }

                this.progressBar.setVisibility(View.GONE);
                this.play_pause.setVisibility(View.VISIBLE);

            }


        }


        @Override
        public void onClick(View v) {
            Bundle data = new Bundle();
            RecipeDetails recipeDetails = (RecipeDetails) context;
            this.isPlay = !isPlay;
            this.play_pause.setImageDrawable(context.getDrawable(map.get(this.isPlay)));
            black_layer.setVisibility(isPlay?View.GONE:View.VISIBLE);
            data.putInt("position",getAdapterPosition());
            data.putBoolean("isPlay",this.isPlay);
            recipeDetails.playVideo(data);
        }


    }


}
