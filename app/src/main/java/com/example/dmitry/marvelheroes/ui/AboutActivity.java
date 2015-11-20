package com.example.dmitry.marvelheroes.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.About;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.ui.adapters.AboutActivityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Denisov-DV on 20.11.2015.
 */

public class AboutActivity extends AppCompatActivity {

    @InjectView(R.id.lv_info_about)
    RecyclerView listViewInfo;

    List<About> aboutList = new ArrayList<>();
    AboutActivityAdapter adapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        adapter = new AboutActivityAdapter(this);

        setUpActionBar();

        for (int i = 1, size = Constants.CHARACTERS_LIMIT+1; i < size; i++) {
            aboutList.add(new About("Text"+i));
        }

        adapter.addItemCollection(aboutList);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listViewInfo.setLayoutManager(llm);
        listViewInfo.setAdapter(adapter);
    }

    private void setUpActionBar() {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.activity_about_name);
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    public void startSnackBar(View view) {

        final Snackbar snackbar = Snackbar.make(view, R.string.snackbar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(getString(R.string.snackbar_action_text), new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                switch (event) {
                    case DISMISS_EVENT_SWIPE:
                        Toast.makeText(AboutActivity.this, R.string.snackbar_toast_text_swipe, Toast.LENGTH_LONG).show();
                        break;
                    case DISMISS_EVENT_ACTION:
                        Toast.makeText(AboutActivity.this, R.string.snackbar_toast_text_manual, Toast.LENGTH_LONG).show();
                        break;
                    case DISMISS_EVENT_TIMEOUT:
                        Toast.makeText(AboutActivity.this, R.string.snackbar_toast_text_timeout, Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onShown(Snackbar snackbar) {
                Toast.makeText(AboutActivity.this, R.string.snackbar_toast_text_shown, Toast.LENGTH_SHORT).show();
            }
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.cardview_light_background))
                .setDuration(Snackbar.LENGTH_LONG)
                .show();
    }
}
