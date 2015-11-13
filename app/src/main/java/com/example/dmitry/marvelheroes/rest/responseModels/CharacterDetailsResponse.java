package com.example.dmitry.marvelheroes.rest.responseModels;

import com.example.dmitry.marvelheroes.item.CharacterDetailsCreate;
import com.example.dmitry.marvelheroes.rest.Constants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class CharacterDetailsResponse {

    @SerializedName(Constants.CODE_KEY)
    private int code;

    @SerializedName(Constants.STATUS_KEY)
    private String status;

    @Expose
    List<CharacterDetailsCreate> characterDetailsCreates;

    public void setCharacterDetailsCreates(List<CharacterDetailsCreate> characterDetailsCreates) {
        this.characterDetailsCreates = characterDetailsCreates;
    }
    public List<CharacterDetailsCreate> getCharacterDetailsCreates() {
        return characterDetailsCreates;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

}
