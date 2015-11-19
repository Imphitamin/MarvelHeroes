package com.example.dmitry.marvelheroes.item;

/**
 * Created by Dmitry on 13.10.2015.
 */

public class NavDrawItem {

    private int idIcon;
    private int idText;
    private int idKey;
    private int idTotal;

    /*public NavDrawItem(int idIcon, int idText, int idKey) {
        this.idIcon = idIcon;
        this.idText = idText;
        this.idKey = idKey;
    }*/

    public NavDrawItem(int idIcon, int idText, int idKey, int idTotal) {
        this.idIcon = idIcon;
        this.idText = idText;
        this.idKey = idKey;
        this.idTotal = idTotal;
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
    public int getIdTotal() {
        return idTotal;
    }
}
