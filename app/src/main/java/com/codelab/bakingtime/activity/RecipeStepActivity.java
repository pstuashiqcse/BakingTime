package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.fragment.RecipeDetailsFragment;
import com.codelab.bakingtime.fragment.RecipeStepFragment;

import java.util.ArrayList;

public class RecipeStepActivity extends AppCompatActivity {

    private ArrayList<StepsModel> finalStepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildDetailsList();
        initView(savedInstanceState);

    }

    private void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recipe_step);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.KEY_STEPS, finalStepList);
            recipeStepFragment.setArguments(bundle);

            setFragment(recipeStepFragment);

            if(isTwoPan()) {
                setDetailsFragment(0);
            }
        }
    }

    private void setFragment(Fragment recipeStepFragment) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("lisFragment");
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, recipeStepFragment, "lisFragment").commit();
    }

    public void setDetailsFragment(int position) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("detailsFragment");
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        Fragment detailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.KEY_STEPS, finalStepList);
        args.putInt("index", position);
        detailsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, detailsFragment, "detailsFragment").commit();
    }

    private void buildDetailsList() {

        String placeholder = getString(R.string.recipe_ingredients);
        String bullet = getString(R.string.bullet);

        finalStepList = new ArrayList<>();
        ArrayList<IngredientsModel> ingredientsModels = null;
        ArrayList<StepsModel> stepsModels = null;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(Constants.KEY_INGREDIENTS)) {
            ingredientsModels = bundle.getParcelableArrayList(Constants.KEY_INGREDIENTS);
        }
        if (bundle != null && bundle.containsKey(Constants.KEY_STEPS)) {
            stepsModels = bundle.getParcelableArrayList(Constants.KEY_STEPS);
        }

        StringBuilder description = new StringBuilder();
        if (ingredientsModels != null) {
            for (IngredientsModel ingredientsModel : ingredientsModels) {
                description.append("      " + bullet + "  " + ingredientsModel.getQuantity() + ", " + ingredientsModel.getMeasure() + ", " + ingredientsModel.getIngredient() + "\n");
            }
            StepsModel stepsModel = new StepsModel(placeholder, description.toString());
            if (isTwoPan()) {
                stepsModel.setSelected(true);
            }
            finalStepList.add(stepsModel);
        }
        if (stepsModels != null && !stepsModels.isEmpty()) {
            finalStepList.addAll(stepsModels);
        }
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
