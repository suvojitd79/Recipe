package com.example.recipe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipe.Adapter.IngredientAdapter;
import com.example.recipe.R;
import com.example.recipe.Retrofit.Ingredient;

import java.util.ArrayList;

public class Ingre_1 extends Fragment {

    private Context context;
    private IngredientAdapter ingredientAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ing_2,container,false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_in);
        ingredientAdapter = new IngredientAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(ingredientAdapter);
        return v;
    }

    public void update(ArrayList<Ingredient> ingredients){

        this.ingredientAdapter.update(ingredients);

    }


}
