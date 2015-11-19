package com.example.dmitry.marvelheroes.rest.deserializers;

import com.example.dmitry.marvelheroes.item.Comic;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.responseModels.ComicsListResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class ComicsListResponseDeserializer implements JsonDeserializer<ComicsListResponse> {

    @Override
    public ComicsListResponse deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) throws JsonParseException {
        // Десериализуем атрибуты первого уровня объекта JsonObject
        Gson gson = new Gson();
        ComicsListResponse response = gson.fromJson(json, ComicsListResponse.class);

        // Затем десериализуем все необходимые вложенные(nested) атрибуты
        JsonObject data = json.getAsJsonObject().getAsJsonObject(Constants.DATA_KEY);
        response.setOffset(data.get(Constants.OFFSET_KEY).getAsInt());
        response.setTotal(data.get(Constants.TOTAL_KEY).getAsInt());

        JsonArray charactersArray = data.getAsJsonArray(Constants.RESULTS_KEY);
        response.setComics(extractComicsFromJson(charactersArray));

        return response;
    }

    private List<Comic> extractComicsFromJson(JsonArray comicsArray) {
        List<Comic> comics = new ArrayList<>();

        for (int i = 0; i < comicsArray.size(); i++) {
            JsonObject characterData = comicsArray.get(i).getAsJsonObject();
            comics.add(Comic.buildFromJson(characterData));
        }
        return comics;
    }
}
