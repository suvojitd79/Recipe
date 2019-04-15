package com.example.recipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.recipe.Model.RecipeItem;
import com.example.recipe.Retrofit.Response;
import com.example.recipe.fragments.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomNav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.recipe)
    FrameLayout frameLayout1;

    private static final String TAG = "d99";
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private FragmentManager fragmentManager;
    private List<Response> responses;


    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<Response> getResponses() {
        return responses;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();



        Recipe recipe = new Recipe();

        fragmentManager.beginTransaction()
                .add(R.id.recipe,recipe).commit();

    }



    @Override
    public void onClick(View v) {


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

     int id = menuItem.getItemId();
     menuItem.setChecked(true);


        return false;
    }
}
