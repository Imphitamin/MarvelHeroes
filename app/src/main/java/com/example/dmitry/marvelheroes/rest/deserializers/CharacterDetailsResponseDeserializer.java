package com.example.dmitry.marvelheroes.rest.deserializers;

import com.example.dmitry.marvelheroes.item.CharacterDetailsCreate;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterDetailsResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class CharacterDetailsResponseDeserializer implements JsonDeserializer<CharacterDetailsResponse> {

    @Override
    public CharacterDetailsResponse deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        CharacterDetailsResponse response = gson.fromJson(json, CharacterDetailsResponse.class);

        JsonObject data = json.getAsJsonObject().getAsJsonObject(Constants.DATA_KEY);

        JsonArray characterDetailsDataArray = data.getAsJsonArray(Constants.RESULTS_KEY);
        response.setCharacterDetailsCreates(extractCharacterFromJson(characterDetailsDataArray));

        return response;
    }

    private ArrayList<CharacterDetailsCreate> extractCharacterFromJson(JsonArray characterDetailsArray) {
        ArrayList<CharacterDetailsCreate> characterDetailsCreates = new ArrayList<>();
        for (int index = 0; index < characterDetailsArray.size(); index++) {
            JsonObject characterDetail = characterDetailsArray.get(index).getAsJsonObject();
            characterDetailsCreates.add(CharacterDetailsCreate.buildFromJson(characterDetail));
        }
        return characterDetailsCreates;
    }

}
