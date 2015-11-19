package com.example.dmitry.marvelheroes.item;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class NavDrawItem {

    private int idIcon;
    private int idText;
    private int idKey;
    private int total;

    public NavDrawItem(int idIcon, int idText, int idKey, int total) {
        this.idIcon = idIcon;
        this.idText = idText;
        this.idKey = idKey;
        this.total = total;
    }

    public int getIdIcon() {
        return this.idIcon;
    }
    public int getIdText() {
        return idText;
    }
    public int getIdKey() {
        return idKey;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
