package com.example.recipe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recipe.MainActivity;
import com.example.recipe.Model.RecipeItem;
import com.example.recipe.R;
import com.example.recipe.RecipeClick;
import com.example.recipe.fragments.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RecipeItem> recipeItems;
    private static Context context;
    private RecipeClick recipeClick;
    private HashMap<Boolean,Fav> favs;

    public RecipeAdapter(Context context) {
        this.context = context;
        this.recipeClick = (MainActivity)context;
        favs = new HashMap<>();
        favs.put(false,new Fav(R.drawable.ic_favorite_black_24dp,"Removed from Favourites"));
        favs.put(true,new Fav(R.drawable.ic_favorite_red_24dp,"Marked as Favourite"));
    }

    public void update(ArrayList<RecipeItem> recipeItems) {

        this.recipeItems = recipeItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        RecipeViewHolder recipeViewHolder
                = (RecipeViewHolder) viewHolder;

        recipeViewHolder.setRecipe_name(recipeItems.get(i).getName());
        recipeViewHolder.setRecipe_thumbnail(recipeItems.get(i).getThumbnail());
    }

    @Override
    public int getItemCount() {
        if (recipeItems == null) return 0;
        return recipeItems.size();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private CardView cardView;
        private TextView recipe_name;
        private ImageView recipe_thumbnail;
        private TextView recipe_video_click;
        private ImageButton recipe_fav, recipe_share;
        private Context context = RecipeAdapter.context;
        private ProgressBar loading;
        private Boolean isFav = false;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            recipe_name = itemView.findViewById(R.id.recipe_name);
            recipe_thumbnail = itemView.findViewById(R.id.recipe_thumbnail);
            recipe_video_click = itemView.findViewById(R.id.recipe_video_click);
            recipe_fav = itemView.findViewById(R.id.recipe_fav);
            recipe_share = itemView.findViewById(R.id.recipe_share);
            loading = itemView.findViewById(R.id.loading);
            //click listners
            recipe_video_click.setOnClickListener(this);
            recipe_fav.setOnClickListener(this);
            recipe_share.setOnClickListener(this);
        }

        public void setRecipe_name(String name) {
            this.recipe_name.setText(name);
        }

        public void setRecipe_thumbnail(Bitmap url) {

            this.recipe_thumbnail.setImageBitmap(url);

        }


        @Override
        public void onClick(View v) {

            MainActivity mainActivity = (MainActivity) context;

            if (v.getId() == R.id.recipe_video_click) {

                this.loading.setVisibility(View.VISIBLE);
                recipeClick.show_details(getAdapterPosition(),loading);

            } else if (v.getId() == R.id.recipe_fav) {

                isFav = !isFav;
                recipe_fav.setImageDrawable(context.getDrawable(favs.get(isFav).getId()));
                recipeClick.mark_as_fav(getAdapterPosition(),favs.get(isFav).getMessage());

            } else if (v.getId() == R.id.recipe_share) {

                recipeClick.share(getAdapterPosition());

            }

        }


    }


    class Fav{

        Integer id; //<----id of drawable
        String message;

        public Fav(Integer id, String message) {
            this.id = id;
            this.message = message;
        }

        public Integer getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }
    }

}
