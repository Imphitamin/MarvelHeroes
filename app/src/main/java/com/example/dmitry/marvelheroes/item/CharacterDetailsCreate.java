package com.example.dmitry.marvelheroes.item;

import com.example.dmitry.marvelheroes.rest.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class CharacterDetailsCreate extends Character {

    public static CharacterDetailsCreate buildFromJson(JsonObject characterDetailsData) {
        Gson gson = new Gson();
        CharacterDetailsCreate thisCharacterDetailsCreate = gson.fromJson(characterDetailsData, CharacterDetailsCreate.class);
        thisCharacterDetailsCreate.setDescription(Constants.DESCRIPTION_KEY);
        return thisCharacterDetailsCreate;
    }
}
