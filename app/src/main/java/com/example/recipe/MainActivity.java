package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recipe.Retrofit.Response;
import com.example.recipe.fragments.Fav;
import com.example.recipe.fragments.Friends;
import com.example.recipe.fragments.Recipe;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, RecipeClick {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomNav)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.frag)
    FrameLayout frameLayout1;

    private final int ACTIVITY_REQUEST_CODE = 1254;
    private ProgressBar loading_video;

    public static final String TAG = "d99";
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private FragmentManager fragmentManager;
    public static List<Response> responses;


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

        //default fragment
        Recipe recipe = new Recipe();
        fragmentManager.beginTransaction()
                .add(R.id.frag,new Recipe()).commit();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(this.loading_video!=null) this.loading_video.setVisibility(View.GONE);
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    @Override
    public void onClick(View v) {



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

     int id = menuItem.getItemId();

        if(id==R.id.frag1){


         fragmentManager.beginTransaction()
                 .replace(R.id.frag,new Recipe()).commit();

     }else if(id==R.id.frag2){

         fragmentManager.beginTransaction()
                 .replace(R.id.frag,new Fav()).commit();


     }else if(id == R.id.frag3){

         fragmentManager.beginTransaction()
                 .replace(R.id.frag,new Friends()).commit();

     }
        return true;
    }




    @Override
    public void show_details(int position, ProgressBar progressBar) {

        Intent intent = new Intent(this,RecipeDetails.class);
        Bundle bundle = new Bundle();
        //sending data as json String
        Gson gson = new Gson();
        try {
            this.loading_video = progressBar;
            String response = gson.toJson(responses.get(position));//<---lagging
            bundle.putString("response", response);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        catch (Exception e){

            Toast.makeText(this,getResources().getString(R.string.error_msg),Toast.LENGTH_SHORT).show();
        }

        }

    @Override
    public void mark_as_fav(int position) {

        Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void share(int position) {

        Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();

    }

}
