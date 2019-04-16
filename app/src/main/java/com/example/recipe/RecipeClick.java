package com.example.recipe;

import android.widget.ProgressBar;

public interface RecipeClick {

    public void show_details(int position, ProgressBar progressBar);

    public void mark_as_fav(int position);

    public void share(int position);
}
