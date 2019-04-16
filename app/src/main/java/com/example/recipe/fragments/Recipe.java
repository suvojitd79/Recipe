package com.example.recipe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.recipe.Adapter.RecipeAdapter;
import com.example.recipe.MainActivity;
import com.example.recipe.Model.RecipeItem;
import com.example.recipe.R;
import com.example.recipe.Retrofit.Response;
import com.example.recipe.Retrofit.RetrofitInterface;
import com.example.recipe.Retrofit.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recipe extends Fragment {

    private Context context;
    private RecipeAdapter recipeAdapter;
    private static final String TAG = "d99";
    ProgressBar progressBar;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.context = context;


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface retrofitInterface
                = retrofit.create(RetrofitInterface.class);

        Call<List<Response>> call = retrofitInterface.getAll();

        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {

                //error free
                if (response.isSuccessful() && response.code() == 200) {
                    ((MainActivity) context).setResponses(response.body());
                    update(response.body()); //<----process the data
                }

            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {

                Log.e(TAG, t.getMessage());

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(container.getContext())
                .inflate(R.layout.recipe_f1, container, false);

        this.progressBar = v.findViewById(R.id.progress_bar);


        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        //linear
        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        recipeAdapter =
                new RecipeAdapter(context);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeAdapter);

        return v;
    }

    public void update(List<Response> responses) {

        ArrayList<RecipeItem> recipeItems = new ArrayList<>();


        for (Response response : responses) {

            String name = response.getName();

            List<Step> steps = response.getSteps();

            //generating unique video for each recipe for thumbnail
            int random = new Random().nextInt(steps.size());
            int size = steps.size();
            while(TextUtils.isEmpty(steps.get(random).getVideoURL()) && size--!=0)
                random = new Random().nextInt(steps.size());

            if (size!=0)
                recipeItems.add(new RecipeItem(name, steps.get(random).getVideoURL()));

        }

        Log.i(TAG, recipeItems.size() + "");
        this.recipeAdapter.update(recipeItems);
        this.progressBar.setVisibility(View.GONE);
    }

}
