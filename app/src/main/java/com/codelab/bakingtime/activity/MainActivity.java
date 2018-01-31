package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.RecipeAdapter;
import com.codelab.bakingtime.api.http.ApiUtils;
import com.codelab.bakingtime.api.models.RecipeModel;
import com.codelab.bakingtime.utility.ActivityUtils;
import com.codelab.bakingtime.utility.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RecipeModel> recipeModels;
    private RecipeAdapter recipeAdapter;
    private ProgressBar progressBar;
    private TextView tvError;
    private GridLayoutManager gridLayoutManager;

    private Parcelable mListState;
    private final String LIST_STATE_KEY = "list_state";
    private final String LIST_DATA_KEY = "list_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initFunctionality(savedInstanceState);
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        recipeModels = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(MainActivity.this, recipeModels);
        RecyclerView rvRecipe = (RecyclerView) findViewById(R.id.rv_recipe);
        gridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.home_column));
        rvRecipe.setLayoutManager(gridLayoutManager);
        rvRecipe.setAdapter(recipeAdapter);

        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
        tvError = (TextView) findViewById(R.id.tv_error);

    }

    private void initFunctionality(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadData();
        }
    }

    private void initListener() {
        recipeAdapter.setItemClickListener(new RecipeAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ActivityUtils.getInstance().invokeRecipeStepPage(MainActivity.this, recipeModels.get(position).getIngredients(), recipeModels.get(position).getSteps());
            }
        });
    }

    private void loadData() {
        if (Utils.isNetworkAvailable(this)) {
            ApiUtils.getApiInterface().getRecipeList().enqueue(new Callback<ArrayList<RecipeModel>>() {
                @Override
                public void onResponse(Call<ArrayList<RecipeModel>> call, Response<ArrayList<RecipeModel>> response) {
                    if (response != null && response.isSuccessful()) {
                        loadListData(response.body());
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
        } else {
            Utils.noInternetWarning(tvError, this);
        }
    }

    private void loadListData(ArrayList<RecipeModel> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            recipeModels.addAll(dataList);
            recipeAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        mListState = gridLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
        state.putParcelableArrayList(LIST_DATA_KEY, recipeModels);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            ArrayList<RecipeModel> restoredList = state.getParcelableArrayList(LIST_DATA_KEY);
            loadListData(restoredList);

            mListState = state.getParcelable(LIST_STATE_KEY);
            gridLayoutManager.onRestoreInstanceState(mListState);
        }
    }

}
