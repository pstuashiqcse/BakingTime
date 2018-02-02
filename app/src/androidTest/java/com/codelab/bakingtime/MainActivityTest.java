package com.codelab.bakingtime;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codelab.bakingtime.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String RIGHT_TEXT = "Nutella Pie";
    private static final String WRONG_TEXT = "Nutella Pie1";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkApiData() {

        int count  = getRVcount();
        if(count > 0) {
            Log.e("Test Output", "No data, loading data from network....");
        }

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int count1  = getRVcount();
        if(count1 > 0) {
            Log.e("Test Output", count1+" data loaded");

            Espresso.onView(withRecyclerView(R.id.rv_recipe).atPosition(0))
                    .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(RIGHT_TEXT))));
        }

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    private int getRVcount(){
        RecyclerView recyclerView = (RecyclerView) mActivityTestRule.getActivity().findViewById(R.id.rv_recipe);
        return recyclerView.getAdapter().getItemCount();
    }

}
