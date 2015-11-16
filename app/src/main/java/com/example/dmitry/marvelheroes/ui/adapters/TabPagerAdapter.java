package com.example.dmitry.marvelheroes.ui.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dmitry.marvelheroes.ui.fragments.CharacterDetailsComicsFragment;
import com.example.dmitry.marvelheroes.ui.fragments.CharacterDetailsFragment;

/**
 * Created by Dmitry on 09.11.2015.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;
    Parcelable mCharacterData;

    public TabPagerAdapter(FragmentManager fragmentManager, int numberOfTabs, Parcelable characterData) { // TODO: добавить третий параметр
        super(fragmentManager);
        this.mNumberOfTabs = numberOfTabs;
        this.mCharacterData = characterData;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return CharacterDetailsFragment.newInstance(mCharacterData);
            case 1:
                return CharacterDetailsComicsFragment.newInstance(mCharacterData);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }

}
