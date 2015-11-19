package com.example.dmitry.marvelheroes.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.marvelheroes.R;
import com.example.dmitry.marvelheroes.item.Comic;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.MarvelApiClient;
import com.example.dmitry.marvelheroes.rest.responseModels.ComicsListResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dmitry on 14.10.2015.
 */

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsListAdapter.ComicViewHolder> {

    public static final int VIEW_PROGRESS = 0;
    public static final int VIEW_COMIC = 1;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Comic> comics;

    public ComicsListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        comics = new ArrayList<>();
        comics.add(null);
        comics.add(null);
    }

    @Override
    public int getItemViewType(int position) {
        return comics.get(position) != null ? VIEW_COMIC : VIEW_PROGRESS;
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_COMIC) {
            return new ComicViewHolder(layoutInflater.inflate(R.layout.item_comic, parent, false), viewType);
        } else {
            return new ComicViewHolder(layoutInflater.inflate(R.layout.item_comic_progressbar, parent, false), viewType);
        }
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        if (position < getItemCount() - 2) {
            holder.setComicData(comics.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public void requestForMoreComics() {

        MarvelApiClient.getInstance(context)
                .requestComicsList(Constants.CHARACTERS_LIMIT, getComicsItemsCount(), new Callback<ComicsListResponse>() {
                    @Override
                    public void success(ComicsListResponse comicsListResponse, Response response) {
                        updateList(comicsListResponse.getComics());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
    }

    public void requestForParticularComics(String query) {

            MarvelApiClient.getInstance(context)
                    .requestComicsListByTitle(Constants.CHARACTERS_LIMIT, getComicsItemsCount(), query, new Callback<ComicsListResponse>() {
                        @Override
                        public void success(ComicsListResponse comicsListResponse, Response response) {
                            updateList(comicsListResponse.getComics());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            error.printStackTrace();
                        }
                    });
        }

    public void requestForComicsByCharacterId(String id) {

        MarvelApiClient.getInstance(context)
                .requestHeroComicsList(id, Constants.CHARACTERS_LIMIT, getComicsItemsCount(), new Callback<ComicsListResponse>() {
                    @Override
                    public void success(ComicsListResponse comicsListResponse, Response response) {
                        updateList(comicsListResponse.getComics());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
    }

    public void updateList(List<Comic> comics) {
        final int startPosition = this.comics.size();
        this.comics.addAll(getComicsItemsCount(), comics);
        notifyItemRangeInserted(startPosition, comics.size());
    }

    private int getComicsItemsCount() {
        return comics.size() - 2;
    }

    static class ComicViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.img_comic)
        SimpleDraweeView imgComic;

        @InjectView(R.id.textView_ComicTitle)
        TextView textTitle;

        @InjectView(R.id.textView_ComicIssue)
        TextView textIssues;

        @InjectView(R.id.textView_ComicPages)
        TextView textPages;

        public ComicViewHolder(View itemView, int type) {
            super(itemView);

            if (type == VIEW_COMIC) {
                ButterKnife.inject(this, itemView);
            }
        }

        public void setComicData(Comic comic) {
            Uri urlImage = comic.getUrlImage();
            if (!urlImage.equals(Uri.EMPTY)) {
                imgComic.setImageURI(urlImage);
            }

            textTitle.setText(comic.getTitle());
            textIssues.setText("Issue #" + comic.getIssues());
            textPages.setText("Page - " + comic.getPages());
        }
    }
}
