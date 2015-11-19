package com.example.dmitry.marvelheroes.rest.responseModels;

import com.example.dmitry.marvelheroes.item.Comic;
import com.example.dmitry.marvelheroes.rest.Constants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class ComicsListResponse {

    @SerializedName(Constants.CODE_KEY)
    private int code;

    @SerializedName(Constants.STATUS_KEY)
    private String status;

    @Expose
    private int total;

    @Expose
    private int offset;

    @Expose
    List<Comic> comics;

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }
    public List<Comic> getComics() {
        return comics;
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

    public void setTotal(int total) {
        this.total = total;
    }
    public int getTotal() {
        return total;
    }
}
