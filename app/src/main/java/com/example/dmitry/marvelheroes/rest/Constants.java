package com.example.dmitry.marvelheroes.rest;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class Constants {

    // Мои ключи
    public static final String API_PUBLIC_KEY = "3100c35abc567e749449fbd38751a5e9";
    public static final String API_PRIVATE_KEY = "ef48f8828c664b78c14b1035d6f047f091f7fb95";

    // Константы для каждого из параметров в запросах URL
    public static final String API_KEY_PARAM = "apikey";
    public static final String LIMIT_PARAM = "limit";
    public static final String OFFSET_PARAM = "offset";
    public static final String TS_PARAM = "ts";
    public static final String HASH_PARAM = "hash";
    public static final int TIMER_NUMBER = 1250;
    public static final int CHARACTERS_LIMIT = 25;
    public static final int CHARACTERS_INIT_COUNT = 0;
    public static final int CHARACTERS = 111;
    public static final int COMICS = 222;

    // Константы для каждого из объектов, найденных в JSON ответе(response)
    public static final String CODE_KEY = "code";
    public static final String STATUS_KEY = "status";
    public static final String DATA_KEY = "data";
    public static final String OFFSET_KEY = "offset";
    public static final String RESULTS_KEY = "results";
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String DESCRIPTION_KEY = "description";
    public static final String TUMBNAIL_KEY = "thumbnail";
    public static final String PATH_KEY = "path";
    public static final String EXTENSION_KEY = "extension";
    public static final String COMICS_KEY = "comics";
    public static final String SERIES_KEY = "series";
    public static final String STORIES_KEY = "stories";
    public static final String AVAILABLE_KEY = "available";
    public static final String COMIC_TITLE_KEY = "title";
    public static final String COMIC_URLS_KEY = "urls";
    public static final String COMIC_URL_KEY = "url";
    public static final String COMIC_ISSUE_KEY = "issueNumber";
    public static final String COMIC_PAGES_KEY = "pageCount";

    // Константы для каждого героя(персонажа)
    public static final String HERO_URL_IMAGE = "heroImage";
    public static final String HERO_NAME = "heroName";
    public static final String HERO_DESC = "heroDesc";

    // URL для запросов
    public static final String MAIN_URL = "http://gateway.marvel.com:80/v1/public";
    public static final String HEROES_URL = "/characters";
    public static final String HERO_DETAIL_URL = "/characters/{id}";
    public static final String HERO_COMICS_URL = "/characters/{id}/comics";
    public static final String COMICS_URL = "/comics";

}
