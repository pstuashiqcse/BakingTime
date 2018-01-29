package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.DetailsPagerAdapter;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.fragment.RecipeStepFragment;
import com.codelab.bakingtime.listeners.StepSelector;
import com.codelab.bakingtime.utility.ActivityUtils;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    private boolean mTwoPane = false;
    private ArrayList<IngredientsModel> ingredientsModels;
    private ArrayList<StepsModel> stepsModels;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey(Constants.KEY_INGREDIENTS)) {
            ingredientsModels = bundle.getParcelableArrayList(Constants.KEY_INGREDIENTS);
        }

        if(bundle != null && bundle.containsKey(Constants.KEY_STEPS)) {
            stepsModels = bundle.getParcelableArrayList(Constants.KEY_STEPS);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_recipe_step);
        if (findViewById(R.id.details_pane) != null) {
            mTwoPane = true;
        }

        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_INGREDIENTS, ingredientsModels);
        bundle.putParcelableArrayList(Constants.KEY_STEPS, stepsModels);
        recipeStepFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.layout_container, recipeStepFragment).commit();

        if(mTwoPane) {
            mViewPager = findViewById(R.id.pager);
            DetailsPagerAdapter detailsPagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager(), stepsModels);
            mViewPager.setAdapter(detailsPagerAdapter);
        }

        recipeStepFragment.setStepSelector(new StepSelector() {
            @Override
            public void onSelected(int index) {
                if(mTwoPane) {
                    mViewPager.setCurrentItem(index, true);
                } else {
                    ActivityUtils.getInstance().invokeStepDetailsPage(RecipeStepActivity.this, stepsModels, index);
                }
            }
        });


    }

}
