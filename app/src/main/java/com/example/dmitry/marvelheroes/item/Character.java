package com.example.dmitry.marvelheroes.item;

import android.net.Uri;

import com.example.dmitry.marvelheroes.rest.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class Character {

    @SerializedName(Constants.ID_KEY)
    int id;

    @SerializedName(Constants.NAME_KEY)
    String name;

    @SerializedName(Constants.DESCRIPTION_KEY)
    String description;

    @Expose
    Uri urlImage;

    @Expose
    int availableComics;

    @Expose
    int availableSeries;

    @Expose
    int availableStories;

    // Построю {com.example.dmitry.marvelheroes.item.Character} из JsonObject
    public static Character buildFromJson(JsonObject characterData) {
        Gson gson = new Gson();
        Character thisCharacter = gson.fromJson(characterData, Character.class);
        thisCharacter.setUrlImage(extractCharacterImgFromJson(characterData));
        thisCharacter.setAvailableComics(
                extractCharacterAvailableResources(Constants.COMICS_KEY, characterData));
        thisCharacter.setAvailableSeries(
                extractCharacterAvailableResources(Constants.SERIES_KEY, characterData));
        thisCharacter.setAvailableStories(
                extractCharacterAvailableResources(Constants.STORIES_KEY, characterData));

        return thisCharacter;
    }

    // Использую JsonObject, который содержит данные о персонаже, чтобы получить его миниатюру
    private static Uri extractCharacterImgFromJson(JsonObject characterData) {
        if (characterData.get(Constants.TUMBNAIL_KEY).isJsonNull()) { return Uri.EMPTY; }

        // Сначала получу URL-путь к картинке, потом её расширение
        String imgUrl = characterData.get(Constants.TUMBNAIL_KEY).getAsJsonObject()
                .get(Constants.PATH_KEY).getAsString() + "." +
                characterData.get(Constants.TUMBNAIL_KEY).getAsJsonObject()
                        .get(Constants.EXTENSION_KEY).getAsString();
        return Uri.parse(imgUrl);
    }

    // Получаю доступные ресурсы для персонажа (комиксы, сериалы и т.д.)
    private static int extractCharacterAvailableResources(String availableResource, JsonObject characterData) throws IllegalArgumentException {
        if (!availableResource.equals(Constants.COMICS_KEY) &&
                !availableResource.equals(Constants.STORIES_KEY) &&
                !availableResource.equals(Constants.SERIES_KEY)) {
            throw new IllegalArgumentException();
        } else {
            return characterData.get(availableResource).getAsJsonObject().get(Constants.AVAILABLE_KEY).getAsInt();
        }
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setUrlImage(Uri urlImage) {
        this.urlImage = urlImage;
    }
    public Uri getUrlImage() {
        return urlImage;
    }

    public void setAvailableComics(int availableComics) {
        this.availableComics = availableComics;
    }
    public int getAvailableComics() {
        return availableComics;
    }

    public void setAvailableSeries(int availableSeries) {
        this.availableSeries = availableSeries;
    }
    public int getAvailableSeries() {
        return availableSeries;
    }

    public void setAvailableStories(int availableStories) {
        this.availableStories = availableStories;
    }
    public int getAvailableStories() {
        return availableStories;
    }

    @Override
    public String toString() {
        String hero = name +
                "\n Description:" + description +
                "\n img:" + urlImage +
                "\n available comics:" + availableComics +
                "\n available series:" + availableSeries +
                "\n available stories:" + availableStories;
        return hero;
    }
}
