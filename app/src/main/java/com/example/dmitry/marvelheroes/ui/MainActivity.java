package com.example.dmitry.marvelheroes.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.Counter;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.ui.adapters.NavigationDrawerAdapter;
import com.example.dmitry.marvelheroes.ui.fragments.CharactersFragment;
import com.example.dmitry.marvelheroes.ui.fragments.ComicsFragment;
import com.example.dmitry.marvelheroes.ui.fragments.NavigationDrawerFragment;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static Timer TIMER = new Timer();
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int mCurrentSelectedPosition = -1;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.editableText)
    EditText editableText;

    @InjectView(R.id.drawer_layout)
    DrawerLayout newDrawerLayout;

    @InjectView(R.id.navigation_drawer)
    View mFragmentViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.inject(this);
        Fresco.initialize(this);
        setSupportActionBar(toolbar);
        editableText.clearFocus();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        editor.commit();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(mFragmentViewContainer, newDrawerLayout, toolbar);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (mCurrentSelectedPosition != position) {
            mCurrentSelectedPosition = position;
            setNewTitle(mCurrentSelectedPosition);

            Fragment fragment = null;

            switch (mCurrentSelectedPosition) {
                case Constants.CHARACTERS:
                    fragment = new CharactersFragment();
                    break;
                case Constants.COMICS:
                    fragment = new ComicsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
        }
    }

    public void setNewTitle(int number) {
        String CharactersTitle = getString(R.string.title_selection1);
        String ComicsTitle = getString(R.string.title_selection2);

        switch (number) {
            case Constants.CHARACTERS:
                mTitle = CharactersTitle;
                MainActivity.this.setTitle(mTitle);
                break;
            case Constants.COMICS:
                mTitle = ComicsTitle;
                MainActivity.this.setTitle(mTitle);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.menu_search);
        final MenuItem cancelSearchItem = menu.findItem(R.id.search_cancel);
        cancelSearchItem.setVisible(false);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        if (mCurrentSelectedPosition == Constants.COMICS) {
            searchItem.setVisible(true);
        } else {
            searchItem.setVisible(false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                TIMER.cancel();
                Bundle bundle = new Bundle();
                bundle.putString("query", query);

                ComicsFragment comicsFragment = new ComicsFragment();
                comicsFragment.setArguments(bundle);

                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                MainActivity.this.setTitle(query);
                cancelSearchItem.setVisible(true);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, comicsFragment)
                        .commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                TIMER.cancel();
                TIMER = new Timer();

                if (!s.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("query", s);
                    final ComicsFragment comicsFragment = new ComicsFragment();
                    comicsFragment.setArguments(bundle);

                    TIMER.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.main_container, comicsFragment)
                                            .commit();
                                }
                            });
                        }
                    }, Constants.TIMER_NUMBER);
                } else {
                    cancelSearchItem.setVisible(false);
                    MainActivity.this.setTitle(R.string.title_selection2);
                    ComicsFragment comicsFragment = new ComicsFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, comicsFragment)
                            .commit();
                }
                return true;
            }
        });
        return true;
    }

    public void onClickCancelSearch(MenuItem item) {
        ComicsFragment comicsFragment = new ComicsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, comicsFragment)
                .commit();
        item.setVisible(false);
        MainActivity.this.setTitle(getResources().getString(R.string.title_selection2));
    }

    public void sendText(View view) {
        int charTotal = 0;
        int comTotal = 0;
        //int position = -1;
        Editable editable = editableText.getText();


        if (TextUtils.isEmpty(editable)) {
            mNavigationDrawerFragment.updateMenuCounter(Constants.COMICS, 0);
            mNavigationDrawerFragment.updateMenuCounter(Constants.CHARACTERS, 0);
        } else {
            charTotal = Integer.valueOf(String.valueOf(editable));
            comTotal = charTotal + 2;

            mNavigationDrawerFragment.updateMenuCounter(Constants.COMICS, comTotal);
            mNavigationDrawerFragment.updateMenuCounter(Constants.CHARACTERS, charTotal);
        }
        editableText.clearFocus();
    }
}
