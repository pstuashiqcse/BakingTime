package com.codelab.bakingtime.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.fragment.RecipeDetailsFragment;

import java.util.ArrayList;


public class DetailsPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<StepsModel> stepsModels;

    public DetailsPagerAdapter(FragmentManager fm, ArrayList<StepsModel> stepsModels) {
        super(fm);
        this.stepsModels = stepsModels;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment postListFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.KEY_STEPS, stepsModels);
        args.putInt("index", i);
        postListFragment.setArguments(args);
        return postListFragment;

    }

    @Override
    public int getCount() {
        return stepsModels.size();
    }

}


