package com.example.dmitry.marvelheroes.item;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.dmitry.marvelheroes.rest.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class Comic implements Parcelable {

    //@SerializedName(Constants.ID_KEY)
    private int id;

    //@SerializedName(Constants.COMIC_TITLE_KEY)
    private String title;

    //@SerializedName(Constants.COMIC_PAGES_KEY)
    private int pageCount;

    //@SerializedName(Constants.COMIC_ISSUE_KEY)
    private int issueNumber;

    @Expose
    Uri urlImage;

    protected Comic(Parcel in) {
        id = in.readInt();
        title = in.readString();
        pageCount = in.readInt();
        issueNumber = in.readInt();
        urlImage = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public static Comic buildFromJson(JsonObject characterData) {
        Gson gson = new Gson();
        Comic thisComic = gson.fromJson(characterData, Comic.class);
        thisComic.setUrlImage(extractComicImgFromJson(characterData));
        return thisComic;
    }

    private static Uri extractComicImgFromJson(JsonObject characterData) {
        if (characterData.get(Constants.TUMBNAIL_KEY).isJsonNull()) { return Uri.EMPTY; }

        // Получу URL путь картинки, затем её расширение
        String imgUrl = characterData.get(Constants.TUMBNAIL_KEY).getAsJsonObject()
                .get(Constants.PATH_KEY).getAsString() + "." +
                characterData.get(Constants.TUMBNAIL_KEY).getAsJsonObject()
                        .get(Constants.EXTENSION_KEY).getAsString();

        return Uri.parse(imgUrl);
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setPages(int pages) {
        this.pageCount = pages;
    }
    public int getPages() {
        return pageCount;
    }

    public void setIssues(int issues) {
        this.issueNumber = issues;
    }
    public int getIssues() {
        return issueNumber;
    }

    public void setUrlImage(Uri urlImage) {
        this.urlImage = urlImage;
    }
    public Uri getUrlImage() {
        return urlImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcelOut, int flags) {
        parcelOut.writeInt(id);
        parcelOut.writeString(title);
        parcelOut.writeInt(pageCount);
        parcelOut.writeInt(issueNumber);
        parcelOut.writeParcelable(urlImage, flags);
    }

}
