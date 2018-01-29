package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.DetailsPagerAdapter;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<StepsModel> stepsModels;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
        initFunctionality();

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey(Constants.KEY_STEPS)) {
            stepsModels = bundle.getParcelableArrayList(Constants.KEY_STEPS);
        }

        if (bundle != null && bundle.containsKey("index")) {
            index = bundle.getInt("index");
        }
    }

    private void initView() {
        setContentView(R.layout.fragment_details_pager);
        mViewPager = findViewById(R.id.pager);
    }

    private void initFunctionality() {
        DetailsPagerAdapter detailsPagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager(), stepsModels);
        mViewPager.setAdapter(detailsPagerAdapter);
        mViewPager.setCurrentItem(index);
    }

}
