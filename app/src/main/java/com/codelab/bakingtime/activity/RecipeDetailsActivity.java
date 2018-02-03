package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.api.models.StepsModel;
import com.codelab.bakingtime.data.constant.Constants;
import com.codelab.bakingtime.fragment.RecipeDetailsFragment;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    private ArrayList<StepsModel> stepsModels;
    private int index = 0;

    private MenuItem nextMenu, previousMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
        setFragment(0);

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
    }

    private void setFragment(int position) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("detailsFragment");
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

        Fragment detailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.KEY_STEPS, stepsModels);
        args.putInt("index", position);
        detailsFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.details_fragment, detailsFragment, "detailsFragment").commit();

    }

    private int handleFragment(boolean next) {

        if(nextMenu != null) {
            nextMenu.setVisible(true);
        }
        if(previousMenu != null) {
            previousMenu.setVisible(true);
        }

        int total = stepsModels.size() - 1;
        if (next) {
            if (index == total) {
                if(nextMenu != null) {
                    nextMenu.setVisible(false);
                }
                return total;
            } else {
                index++;
                return index;
            }
        } else {
            if (index == 0) {
                if(previousMenu != null) {
                    previousMenu.setVisible(false);
                }
                return 0;
            } else {
                index--;
                return index;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);

        nextMenu = menu.findItem(R.id.next);
        previousMenu = menu.findItem(R.id.previous);
        previousMenu.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.next:
                setFragment(handleFragment(true));
                return true;
            case R.id.previous:
                setFragment(handleFragment(false));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
