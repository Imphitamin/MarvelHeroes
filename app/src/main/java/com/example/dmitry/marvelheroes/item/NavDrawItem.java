package com.example.dmitry.marvelheroes.item;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class NavDrawItem {
    // TODO: 13.11.2015 добавить ключик
    private int idIcon;
    private int idText;
    private int idKey;

    public NavDrawItem(int idIcon, int idText, int idKey) {
        this.idIcon = idIcon;
        this.idText = idText;
        this.idKey = idKey;
    }


    public int getIdIcon() {
        return idIcon;
    }
    public int getIdText() {
        return idText;
    }
    public int getIdKey() {
        return idKey;
    }

}
