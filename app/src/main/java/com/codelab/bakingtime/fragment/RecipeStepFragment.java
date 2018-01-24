package com.codelab.bakingtime.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.RecipeAdapter;
import com.codelab.bakingtime.api.http.ApiUtils;
import com.codelab.bakingtime.api.models.RecipeModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepFragment extends Fragment {

    private ArrayList<RecipeModel> recipeModels;
    private RecipeAdapter recipeAdapter;
    private RecyclerView rvRecipe;

    public RecipeStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        recipeModels = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getActivity(), recipeModels);
        rvRecipe = (RecyclerView) rootView.findViewById(R.id.rv_recipe);
        rvRecipe.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecipe.setAdapter(recipeAdapter);

        loadData();

        return rootView;
    }

    private void loadData() {
        

    }
}
