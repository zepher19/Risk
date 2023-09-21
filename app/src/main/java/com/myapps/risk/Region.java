package com.myapps.risk;

public class Region {

    private String[] connected;

    private int viewID;

    private char colorControl;

    private int unitCount = 0;

    public Region(String[] connected, int viewID) {
        this.connected = connected;
        this.viewID = viewID;
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

    public String[] getConnected() {
        return connected;
    }
}
