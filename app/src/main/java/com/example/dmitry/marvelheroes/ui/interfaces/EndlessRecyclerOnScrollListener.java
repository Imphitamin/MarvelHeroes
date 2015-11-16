package com.example.dmitry.marvelheroes.ui.interfaces;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dmitry.marvelheroes.ui.adapters.CharactersListAdapter;

/**
 * Created by Dmitry on 14.10.2015.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // Общее число загруженных предметов в "наборе"
    private boolean loading = true; // Правда - если ждем последние загружаемые данные
    private int visibleThreshols = 12; // Минимальное количество ячеек/предметов ниже текущей позиции в скроллере перед тем, как загружать еще
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int currentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = mLinearLayoutManager.getChildCount();
        totalItemCount = ((CharactersListAdapter) recyclerView.getAdapter()).getCharactersItemsCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshols))) {
            loading = true;
            currentPage++;
            onLoadMore(currentPage);
        }
    }

    public abstract void onLoadMore(int currentPage);
}
