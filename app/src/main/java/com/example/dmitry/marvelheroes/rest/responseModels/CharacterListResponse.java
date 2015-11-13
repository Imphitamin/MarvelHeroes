package com.example.dmitry.marvelheroes.rest.responseModels;

import com.example.dmitry.marvelheroes.item.Character;
import com.example.dmitry.marvelheroes.rest.Constants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class CharacterListResponse {

    @SerializedName(Constants.CODE_KEY)
    int code;

    @SerializedName(Constants.STATUS_KEY)
    String status;

    @Expose
    public int offset;

    @Expose
    public List<Character> characters;

    public CharacterListResponse() {
        super();
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    public List<Character> getCharacters() {
        return characters;
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

    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }

}
