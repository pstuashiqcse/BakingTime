package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.fragment.RecipeStepFragment;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    private ArrayList<IngredientsModel> ingredientsModels;
    private ArrayList<StepsModel> stepsModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView(savedInstanceState);

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey(Constants.KEY_INGREDIENTS)) {
            ingredientsModels = bundle.getParcelableArrayList(Constants.KEY_INGREDIENTS);
        }

        if (bundle != null && bundle.containsKey(Constants.KEY_STEPS)) {
            stepsModels = bundle.getParcelableArrayList(Constants.KEY_STEPS);
        }
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recipe_step);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.KEY_INGREDIENTS, ingredientsModels);
            bundle.putParcelableArrayList(Constants.KEY_STEPS, stepsModels);
            recipeStepFragment.setArguments(bundle);

            setFragment(recipeStepFragment);
        }
    }

    private void setFragment(Fragment recipeStepFragment) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("lisFragment");
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, recipeStepFragment, "lisFragment").commit();
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

    public boolean isTwoPan() {
        return findViewById(R.id.details_pane) != null;
    }
}
