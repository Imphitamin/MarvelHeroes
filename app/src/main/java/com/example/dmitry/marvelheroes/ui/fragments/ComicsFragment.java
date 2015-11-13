package com.example.dmitry.marvelheroes.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.ui.adapters.ComicsListAdapter;
import com.example.dmitry.marvelheroes.ui.interfaces.OnComicsRecycleViewScrollListener;

/**
 * Created by Dmitry on 14.10.2015.
 */

public class ComicsFragment extends Fragment {

    private RecyclerView recyclerViewComics;
    private ComicsListAdapter comicsListAdapter;
    private Context CONTEXT;
    private String query;

    public ComicsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CONTEXT = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            query = this.getArguments().getString("query");
        } catch (NullPointerException e) {
            query = null;
        }
        recyclerViewComics = (RecyclerView) inflater.inflate(R.layout.fragment_comics, container, false);
        return recyclerViewComics;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComicsList();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void initComicsList() {
        GridLayoutManager glm = new GridLayoutManager(CONTEXT, 2);
        comicsListAdapter = new ComicsListAdapter(CONTEXT);
        recyclerViewComics.setLayoutManager(glm);
        recyclerViewComics.setAdapter(comicsListAdapter);
        recyclerViewComics.setOnScrollListener(new OnComicsRecycleViewScrollListener(glm) {
            @Override
            public void onLoadMore() {

                if (query != null) {
                    comicsListAdapter.requestForParticularComics(query);
                } else {
                    comicsListAdapter.requestForMoreComics();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (query != null) {
            comicsListAdapter.requestForParticularComics(query);
        } else {
            comicsListAdapter.requestForMoreComics();
        }
    }
}
