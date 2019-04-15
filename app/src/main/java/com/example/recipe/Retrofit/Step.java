package com.example.recipe.Retrofit;

import com.google.gson.annotations.Expose;

public class Step {

    @Expose(deserialize = true)
    private int id;

    @Expose(deserialize = true)
    private String shortDescription;

    @Expose(deserialize = true)
    private String description;

    @Expose(deserialize = true)
    private String videoURL;

    @Expose(deserialize = true)
    private String thumbnailURL;

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
