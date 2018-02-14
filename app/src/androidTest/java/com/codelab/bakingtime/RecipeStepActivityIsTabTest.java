package com.codelab.bakingtime;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codelab.bakingtime.activity.MainActivity;
import com.codelab.bakingtime.activity.RecipeStepActivity;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeStepActivityIsTabTest {

    @Rule
    public ActivityTestRule<RecipeStepActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeStepActivity.class);

    @Test
    public void checkTab() {
        Espresso.onView(ViewMatchers.withId(R.id.details_pane))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        /**
         * If test pass then the app is running in a tab
         * If test failed then the app is running in a mobile
         */

    }

}
