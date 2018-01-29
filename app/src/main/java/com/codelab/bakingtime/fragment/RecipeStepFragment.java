package com.codelab.bakingtime.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.adapter.DetailsPagerAdapter;
import com.codelab.bakingtime.adapter.RecipeAdapter;
import com.codelab.bakingtime.adapter.StepAdapter;
import com.codelab.bakingtime.api.http.ApiUtils;
import com.codelab.bakingtime.api.models.IngredientsModel;
import com.codelab.bakingtime.api.models.RecipeModel;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.listeners.StepSelector;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipeStepFragment extends Fragment {

    private ArrayList<StepsModel> arrayList;
    private StepAdapter stepAdapter;
    private RecyclerView rvSteps;

    private StepSelector stepSelector;

    private String placeholder;
    private String bullet;

    public RecipeStepFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        arrayList = new ArrayList<>();
        stepAdapter = new StepAdapter(getActivity(), arrayList);
        rvSteps = (RecyclerView) rootView.findViewById(R.id.rv_steps);
        rvSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSteps.setAdapter(stepAdapter);

        placeholder = getActivity().getString(R.string.recipe_ingredients);
        bullet = getActivity().getString(R.string.bullet);


        stepAdapter.setItemClickListener(new StepAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                stepSelector.onSelected(position);
            }
        });

        loadData();

        return rootView;
    }

    private void loadData() {

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
            arrayList.add(stepsModel);
        }


        if(stepsModels != null) {
            arrayList.addAll(stepsModels);
        }

        stepAdapter.notifyDataSetChanged();

    }

    public void setStepSelector(StepSelector stepSelector) {
        this.stepSelector = stepSelector;
    }

}
