package com.codelab.bakingtime.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.FavRecipeAdapter;
import com.codelab.bakingtime.adapter.RecipeAdapter;
import com.codelab.bakingtime.api.http.ApiUtils;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.RecipeModel;
import com.codelab.bakingtime.data.preference.AppPreference;
import com.codelab.bakingtime.data.preference.PrefKey;
import com.codelab.bakingtime.utility.ActivityUtils;
import com.codelab.bakingtime.utility.RecipeWidget;
import com.codelab.bakingtime.utility.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavRecipeActivity extends AppCompatActivity {

    private ArrayList<RecipeModel> recipeModels;
    private FavRecipeAdapter favRecipeAdapter;
    private ProgressBar progressBar;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initFunctionality(savedInstanceState);
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_fav_recipe);
        recipeModels = new ArrayList<>();
        favRecipeAdapter = new FavRecipeAdapter(FavRecipeActivity.this, recipeModels);
        RecyclerView rvRecipe = (RecyclerView) findViewById(R.id.rv_recipe);
        rvRecipe.setLayoutManager(new LinearLayoutManager(FavRecipeActivity.this));
        rvRecipe.setAdapter(favRecipeAdapter);

        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
        tvError = (TextView) findViewById(R.id.tv_error);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initFunctionality(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadData();
        }
    }

    private void initListener() {
        favRecipeAdapter.setItemClickListener(new FavRecipeAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                resetSelect(position);
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

            String savedTitle = AppPreference.getInstance(FavRecipeActivity.this).getString(PrefKey.TITLE);
            for(RecipeModel recipeModel : dataList) {
                if(savedTitle != null && savedTitle.equals(recipeModel.getName())) {
                    recipeModel.setSelected(true);
                }
                recipeModels.add(recipeModel);
            }
            favRecipeAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    private void resetSelect(int index) {
        for (RecipeModel recipeModel : recipeModels) {
            recipeModel.setSelected(false);
        }
        recipeModels.get(index).setSelected(true);
        favRecipeAdapter.notifyDataSetChanged();

        AppPreference.getInstance(FavRecipeActivity.this).setString(PrefKey.TITLE, recipeModels.get(index).getName());

        ArrayList<IngredientsModel> ingredientsModels = recipeModels.get(index).getIngredients();
        StringBuilder description = new StringBuilder();
        for (IngredientsModel ingredientsModel : ingredientsModels) {
            description.append("      " + getString(R.string.bullet) + "  " + ingredientsModel.getQuantity() + ", " + ingredientsModel.getMeasure() + ", " + ingredientsModel.getIngredient() + "\n");
        }
        AppPreference.getInstance(FavRecipeActivity.this).setString(PrefKey.DESCRIPTION, description.toString());
        updateWidget();
    }

    private void updateWidget() {
        Intent intent = new Intent(this, RecipeWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
