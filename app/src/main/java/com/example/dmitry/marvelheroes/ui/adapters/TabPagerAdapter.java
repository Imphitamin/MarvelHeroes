package com.example.dmitry.marvelheroes.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dmitry.marvelheroes.ui.CharacterDetailsActivity;
import com.example.dmitry.marvelheroes.ui.fragments.CharacterDetailsComicsFragment;
import com.example.dmitry.marvelheroes.ui.fragments.CharacterDetailsFragment;

/**
 * Created by Dmitry on 09.11.2015.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;

    public TabPagerAdapter(FragmentManager fragmentManager, int numberOfTabs) {
        super(fragmentManager);
        this.mNumberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return new CharacterDetailsFragment();
            case 1:
                return new CharacterDetailsComicsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }

}
