package com.example.dmitry.marvelheroes.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.Counter;
import com.example.dmitry.marvelheroes.item.NavDrawItem;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.ui.adapters.NavigationDrawerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class NavigationDrawerFragment extends Fragment {

    public Context CONTEXT;

    private List<NavDrawItem> NavDrawItems = new ArrayList<>();
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    public ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private SharedPreferences preferences;

    Counter counter;
    NavigationDrawerAdapter navigationDrawerAdapter;


    public NavigationDrawerFragment() {}

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CONTEXT = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        counter = new Counter(CONTEXT);

        try {
            mCallbacks = (NavigationDrawerCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks method.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCallbacks.onNavigationDrawerItemSelected(Constants.CHARACTERS);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setFitsSystemWindows(true);
        return mDrawerListView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int chTotal = preferences.getInt(Constants.CHARACTERS_IN_TOTAL, 0);
        int cmTotal = preferences.getInt(Constants.COMICS_IN_TOTAL, 0);
        navigationDrawerAdapter = new NavigationDrawerAdapter(getActivity(), loadArrayOptions(chTotal, cmTotal));

        mDrawerListView.setAdapter(navigationDrawerAdapter);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
    }
    
    public List<NavDrawItem> loadArrayOptions(int charactersTotal, int comicsTotal) {
        counter.requestCharactersCount();
        counter.requestComicsCount();
        NavDrawItems.clear();
        NavDrawItems.add(new NavDrawItem(R.mipmap.ic_character, R.string.title_selection1, Constants.CHARACTERS, charactersTotal));
        NavDrawItems.add(new NavDrawItem(R.mipmap.ic_comics, R.string.title_selection2, Constants.COMICS, comicsTotal));
        return NavDrawItems;
    }

    public void updateMenuCounter(int key, @IntRange(from = 0) int value) {
        for (int position = 0, size = NavDrawItems.size(); position < size; position++) {
            NavDrawItem navDrawItem = NavDrawItems.get(position);
            if (navDrawItem.getIdKey() == key) {
                navDrawItem.setTotal(value);
            }
        }
        navigationDrawerAdapter.notifyDataSetChanged();
    }
    
    public void selectItem(int position) {
        mCurrentSelectedPosition = position;
        mCallbacks.onNavigationDrawerItemSelected(NavDrawItems.get(position).getIdKey());
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }
    
    public void setUp(View mFragmentContainerView, DrawerLayout drawerLayout, Toolbar toolbar) {
        this.mFragmentContainerView = mFragmentContainerView;
        this.mDrawerLayout = drawerLayout;
        
        enableHomeButton();
        
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override 
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().supportInvalidateOptionsMenu();
            }
            
            @Override 
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().supportInvalidateOptionsMenu();


            }
        };
        
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    
    private void enableHomeButton() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    
    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }
    
    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
        mDrawerToggle.onConfigurationChanged(newConfiguration);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
