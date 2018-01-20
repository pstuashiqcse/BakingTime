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
public class MainActivityFragment extends Fragment {

    private ArrayList<RecipeModel> recipeModels;
    private RecipeAdapter recipeAdapter;
    private RecyclerView rvRecipe;
    private ProgressBar progressBar;
    private TextView tvError;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        recipeModels = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getActivity(), recipeModels);
        rvRecipe = (RecyclerView) rootView.findViewById(R.id.rv_recipe);
        rvRecipe.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecipe.setAdapter(recipeAdapter);

        progressBar = (ProgressBar) rootView.findViewById(R.id.pb_loader);
        tvError = (TextView) rootView.findViewById(R.id.tv_error);

        loadData();

        return rootView;
    }

    private void loadData() {
        ApiUtils.getApiInterface().getRecipeList().enqueue(new Callback<ArrayList<RecipeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeModel>> call, Response<ArrayList<RecipeModel>> response) {
                if (response != null && response.isSuccessful()) {
                    recipeModels.addAll(response.body());
                    recipeAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    tvError.setVisibility(View.GONE);
                } else {
                    tvError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeModel>> call, Throwable t) {
                t.printStackTrace();
                tvError.setVisibility(View.GONE);
            }
        });

    }
}
