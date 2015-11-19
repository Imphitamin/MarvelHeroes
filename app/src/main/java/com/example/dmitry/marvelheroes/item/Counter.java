package com.example.dmitry.marvelheroes.item;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.MarvelApiClient;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterListResponse;
import com.example.dmitry.marvelheroes.rest.responseModels.ComicsListResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Denisov-DV on 18.11.2015.
 */

public class Counter {

    private static final String TAG = "Counter";
    private Context context;
    private SharedPreferences.Editor editor;

    public Counter(Context context) {
        this.context = context;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.commit();
    }

    public void requestCharactersCount() {
        MarvelApiClient.getInstance(context)
                .requestHeroesList(Constants.CHARACTERS_LIMIT, Constants.CHARACTERS_INIT_COUNT, new Callback<CharacterListResponse>() {
                    @Override
                    public void success(CharacterListResponse characterListResponse, Response response) {
                        editor.putInt("totalCharacters", characterListResponse.getTotal());
                        editor.commit();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(TAG, "Failed to get Characters response: ", error);
                    }
                });
    }

    public void requestComicsCount() {
        MarvelApiClient.getInstance(context)
                .requestComicsList(Constants.CHARACTERS_LIMIT, Constants.COMICS_INIT_COUNT, new Callback<ComicsListResponse>() {
                    @Override
                    public void success(ComicsListResponse comicsListResponse, Response response) {
                        editor.putInt("totalComics", comicsListResponse.getTotal());
                        editor.commit();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(TAG, "Failed to get Comics response: ", error);
                    }
                });
    }
}
