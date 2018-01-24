package com.codelab.bakingtime.utility;

import android.app.Activity;
import android.content.Intent;

import com.codelab.bakingtime.activity.RecipeStepActivity;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;

import java.util.ArrayList;


public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;
    public static ActivityUtils getInstance() {
        if(sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }


    public void invokeRecipeStepPage(Activity activity, ArrayList<IngredientsModel> ingredientsModels, ArrayList<StepsModel> stepsModels) {
        Intent intent = new Intent(activity, RecipeStepActivity.class);
        intent.putParcelableArrayListExtra(Constants.KEY_INGREDIENTS, ingredientsModels);
        intent.putParcelableArrayListExtra(Constants.KEY_STEPS, stepsModels);
        activity.startActivity(intent);
    }



}
