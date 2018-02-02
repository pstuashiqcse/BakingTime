package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.StepAdapter;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.fragment.RecipeDetailsFragment;
import com.codelab.bakingtime.fragment.RecipeStepFragment;
import com.codelab.bakingtime.utility.ActivityUtils;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    private RecipeStepFragment recipeStepFragment;
    private ArrayList<IngredientsModel> ingredientsModels;
    private ArrayList<StepsModel> stepsModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();

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

    private void initView() {
        setContentView(R.layout.activity_recipe_step);

        recipeStepFragment = new RecipeStepFragment();
        if (findViewById(R.id.details_pane) != null) {
            // two pane
            recipeStepFragment.setIsTwoPan(true);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_INGREDIENTS, ingredientsModels);
        bundle.putParcelableArrayList(Constants.KEY_STEPS, stepsModels);
        recipeStepFragment.setArguments(bundle);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("lisFragment");
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, recipeStepFragment, "lisFragment").commit();

    }


}
