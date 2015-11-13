package com.example.dmitry.marvelheroes.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.ui.adapters.TabPagerAdapter;
import com.example.dmitry.marvelheroes.ui.fragments.CharacterDetailsComicsFragment;
import com.example.dmitry.marvelheroes.ui.fragments.CharacterDetailsFragment;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class CharacterDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
            fragmentSwitcher(savedInstanceState.getInt(CHARACTER_DETAILS_FRAGMENT_TAG), savedInstanceState);
        }*/

        setContentView(R.layout.character_details_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int colorNormal = getResources().getColor(R.color.primary);
        int colorSelected = getResources().getColor(R.color.text);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Description"));
        tabLayout.addTab(tabLayout.newTab().setText("Related Comics"));
        tabLayout.setTabTextColors(colorNormal, colorSelected);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /*private void fragmentSwitcher(int fragmentId, Bundle args) {
        switch (fragmentId) {
            case CHARACTER_DETAILS_FRAGMENT:
                setFragment(CharacterDetailsFragment.getInstance(args), CHARACTER_DETAILS_FRAGMENT_TAG);
                break;
        }
    }

    private void setFragment(Fragment fragment, String tag) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailContainer, fragment, tag)
                .commit();
    }*/

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
