package com.example.recipe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recipe.R;
import com.example.recipe.Retrofit.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(Context context) {
        this.context = context;
    }

    public void update(ArrayList<Ingredient> ingredients){

        this.ingredients = ingredients;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.ingred_layout,viewGroup,false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        IngredientViewHolder ingredientViewHolder
                = (IngredientViewHolder) viewHolder;
        Ingredient ingredient = ingredients.get(i);
        ingredientViewHolder.setData(ingredient.getQuantity()+"",ingredient.getMeasure(),ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder{

        private TextView quantity,measure,ingredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.measure = itemView.findViewById(R.id.measure);
            this.ingredient = itemView.findViewById(R.id.ingredient);
        }

        public void setData(String quantity,String measure,String ingredient){

            this.quantity.setText(context.getString(R.string.quantity) + quantity);
            this.measure.setText(context.getString(R.string.measure) + measure);
            this.ingredient.setText(context.getString(R.string.ingredient) + ingredient);
        }
    }

}
