package com.codelab.bakingtime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.DetailsPagerAdapter;
import com.codelab.bakingtime.adapter.StepAdapter;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.utility.ActivityUtils;

import java.util.ArrayList;


public class RecipeStepFragment extends Fragment {

    private ArrayList<StepsModel> arrayList;
    private StepAdapter stepAdapter;
    private ViewPager viewPager;
    private boolean twoPan;

    private String placeholder;
    private String bullet;

    public RecipeStepFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        arrayList = new ArrayList<>();
        stepAdapter = new StepAdapter(arrayList);
        RecyclerView rvSteps = (RecyclerView) rootView.findViewById(R.id.rv_steps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSteps.setLayoutManager(layoutManager);
        rvSteps.setAdapter(stepAdapter);

        placeholder = getActivity().getString(R.string.recipe_ingredients);
        bullet = getActivity().getString(R.string.bullet);


        stepAdapter.setItemClickListener(new StepAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (twoPan) {
                    selectItem(position);
                    viewPager.setCurrentItem(position, true);
                } else {
                    ActivityUtils.getInstance().invokeStepDetailsPage(getActivity(), arrayList, position);
                }
            }
        });

        loadData();

        return rootView;
    }

    private void loadData() {


        ArrayList<StepsModel> finalList = new ArrayList<>();
        ArrayList<IngredientsModel> ingredientsModels = null;
        ArrayList<StepsModel> stepsModels = null;

        Bundle bundle = getActivity().getIntent().getExtras();

        if (bundle != null && bundle.containsKey(Constants.KEY_INGREDIENTS)) {
            ingredientsModels = bundle.getParcelableArrayList(Constants.KEY_INGREDIENTS);
        }

        if (bundle != null && bundle.containsKey(Constants.KEY_STEPS)) {
            stepsModels = bundle.getParcelableArrayList(Constants.KEY_STEPS);
        }

        StringBuilder description = new StringBuilder();
        if(ingredientsModels != null) {
            for (IngredientsModel ingredientsModel : ingredientsModels) {
                description.append("      " + bullet + "  " + ingredientsModel.getQuantity() + ", " + ingredientsModel.getMeasure() + ", " + ingredientsModel.getIngredient() + "\n");
            }
            StepsModel stepsModel = new StepsModel(placeholder, description.toString());
            if(twoPan) {
                stepsModel.setSelected(true);
            }
            finalList.add(stepsModel);
        }

        if(stepsModels != null && !stepsModels.isEmpty()) {
            finalList.addAll(stepsModels);
        }

        loadListData(finalList);

        if(twoPan && viewPager != null) {
            DetailsPagerAdapter detailsPagerAdapter = new DetailsPagerAdapter(getActivity().getSupportFragmentManager(), arrayList);
            viewPager.setAdapter(detailsPagerAdapter);
        }

    }


    public void setViewPager(ViewPager viewPager, boolean twoPan) {
        this.viewPager = viewPager;
        this.twoPan = twoPan;
    }

    public void selectItem(int position) {
        for (StepsModel stepsModel : arrayList) {
            stepsModel.setSelected(false);
        }
        arrayList.get(position).setSelected(true);
        stepAdapter.notifyDataSetChanged();
    }


    private void loadListData(ArrayList<StepsModel> stepsModels) {
        arrayList.clear();
        arrayList.addAll(stepsModels);
        stepAdapter.notifyDataSetChanged();
    }

}
