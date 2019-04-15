package com.example.recipe.Model;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeItem {

    private String name;
    private String video_url;

    public RecipeItem(String name, String video_url) {
        this.name = name;
        this.video_url = video_url;
    }

    public String getName() {
        return name;
    }

    public Bitmap getThumbnail(){

        if(video_url==null) return null;

        Bitmap bitmap =
                null;
        try{

            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(video_url,new HashMap<String, String>());
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        }catch (Exception e){}

        return bitmap;
    }
}
