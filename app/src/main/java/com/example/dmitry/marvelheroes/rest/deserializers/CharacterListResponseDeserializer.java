package com.example.dmitry.marvelheroes.rest.deserializers;

import com.example.dmitry.marvelheroes.database.sqlDatabase;
import com.example.dmitry.marvelheroes.item.Character;
import com.example.dmitry.marvelheroes.rest.Constants;
import com.example.dmitry.marvelheroes.rest.responseModels.CharacterListResponse;

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

public class CharacterListResponseDeserializer implements JsonDeserializer<CharacterListResponse> {

    //public sqlDatabase myDb;

    @Override
    public CharacterListResponse deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        CharacterListResponse response = gson.fromJson(json, CharacterListResponse.class);

        JsonObject data = json.getAsJsonObject()
                .getAsJsonObject(Constants.DATA_KEY);
        response.setOffset(data.get(Constants.OFFSET_KEY).getAsInt());

        JsonArray charactersArray = data.getAsJsonArray(Constants.RESULTS_KEY);
        response.setCharacters(extractCharactersFromJsom(charactersArray));

        //String charactersList = gson.toJson(charactersArray);
        //myDb.insertCharactersArray(charactersList);  // Inserting Characters-Array in Database

        return response;
    }

    public ArrayList<Character> extractCharactersFromJsom(JsonArray charactersArray) {
        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < charactersArray.size(); i++) {
            JsonObject characterData = charactersArray.get(i).getAsJsonObject();
            characters.add(Character.buildFromJson(characterData));
            //String charData = charactersArray.get(i).getAsString();
            //myDb.insertCharactersArray(charData);
        }
        return characters;
    }
}
