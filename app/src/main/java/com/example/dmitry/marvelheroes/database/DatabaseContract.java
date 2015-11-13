package com.example.dmitry.marvelheroes.database;

import android.provider.BaseColumns;

/**
 * Created by Dmitry on 10.10.2015.
 */

public final class DatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "marvel.db";
        public static final String CHARACTERS_COLUMN_ID = "id";
        public static final String CHARACTERS_COLUMN_ARRAY = "characters";
        public static final String CHARACTERS_COLUMN_THUMBNAIL = "thumbnail";
        public static final String CHARACTERS_COLUMN_NAME = "name";
        public static final String CHARACTERS_COLUMN_DESCRIPTION = "description";
        public static final String CHARACTERS_COLUMN_COMICS = "comics";
        public static final String CHARACTERS_COLUMN_SERIES = "series";
        public static final String CHARACTERS_COLUMN_STORIES = "stories";
        public static final String CHARACTERS_COLUMN_EVENTS = "events";
    }
}
