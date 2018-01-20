package com.codelab.bakingtime.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.fragment.MainActivityFragment;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.layout_container, mainActivityFragment).commit();

        if (findViewById(R.id.details_pane) != null) {
            mTwoPane = true;
        }

    }

}
