package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.fragment.RecipeStepFragment;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    private boolean mTwoPane;
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

        if(bundle != null && bundle.containsKey(Constants.KEY_INGREDIENTS)) {
            ingredientsModels = bundle.getParcelableArrayList(Constants.KEY_INGREDIENTS);
        }

        if(bundle != null && bundle.containsKey(Constants.KEY_STEPS)) {
            stepsModels = bundle.getParcelableArrayList(Constants.KEY_STEPS);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_recipe_step);
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.layout_container, recipeStepFragment).commit();

        if (findViewById(R.id.details_pane) != null) {
            mTwoPane = true;
        }

    }

}
