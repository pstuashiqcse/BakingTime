package com.codelab.bakingtime.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.activity.RecipeStepActivity;
import com.codelab.bakingtime.adapter.StepAdapter;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.utility.ActivityUtils;

import java.util.ArrayList;


public class RecipeStepFragment extends Fragment {

    private ArrayList<StepsModel> arrayList;
    private StepAdapter stepAdapter;

    private String placeholder;
    private String bullet;
    private boolean twoPan;

    private int backupPosition = 0;
    private static final String POSITION_KEY = "position";

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

        twoPan = ((RecipeStepActivity)getActivity()).isTwoPan();

        stepAdapter.setItemClickListener(new StepAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (twoPan) {
                    backupPosition = position;
                    selectItem(position);
                    setFragment(position);
                } else {
                    ActivityUtils.getInstance().invokeStepDetailsPage(getActivity(), arrayList, position);
                }
            }
        });

        placeholder = getActivity().getString(R.string.recipe_ingredients);
        bullet = getActivity().getString(R.string.bullet);


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
        if (ingredientsModels != null) {
            for (IngredientsModel ingredientsModel : ingredientsModels) {
                description.append("      " + bullet + "  " + ingredientsModel.getQuantity() + ", " + ingredientsModel.getMeasure() + ", " + ingredientsModel.getIngredient() + "\n");
            }
            StepsModel stepsModel = new StepsModel(placeholder, description.toString());
            if (twoPan) {
                stepsModel.setSelected(true);
            }
            finalList.add(stepsModel);
        }

        if (stepsModels != null && !stepsModels.isEmpty()) {
            finalList.addAll(stepsModels);
        }

        loadListData(finalList);

        if (twoPan) {
            selectItem(backupPosition);
            setFragment(backupPosition);
        }
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

    public void setFragment(int position) {

        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("detailsFragment");
        if (fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        Fragment detailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.KEY_STEPS, arrayList);
        args.putInt("index", position);
        detailsFragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, detailsFragment, "detailsFragment").commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_KEY, backupPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            backupPosition = savedInstanceState.getInt(POSITION_KEY);
        }
    }
}
