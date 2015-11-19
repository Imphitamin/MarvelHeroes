package com.example.dmitry.marvelheroes.rest;

import com.example.dmitry.marvelheroes.rest.responseModels.CharacterDetailsResponse;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterListResponse;
import com.example.dmitry.marvelheroes.rest.responseModels.ComicsListResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Dmitry on 13.10.2015.
 */

public interface MarvelApiService {

    @GET(Constants.HEROES_URL)
    void requestHeroesList(@Query(Constants.LIMIT_PARAM) int limit,
                           @Query(Constants.OFFSET_PARAM) int offset,
                           @Query(Constants.API_KEY_PARAM) String apiKey,
                           @Query(Constants.TS_PARAM) long ts,
                           @Query(Constants.HASH_PARAM) String hash,
                           Callback<CharacterListResponse> callback);

    @GET(Constants.HERO_DETAIL_URL)
    void requestHeroDetails(
            @Path("id")
            String characterId,
            @Query(Constants.API_KEY_PARAM) String apiKey,
            @Query(Constants.TS_PARAM) long ts,
            @Query(Constants.HASH_PARAM) String hash,
            Callback<CharacterDetailsResponse> callback);

    @GET(Constants.HERO_COMICS_URL)
    void requestHeroComicsList(
            @Path("id") String characterId,
            @Query(Constants.LIMIT_PARAM) int limit,
            @Query(Constants.OFFSET_PARAM) int offset,
            @Query(Constants.API_KEY_PARAM) String apiKey,
            @Query(Constants.TS_PARAM) long ts,
            @Query(Constants.HASH_PARAM) String hash,
            Callback<ComicsListResponse> callback);

    @GET(Constants.COMICS_URL)
    void requestComicsList(@Query(Constants.LIMIT_PARAM) int limit,
                                  @Query(Constants.OFFSET_PARAM) int offset,
                                  @Query(Constants.API_KEY_PARAM) String apiKey,
                                  @Query(Constants.TS_PARAM) long ts,
                                  @Query(Constants.HASH_PARAM) String hash,
                           Callback<ComicsListResponse> callback);

    @GET(Constants.COMICS_URL)
    void requestComicsListByTitile(@Query(Constants.LIMIT_PARAM) int limit,
                                   @Query(Constants.OFFSET_PARAM) int offset,
                                   @Query(Constants.COMIC_TITLE_KEY) String title,
                                   @Query(Constants.API_KEY_PARAM) String apiKey,
                                   @Query(Constants.TS_PARAM) long ts,
                                   @Query(Constants.HASH_PARAM) String hash,
                                   Callback<ComicsListResponse> callback);
}
