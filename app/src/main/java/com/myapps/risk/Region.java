package com.myapps.risk;

public class Region {

    private Region[] connected;
    private int viewID;
    private char colorControl;
    private char textColor;


    private int unitCount = 0;

    public Region(int viewID) {
        this.viewID = viewID;
    }




    public void setTextColor(char textColor) {
        this.textColor = textColor;
    }

    public char getTextColor() {
        return textColor;
    }

    public int getViewID() {
        return viewID;
    }

    public void setColorControl(char colorControl) {
        this.colorControl = colorControl;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public char getColorControl() {
        return colorControl;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public Region[] getConnected() {
        return connected;
    }

    public void setConnected(Region[] connected) {
        this.connected = connected;
    }
}
