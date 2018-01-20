package com.codelab.bakingtime.api.http;

import com.codelab.bakingtime.api.models.RecipeModel;
import com.codelab.bakingtime.api.params.HttpParams;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ashiq on 1/20/2018.
 */

public interface ApiInterface {

    @GET(HttpParams.API_RECIPE)
    Call<ArrayList<RecipeModel>> getRecipeList();

}
