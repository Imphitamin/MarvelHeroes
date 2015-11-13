package com.example.dmitry.marvelheroes.ui.interfaces;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Dmitry on 14.10.2015.
 */

public abstract class OnComicsRecycleViewScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = OnComicsRecycleViewScrollListener.class.getSimpleName();

    private int previousTotal = 2;
    private boolean loading = true;
    private final static int VISIBLE_THRESHOLD = 8;
    int lastVisibleItem, totalItemCount;


    private LinearLayoutManager mLinearLayoutManager;

    public OnComicsRecycleViewScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        totalItemCount = recyclerView.getAdapter().getItemCount();
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD))) {
            loading = true;
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
