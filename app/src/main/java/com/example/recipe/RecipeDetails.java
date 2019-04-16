package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.recipe.Adapter.RecipeVideoAdapter;
import com.example.recipe.Retrofit.Response;
import com.example.recipe.fragments.Ingre_1;
import com.example.recipe.fragments.Vid_1;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetails extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener,OnClickVideo{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Response response;
    @BindView(R.id.bottomNav)
    BottomNavigationView bottomNavigationView;
    private Vid_1 vid_1;
    private Ingre_1 ingre_1;

    @BindView(R.id.f1)
    FrameLayout f1;
    @BindView(R.id.f2)
    FrameLayout f2;

    public Response getResponse() {
        return response;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            this.response = new Gson().fromJson(bundle.getString("response"),Response.class);
            toolbar.setTitle(response.getName());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(RecipeDetails.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            bottomNavigationView.setOnNavigationItemSelectedListener(this);


            vid_1 = new Vid_1();
            ingre_1 = new Ingre_1();



            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.f1,vid_1).commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.f2,ingre_1).commit();
            f1.setVisibility(View.VISIBLE);
            f2.setVisibility(View.GONE);

        }

    }


    @Override
    public void onClick(View v) {


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId()==R.id.vid){

            f1.setVisibility(View.VISIBLE);
            f2.setVisibility(View.GONE);


        }

        else if(menuItem.getItemId()==R.id.ing){

            f1.setVisibility(View.GONE);
            f2.setVisibility(View.VISIBLE);
            ingre_1.update(response.getIngredients());
        }

        return true;
    }



    @Override
    public void playVideo(Bundle data) {
        try {
            data.putString("url",response.getSteps().get(data.getInt("position")).getVideoURL());
            data.putString("video_description",response.getSteps().get(data.getInt("position")).getDescription());
            vid_1.playVideo(data);
        }catch (Exception e){}
    }

    @Override
    protected void onPause() {
        super.onPause();
        vid_1.releasePlayer();//<---release player and reset
        vid_1.setVid_description(getResources().getString(R.string.default_vid_description));//<----- set the default title
    }
}
