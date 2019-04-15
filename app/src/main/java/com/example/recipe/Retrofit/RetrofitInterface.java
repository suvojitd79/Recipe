package com.example.recipe.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {


    @GET("topher/2017/May/59121517_baking/baking.json")
    public Call<List<Response>> getAll();

}
