package com.example.dmitry.marvelheroes.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.dmitry.marvelheroes.rest.MarvelApiClient;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterListResponse;
import com.example.dmitry.marvelheroes.ui.adapters.ComicsListAdapter;
import com.example.dmitry.marvelheroes.ui.adapters.NavigationDrawerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class NavigationDrawerFragment extends Fragment {

    private static final String TAG = "NavigationDrawerFrag";
    public Context CONTEXT;

    private List<NavDrawItem> NavDrawItems = new ArrayList<>();
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    Counter counter;


    public NavigationDrawerFragment() {}

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CONTEXT = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.commit();
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

        mDrawerListView.setAdapter(new NavigationDrawerAdapter(getActivity(), loadArrayOptions()));
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
                /*mDrawerListView.setAdapter(new NavigationDrawerAdapter(getActivity(), loadArrayOptionsWithNotificator(position)));
                mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectItem(position);
                    }
                });
                mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);*/
            }
        });
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
    }
    
    public List<NavDrawItem> loadArrayOptions() {
        counter.requestCharactersCount();
        counter.requestComicsCount();
        NavDrawItems.add(new NavDrawItem(R.mipmap.ic_character, R.string.title_selection1, Constants.CHARACTERS, 0));
        NavDrawItems.add(new NavDrawItem(R.mipmap.ic_comics, R.string.title_selection2, Constants.COMICS, preferences.getInt(Constants.COMICS_IN_TOTAL, 0)));
        return NavDrawItems;
    }

    /*public List <NavDrawItem> loadArrayOptionsWithNotificator(int position) {
        if (position == Constants.CHARACTERS) {
            counter.requestCharactersCount();
            NavDrawItems.set(position, new NavDrawItem(R.mipmap.ic_character, R.string.title_selection1, Constants.CHARACTERS, preferences.getInt("totalCharacters", 0)));
            //NavDrawItems.get(Constants.CHARACTERS).setIdTotal(counter.getCharactersCount());
        } else {
            counter.requestComicsCount();
            NavDrawItems.set(position, new NavDrawItem(R.mipmap.ic_comics, R.string.title_selection2, Constants.COMICS, preferences.getInt("totalComics", 0)));
            //NavDrawItems.get(Constants.COMICS).setIdTotal(counter.getComicsCounter());
        }
        return NavDrawItems;
    }*/
    
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
