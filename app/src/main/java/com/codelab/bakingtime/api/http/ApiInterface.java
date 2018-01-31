package com.codelab.bakingtime.api.http;

import com.codelab.bakingtime.api.models.RecipeModel;
import com.codelab.bakingtime.api.params.HttpParams;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET(HttpParams.API_RECIPE)
    Call<ArrayList<RecipeModel>> getRecipeList();

}
