package com.example.dmitry.marvelheroes.rest;

import android.content.Context;

import com.example.dmitry.marvelheroes.rest.deserializers.CharacterDetailsResponseDeserializer;
import com.example.dmitry.marvelheroes.rest.deserializers.CharacterListResponseDeserializer;
import com.example.dmitry.marvelheroes.rest.deserializers.ComicsListResponseDeserializer;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterDetailsResponse;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterListResponse;
import com.example.dmitry.marvelheroes.rest.responseModels.ComicsListResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class MarvelApiClient {

    private MarvelApiService apiService;
    private Context context;

    private static MarvelApiClient API_CLIENT;

    public static MarvelApiClient getInstance(Context context) {
        if (API_CLIENT == null) {
            API_CLIENT = new MarvelApiClient(context);
        }
        return API_CLIENT;
    }

    private MarvelApiClient(Context context) {
        this.context = context;

        // Строю Response-parser
        Gson gsonConfig = new GsonBuilder()
                .registerTypeAdapter(CharacterListResponse.class, new CharacterListResponseDeserializer())
                .registerTypeAdapter(CharacterDetailsResponse.class, new CharacterDetailsResponseDeserializer())
                .registerTypeAdapter(ComicsListResponse.class, new ComicsListResponseDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        // Адаптер Retrofit - для того, чтобы совершать запросы
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("MARVEL_TAG"))
                .setEndpoint(Constants.MAIN_URL)
                .setConverter(new GsonConverter(gsonConfig))
                .build();

        apiService = restAdapter.create(MarvelApiService.class);
    }

    // Метод, возвращающий интерфейс retrofit для вызова любого доступного метода
    private MarvelApiService getApiService() {
        return apiService;
    }

    // Запрос списка героев
    public void requestHeroesList(int limit, int offset, Callback<CharacterListResponse> callback) {
        Long ts = UtilMethods.generateTimeStamp();
        String hash = UtilMethods.generateHash(ts);

        getApiService().requestHeroesList(
                limit,
                offset,
                Constants.API_PUBLIC_KEY,
                ts,
                hash,
                callback);
    }

    public void requestHeroDetails(String characterId, Callback<CharacterDetailsResponse> callback) {
        Long ts = UtilMethods.generateTimeStamp();
        String hash = UtilMethods.generateHash(ts);

        getApiService().requestHeroDetails(
                characterId,
                Constants.API_PUBLIC_KEY,
                ts,
                hash,
                callback);
    }

    public void requestHeroComicsList(String characterId, int limit, int offset, Callback<ComicsListResponse> callback) {
        Long ts = UtilMethods.generateTimeStamp();
        String hash = UtilMethods.generateHash(ts);

        getApiService().requestHeroComicsList(
                characterId,
                limit,
                offset,
                Constants.API_PUBLIC_KEY,
                ts,
                hash,
                callback);
    }

    public void requestComicsList(int limit, int offset, Callback<ComicsListResponse> callback) {
        Long ts = UtilMethods.generateTimeStamp();
        String hash = UtilMethods.generateHash(ts);

        getApiService().requestComicsList(
                limit,
                offset,
                Constants.API_PUBLIC_KEY,
                ts,
                hash,
                callback);
    }
    public void requestComicsListByTitle(int limit, int offset, String title, Callback<ComicsListResponse> callback) {
        Long ts = UtilMethods.generateTimeStamp();
        String hash = UtilMethods.generateHash(ts);

        getApiService().requestComicsListByTitile(
                limit,
                offset,
                title,
                Constants.API_PUBLIC_KEY,
                ts,
                hash,
                callback);
    }
}
